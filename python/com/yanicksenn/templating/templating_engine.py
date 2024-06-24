from dataclasses import dataclass
from pathlib import Path
from python.com.yanicksenn.libraries.logging import logging
import re

from python.com.yanicksenn.templating.rules.abstract_rule import AbstractParser
from python.com.yanicksenn.templating.rules.abstract_rule import AbstractRule
import python.com.yanicksenn.templating.rules.regex_rule as regex
import python.com.yanicksenn.templating.rules.alphabetic_rule as alphabetic
import python.com.yanicksenn.templating.rules.alphanumeric_rule as alphanumeric
import python.com.yanicksenn.templating.rules.numeric_rule as numeric
import python.com.yanicksenn.templating.rules.min_length_rule as min_length
import python.com.yanicksenn.templating.rules.max_length_rule as max_length

class TemplatePreconditionException(Exception):
    def __init__(self, message):
        super().__init__(message)

@dataclass
class TemplateRequest:
    template_path_raw: str
    rules_path_raw: str
    target_root_raw: str
    interactive: bool
    custom_flags: dict[str, str]
    override_output: bool

@dataclass
class RuleDefinition:
    key: str
    default: str | None
    rules: list[AbstractRule] | None

__rule_parsers: list[AbstractParser] = [
    regex.Parser(),
    alphanumeric.Parser(), 
    alphabetic.Parser(), 
    numeric.Parser(),
    min_length.Parser(), 
    max_length.Parser()
]

def __validate_template_path(template_path_raw: str):
    path = Path(template_path_raw)
    if not path.exists():
        raise TemplatePreconditionException(f"template {template_path_raw} cannot be resolved")

def __validate_rules_path(rules_path_raw: str):
    path = Path(rules_path_raw)
    if not path.exists():
        raise TemplatePreconditionException(f"rules {rules_path_raw} cannot be resolved")
    if not path.is_file():
        raise TemplatePreconditionException(f"rules {rules_path_raw} is not a file")

def __validate_template_request(template_request: TemplateRequest):
    __validate_template_path(template_request.template_path_raw)
    __validate_rules_path(template_request.rules_path_raw)

def __remove(template_request: TemplateRequest, path: Path):
    if path.is_file():
        logging.debug(f"Deleting file {path} ...")
        path.unlink()
    else:
        for child in path.iterdir():
            __remove(template_request, child)
        logging.debug(f"Deleting directory {path} ...")
        path.rmdir()

def __extract_templates(template_path: Path, rules_path: Path) -> list[str]:
    if template_path.is_file():
        return [ template_path ]
    else:
        templates: list[Path] = []
        for dirpath, _, filenames in template_path.walk():
            for filename in filenames:
                absolute_filepath = str(Path.joinpath(dirpath, filename))
                
                # Ignore RULES file.
                if absolute_filepath == str(rules_path):
                    continue
                
                relative_filepath = absolute_filepath[len(str(template_path)) + 1:]
                templates.append(relative_filepath)
        return templates

def __parse_rule_definition_rules(rules_raw: str | None) -> list[AbstractRule]:
    if not rules_raw:
        return []
    
    rule_parsers_by_name = { x.rule_name: x for x in __rule_parsers }

    rules = []
    rules_pattern = re.compile(r'\s*([a-zA-Z0-9_]+(\([^)]+\))?)\s*,?', flags = re.MULTILINE)
    rule_pattern = re.compile(r'([a-zA-Z0-9_]+)(\((.+)\))?')
    for rule_raw in re.findall(rules_pattern, rules_raw):
        rule = re.match(rule_pattern, rule_raw[0].strip()).groups()
        rule_name = rule[0]
        rule_parameter = rule[2]
        rule_parser = rule_parsers_by_name.get(rule_name, None)
        if not rule_parser:
            raise TemplatePreconditionException(f"rule {rule_name} is unknown")

        rules.append(rule_parser.parse(rule_parameter))

    return rules

def __parse_rule_definition(key_raw: str, default_raw: str | None, rules_raw: str | None) -> RuleDefinition:
    return RuleDefinition(
        (key_raw or "").strip(), 
        (default_raw or "").strip(), 
        __parse_rule_definition_rules(rules_raw))

def __build_input_message(rule_definition: RuleDefinition) -> str:
    if len(rule_definition.default) == 0:
        return f'{rule_definition.key}: '
    return f'{rule_definition.key} (default = {rule_definition.default}): '

def __get_replacement_from_user_input_until_valid(rule_definition: RuleDefinition) -> str:
    input_raw: str | None = None
    while True:
        input_raw = input(__build_input_message(rule_definition))
        input_raw = input_raw if len(input_raw) != 0 else rule_definition.default
            
        conforms = True
        for rule in rule_definition.rules:
            rule_violation = rule.validate(rule_definition.key, input_raw)
            if rule_violation:
                print(f'> {rule_violation.message}')
                conforms = False
            
        if conforms:
            break
        else:
            print()

    return input_raw

def __get_replacement_from_flags(rule_definition: RuleDefinition, custom_flags: dict[str, str]) -> str:
    flag_name = rule_definition.key
    flag_value = custom_flags[flag_name] if flag_name in custom_flags else rule_definition.default

    for rule in rule_definition.rules:
        rule_violation = rule.validate(rule_definition.key, flag_value)
        if rule_violation:
            raise TemplatePreconditionException(rule_violation.message)

    return flag_value

def run(template_request: TemplateRequest):
    __validate_template_request(template_request)
    template_path = Path(template_request.template_path_raw)
    rules_path = Path(template_request.rules_path_raw)
    target_root = Path(template_request.target_root_raw)

    rule_definition_pattern_raw = r'^([a-zA-Z0-9_]+)\s*=?\s*([a-zA-Z0-9_\- *%+\.]*)?\s*(\[[^;]*])?\s*;$'
    rule_definition_pattern = re.compile(rule_definition_pattern_raw, flags = re.MULTILINE)

    logging.debug(f"Extracting rules {rules_path.absolute()} ...")
    rule_definitions: dict[str, RuleDefinition] = {}
    for match in re.findall(rule_definition_pattern, rules_path.read_text()):
        rule_definition = __parse_rule_definition(
            match[0], 
            match[1], 
            match[2])
        rule_definitions[rule_definition.key] = rule_definition

    logging.debug("Getting replacements ...")
    key_replacements: dict[str, str] = {}
    for key in rule_definitions:
        key_replacement = None
        if template_request.interactive:
            key_replacement = __get_replacement_from_user_input_until_valid(rule_definitions[key])
        else:
            key_replacement = __get_replacement_from_flags(rule_definitions[key], template_request.custom_flags)
        key_replacements[key] = key_replacement
        logging.debug(f"Replacing {key} with '{key_replacement}'.")

    if template_request.override_output and target_root.exists():
        __remove(template_request, target_root)

    if not target_root.exists():
        logging.debug(f"Creating {target_root.absolute()} ...")
        target_root.mkdir(parents = True)

    for relative_template in __extract_templates(template_path, rules_path):
        template_content = Path.joinpath(template_path, relative_template).read_text()
        fixed_content = template_content

        # TODO - yanicksenn: Also replace filenames if they contain a key.
        output_file = Path.joinpath(target_root, relative_template)

        if not output_file.parent.exists():
            logging.debug(f"Creating {output_file.parent.absolute()} ...")
            output_file.parent.mkdir()

        logging.debug(f"Fixing {output_file.absolute()} ...")
        for (key, replacement) in key_replacements.items():
            fixed_content = fixed_content.replace(key, replacement)

        output_file.write_text(fixed_content)

    logging.info("Templating complete.")
