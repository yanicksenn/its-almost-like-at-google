from python.com.yanicksenn.templating.rules.abstract_rule import NoArgumentRuleParser
from python.com.yanicksenn.templating.rules.abstract_rule import AbstractRule

class Rule(AbstractRule):
    def is_valid(self, value: str) -> bool:
        try:
            _ = int(value)
            return True
        except ValueError:
            return False
    
    def build_violation_message(self, value: str) -> str:
        return "value is not numeric"
    
class Parser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('NUMERIC')

    def _build(self, rule_parameter: str | None):
        return Rule()