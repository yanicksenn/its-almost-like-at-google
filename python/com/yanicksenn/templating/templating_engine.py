from dataclasses import dataclass
from pathlib import Path
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

def __validate_template_path(template_path_raw: str) -> Path:
    try:
        return Path(template_path_raw).resolve(strict=True)
    except FileNotFoundError:
        raise TemplatePreconditionException(f"template {template_path_raw} cannot be resolved")
    
def __validate_rules_path(rules_path_raw: str) -> Path:
    try:
        return Path(rules_path_raw).resolve(strict=True)
    except FileNotFoundError:
        raise TemplatePreconditionException(f"rules {rules_path_raw} cannot be resolved")

def __validate_target_path(target_path_raw: str) -> Path:
    try:
        return Path(target_path_raw).resolve(strict=True)
    except FileNotFoundError:
        raise TemplatePreconditionException(f"target {target_path_raw} cannot be resolved")

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

def __request_user_input_until_valid(rule_definition):
    input_raw: str | None = None
    while True:
        input_raw = input(__build_input_message(rule_definition))
        input_raw = input_raw if len(input_raw) != 0 else rule_definition.default
            
        conforms = True
        for rule in rule_definition.rules:
            rule_violation = rule.validate(input_raw)
            if rule_violation:
                print(f'> {rule_violation.message}')
                conforms = False
        print()
            
        if conforms:
            break

    return input_raw

def run(template_path_raw: str, target_path_raw: str):
    template_path = __validate_template_path(template_path_raw)
    rules_path = __validate_rules_path(template_path_raw + '.rules')
    target_path = __validate_target_path(target_path_raw)

    rule_definition_pattern_raw = r'^([a-zA-Z0-9_]+)\s*=?\s*([a-zA-Z0-9_\- *%+\.]*)?\s*(\[[^;]*])?\s*;$'
    rule_definition_pattern = re.compile(rule_definition_pattern_raw, flags = re.MULTILINE)

    rule_definitions: dict[str, RuleDefinition] = {}
    for match in re.findall(rule_definition_pattern, rules_path.read_text()):
        rule_definition = __parse_rule_definition(
            match[0], 
            match[1], 
            match[2])
        rule_definitions[rule_definition.key] = rule_definition

    template_content = template_path.read_text()
    fixed_content = template_content
    for key in rule_definitions:
        key_replacement = __request_user_input_until_valid(rule_definitions[key])
        fixed_content = fixed_content.replace(key, key_replacement)
    
    print()
    print("=== PREVIEW BEING")
    print(fixed_content)
    print("=== PREVIEW END")
    print()

    
