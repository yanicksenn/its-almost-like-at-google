from dataclasses import dataclass

@dataclass
class RuleViolation:
    message: str

class RuleParsingException(Exception):
    def __init__(self, message):
        super().__init__(message)

class AbstractRule:
    def is_valid(self, value: str) -> bool:
        raise NotImplementedError()
    
    def build_violation_message(self, value: str) -> str:
        raise NotImplementedError()
    
    def validate(self, value: str) -> RuleViolation | None:
        return None if self.is_valid(value) else RuleViolation(self.build_violation_message(value))

class AbstractParser():
    rule_name: str

    def __init__(self, rule_name: str):
        super().__init__()
        self.rule_name = rule_name

    def _build(self, rule_parameter: str | None):
        raise NotImplementedError()
    
    def _validate(self, rule_parameter: str | None):
        raise NotImplementedError()

    def parse(self, rule_parameter: str | None) -> AbstractRule:
        self._validate(rule_parameter)
        return self._build(rule_parameter)

class NoArgumentRuleParser(AbstractParser):
    def _validate(self, rule_parameter: str | None):
        if rule_parameter != None:
            raise RuleParsingException(f"rule {self.rule_name} does not accept a parameter")

class AnyArgumentRuleParser(AbstractParser):
    def _validate(self, rule_parameter: str | None):
        if rule_parameter == None:
            raise RuleParsingException(f"rule {self.rule_name} requires a parameter")
    
class IntArgumentRuleParser(AnyArgumentRuleParser):
    def _validate(self, rule_parameter: str | None):
        super()._validate(rule_parameter)
        try:
            _ = int(rule_parameter)
        except ValueError:
            raise RuleParsingException(f"rule {self.rule_name} requires an int parameter")
