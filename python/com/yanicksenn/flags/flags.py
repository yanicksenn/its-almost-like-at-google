from typing import Optional

__parsed_args = dict()

def parse(args: list[str]):
    __parsed_args.clear()

    for arg in args:
        if arg.startswith("--"):
            separator_index = arg.find("=")
            has_value = separator_index != -1
            arg_name = arg[2:(separator_index if has_value else len(arg))]
            arg_value = arg[separator_index + 1:len(arg)] if has_value else None
            __parsed_args[arg_name] = arg_value

def get(name: str) -> Optional[str]:
    if name not in __parsed_args:
        return None
    return __parsed_args[name]

def getAsInt(name: str) -> Optional[int]: 
    if name not in __parsed_args:
        return None
    valueRaw = __parsed_args[name]
    if valueRaw == None:
        return None
    try:
        return int(valueRaw)
    except ValueError:
        return None
    
def is_set(name: str):
    return name in __parsed_args

def has_value(name: str):
    return is_set(name) and __parsed_args[name] != None

def all_flags() -> dict[str, str]:
    return __parsed_args.copy()
