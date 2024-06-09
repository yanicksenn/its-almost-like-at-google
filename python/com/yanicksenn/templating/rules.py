from dataclasses import dataclass
from python.com.yanicksenn.templating.utils import NotImplementedException
import re

class RuleParsingException(Exception):
    def __init__(self, message):
        super().__init__(message)

@dataclass
class RuleViolation:
    message: str

class AbstractRule:
    def is_valid(self, value: str) -> bool:
        raise NotImplementedException
    
    def build_violation_message(self, value: str) -> str:
        raise NotImplementedException
    
    def validate(self, value: str) -> RuleViolation | None:
        return None if self.is_valid(value) else RuleViolation(self.build_violation_message(value))

class RegexRule(AbstractRule):
    pattern: str

    def __init__(self, pattern):
        super().__init__()
        self.pattern = pattern

    def is_valid(self, value: str) -> bool:
        return re.match(self.pattern, value)
    
    def build_violation_message(self, value: str) -> str:
        return f"value does not match the pattern {self.pattern}"

class AlphanumericRule(RegexRule):
    def __init__(self):
        super().__init__(r'[a-zA-Z0-9]+')
    
    def build_violation_message(self, value: str) -> str:
        return "value is not alphanumeric"

class AlphabeticRule(RegexRule):
    def __init__(self):
        super().__init__(r'[a-zA-Z]+')
    
    def build_violation_message(self, value: str) -> str:
        return "value is not alphabetic"

class NumericRule(AbstractRule):
    def is_valid(self, value: str) -> bool:
        try:
            _ = int(value)
            return True
        except ValueError:
            return False
    
    def build_violation_message(self, value: str) -> str:
        return "value is not numeric"

class MinLengthRule(AbstractRule):
    length: int

    def __init__(self, length):
        super().__init__()
        self.length = length

    def is_valid(self, value: str) -> bool:
        return len(value) >= self.length
    
    def build_violation_message(self, value: str) -> str:
        return f"value length must be equal or longer than {self.length} but was {len(value)}"

class MaxLengthRule(AbstractRule):
    length: int

    def __init__(self, length):
        super().__init__()
        self.length = length

    def is_valid(self, value: str) -> bool:
        return len(value) <= self.length
    
    def build_violation_message(self, value: str) -> str:
        return f"value length must be equal or less than {self.length} but was {len(value)}"

class AbstractParser():
    rule_name: str

    def __init__(self, rule_name: str):
        super().__init__()
        self.rule_name = rule_name

    def _build(self, rule_parameter: str | None):
        raise NotImplementedException()
    
    def _validate(self, rule_parameter: str | None):
        raise NotImplementedException()

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

class RegexRuleParser(AnyArgumentRuleParser):
    def __init__(self):
        super().__init__('REGEX')

    def _build(self, rule_parameter: str | None):
        return RegexRule(rule_parameter)

class AlphanumericRuleParser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('ALPHANUMERIC')

    def _build(self, rule_parameter: str | None):
        return AlphanumericRule()

class AlphabeticRuleParser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('ALPHABETIC')

    def _build(self, rule_parameter: str | None):
        return AlphabeticRule()

class NumericRuleParser(NoArgumentRuleParser):
    def __init__(self):
        super().__init__('NUMERIC')

    def _build(self, rule_parameter: str | None):
        return NumericRule()

class MinLengthRuleParser(IntArgumentRuleParser):
    def __init__(self):
        super().__init__('MIN_LENGTH')

    def _validate(self, rule_parameter: str | None):
        super()._validate(rule_parameter)
        if int(rule_parameter) < 0:
            raise RuleParsingException(f"rule {self.rule_name} requires a positive int parameter")

    def _build(self, rule_parameter: str | None):
        return MinLengthRule(int(rule_parameter))

class MaxLengthRuleParser(IntArgumentRuleParser):
    def __init__(self):
        super().__init__('MAX_LENGTH')

    def _validate(self, rule_parameter: str | None):
        super()._validate(rule_parameter)
        if int(rule_parameter) < 0:
            raise RuleParsingException(f"rule {self.rule_name} requires a positive int parameter")

    def _build(self, rule_parameter: str | None):
        return MaxLengthRule(int(rule_parameter))

