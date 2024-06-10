from python.com.yanicksenn.templating.rules.abstract_rule import NoArgumentRuleParser
import python.com.yanicksenn.templating.rules.regex_rule as regex

class Rule(regex.Rule):
    def __init__(self):
        super().__init__(r'[a-zA-Z]+')
    
    def build_violation_message(self, key: str, value: str) -> str:
        return f"{key} is not alphabetic"

class Parser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('ALPHABETIC')

    def _build(self, rule_parameter: str | None):
        return Rule()