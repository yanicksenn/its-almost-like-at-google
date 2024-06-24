from python.com.yanicksenn.tools.templating.rules.abstract_rule import AnyArgumentRuleParser
from python.com.yanicksenn.tools.templating.rules.abstract_rule import AbstractRule
import re

class Rule(AbstractRule):
    pattern: str

    def __init__(self, pattern):
        super().__init__()
        self.pattern = pattern

    def is_valid(self, value: str) -> bool:
        return re.match(self.pattern, value)
    
    def build_violation_message(self, key: str, value: str) -> str:
        return f"{key} does not match the pattern {self.pattern}"

class Parser(AnyArgumentRuleParser):
    def __init__(self):
        super().__init__('REGEX')

    def _build(self, rule_parameter: str | None):
        return Rule(rule_parameter)
