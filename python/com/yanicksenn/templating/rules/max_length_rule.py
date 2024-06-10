from python.com.yanicksenn.templating.rules.abstract_rule import IntArgumentRuleParser
from python.com.yanicksenn.templating.rules.abstract_rule import AbstractRule
from python.com.yanicksenn.templating.rules.abstract_rule import RuleParsingException

class Rule(AbstractRule):
    length: int

    def __init__(self, length):
        super().__init__()
        self.length = length

    def is_valid(self, value: str) -> bool:
        return len(value) <= self.length
    
    def build_violation_message(self, key: str, value: str) -> str:
        return f"{key} length must be equal or less than {self.length} but was {len(value)}"

class Parser(IntArgumentRuleParser):
    def __init__(self):
        super().__init__('MAX_LENGTH')

    def _validate(self, rule_parameter: str | None):
        super()._validate(rule_parameter)
        if int(rule_parameter) < 0:
            raise RuleParsingException(f"rule {self.rule_name} requires a positive int parameter")

    def _build(self, rule_parameter: str | None):
        return Rule(int(rule_parameter))
