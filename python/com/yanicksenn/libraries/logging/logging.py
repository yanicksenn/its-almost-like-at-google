from collections.abc import Callable
from dataclasses import dataclass
from enum import Enum

class LoggingLevel(Enum):
    INFO = 1
    WARNING = 2
    ERROR = 3

@dataclass
class LoggingMessage():
    level: LoggingLevel
    message: str

class LoggingConfig():
    __is_mute: bool = False
    __minimal_logging_level: LoggingLevel = LoggingLevel.INFO
    __peekers: list[Callable[[], LoggingMessage]] = []

    def is_mute(self) -> bool:
        return self.__is_mute
    
    def minimal_logging_level(self) -> LoggingLevel:
        return self.__minimal_logging_level
    
    def peekers(self) -> list[Callable[[], LoggingMessage]]:
        return self.__peekers
    
    def make_mute(self):
        self.__is_mute = True
    
    def make_noisy(self):
        self.__is_mute = False
    
    def set_minimal_logging_level(self, minimal_logging_level: LoggingLevel):
        if minimal_logging_level == None:
            raise ValueError("minimal logging level cannot be None")
        
        self.__minimal_logging_level = minimal_logging_level
    
    def add_peeker(self, peeker: Callable[[], LoggingMessage]):
        self.__peekers.append(peeker)

    def clear_peekers(self):
        self.__peekers.clear()

    
__logging_config = LoggingConfig()

def is_mute() -> bool:
    return __logging_config.is_mute()

def minimal_logging_level() -> LoggingLevel:
    return __logging_config.minimal_logging_level()

def make_mute():
    __logging_config.make_mute()

def make_noisy():
    __logging_config.make_noisy()

def set_minimal_logging_level(minimal_logging_level: LoggingLevel):
    __logging_config.set_minimal_logging_level(minimal_logging_level)

def add_peeker(peeker: Callable[[], LoggingMessage]):
    __logging_config.add_peeker(peeker)

def clear_peekers():
    __logging_config.clear_peekers()


def __format(logging_message: LoggingMessage) -> str:
    return f"{logging_message.level}: {logging_message.message}"

def __log(level: LoggingLevel, message: str):
    if not is_mute() and level.value >= minimal_logging_level().value:
        logging_message = LoggingMessage(level, message)
        for peeker in __logging_config.peekers():
            peeker(logging_message)
        print(__format(logging_message))

def info(message: str):
    __log(LoggingLevel.INFO, message)

def warning(message: str):
    __log(LoggingLevel.WARNING, message)

def error(message: str):
    __log(LoggingLevel.ERROR, message)