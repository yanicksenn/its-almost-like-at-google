from dataclasses import dataclass
from pathlib import Path
import re

from python.com.yanicksenn.templating.rules import Rule
from python.com.yanicksenn.templating.rules import RegexRule
from python.com.yanicksenn.templating.rules import AlphabeticRule
from python.com.yanicksenn.templating.rules import AlphanumericRule
from python.com.yanicksenn.templating.rules import NumericRule
from python.com.yanicksenn.templating.rules import MinLength
from python.com.yanicksenn.templating.rules import MaxLength

class TemplatePreconditionException(Exception):
    def __init__(self, message):
        super().__init__(message)

@dataclass
class RuleDefinition:
    key: str
    default: str | None
    rules: list[Rule] | None

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

def __parse_rule_definition_rules(rules_raw: str | None) -> list[Rule]:
    if not rules_raw:
        return []
    
    rules = []
    rules_pattern = re.compile(r'\s*([a-zA-Z0-9_]+(\([^)]+\))?)\s*,?', flags = re.MULTILINE)
    rule_pattern = re.compile(r'([a-zA-Z0-9_]+)(\((.+)\))?')
    for rule_raw in re.findall(rules_pattern, rules_raw):
        rule = re.match(rule_pattern, rule_raw[0].strip()).groups()
        rule_name = rule[0]

        if rule_name == 'REGEX':
            if rule[2] == None:
                raise TemplatePreconditionException(f"rule {rule_name} is missing it's parameters")
            rules.append(RegexRule(rule[2]))
            
        elif rule_name == 'ALPHANUMERIC':
            if rule[2] != None:
                raise TemplatePreconditionException(f"rule {rule_name} is does not accept any parameter")
            rules.append(AlphanumericRule())
            
        elif rule_name == 'ALPHABETIC':
            if rule[2] != None:
                raise TemplatePreconditionException(f"rule {rule_name} is does not accept any parameter")
            rules.append(AlphabeticRule())
            
        elif rule_name == 'NUMERIC':
            if rule[2] != None:
                raise TemplatePreconditionException(f"rule {rule_name} is does not accept any parameter")
            rules.append(NumericRule())
            
        elif rule_name == 'MIN_LENGTH':
            rule_value = rule[2]
            if rule_value == None:
                raise TemplatePreconditionException(f"rule {rule_name} is missing it's parameters")
            try:
                rules.append(MinLength(int(rule_value)))
            except ValueError:
                raise TemplatePreconditionException(f"param {rule_value} for rule {rule_name} cannot be parsed to an int")
            
        elif rule_name == 'MAX_LENGTH':
            rule_value = rule[2]
            if rule_value == None:
                raise TemplatePreconditionException(f"rule {rule_name} is missing it's parameters")
            try:
                rules.append(MaxLength(int(rule_value)))
            except ValueError:
                raise TemplatePreconditionException(f"param {rule_value} for rule {rule_name} cannot be parsed to an int")
        else:
            raise TemplatePreconditionException(f"rule {rule_name} is unknown")

    return rules

def __parse_rule_definition(key_raw: str, default_raw: str | None, rules_raw: str | None) -> RuleDefinition:
    return RuleDefinition(
        (key_raw or "").strip(), 
        (default_raw or "").strip(), 
        __parse_rule_definition_rules(rules_raw))

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
        rule_definition = rule_definitions[key]
        
        input_raw: str | None = None
        while True:
            message = f'{rule_definition.key}: ' if len(rule_definition.default) == 0 else f'{rule_definition.key} ({rule_definition.default}): '
            input_raw = input(message)
            input_raw = input_raw if len(input_raw) != 0 else rule_definition.default
            
            conforms = True
            for rule in rule_definition.rules:
                if not rule.is_valid(input_raw):
                    print(f"Input failed {type(rule)} check")
                    conforms = False
            
            if conforms:
                break

        fixed_content = fixed_content.replace(key, input_raw)
    
    print()
    print("=== PREVIEW BEING")
    print(fixed_content)
    print("=== PREVIEW END")

    
