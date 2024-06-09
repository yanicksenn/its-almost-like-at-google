import re

class Rule:
    def is_valid(self, value: str) -> bool:
        return value != None

class RegexRule(Rule):
    pattern: str

    def __init__(self, pattern):
        super().__init__()
        self.pattern = pattern

    def is_valid(self, value: str) -> bool:
        return re.match(self.pattern, value)

class AlphanumericRule(RegexRule):
    def __init__(self):
        super().__init__(r'[a-zA-Z0-9]+')

class AlphabeticRule(RegexRule):
    def __init__(self):
        super().__init__(r'[a-zA-Z]+')

class NumericRule(Rule):
    def is_valid(self, value: str) -> bool:
        try:
            _ = int(value)
            return True
        except ValueError:
            return False

class MinLength(Rule):
    length: int

    def __init__(self, length):
        super().__init__()
        self.length = length

    def is_valid(self, value: str) -> bool:
        return len(value) >= self.length

class MaxLength(Rule):
    length: int

    def __init__(self, length):
        super().__init__()
        self.length = length

    def is_valid(self, value: str) -> bool:
        return len(value) <= self.length