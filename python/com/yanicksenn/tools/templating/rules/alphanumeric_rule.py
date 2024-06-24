from python.com.yanicksenn.tools.templating.rules.abstract_rule import NoArgumentRuleParser
import python.com.yanicksenn.tools.templating.rules.regex_rule as regex

class Rule(regex.Rule):
    def __init__(self):
        super().__init__(r'[a-zA-Z0-9]+')

    def build_violation_message(self, key: str, value: str) -> str:
        return f"{key} is not alphanumeric"

class Parser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('ALPHANUMERIC')

    def _build(self, rule_parameter: str | None):
        return Rule()
