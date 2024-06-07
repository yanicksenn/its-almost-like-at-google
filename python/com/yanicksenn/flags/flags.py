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
            print(f'{arg_name} = {arg_value}')

def get(name: str) -> Optional[str]:
    if name not in __parsed_args:
        return None
    return __parsed_args[name]

def getAsInt(name: str) -> Optional[int]: 
    if name not in __parsed_args:
        return None
    try:
        return int(__parsed_args[name])
    except ValueError:
        return None
