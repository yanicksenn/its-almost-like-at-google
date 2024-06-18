from pathlib import Path
import datetime
import time
import os
import re

__todo_pattern = r'TODO - ([^\s^:]+): (.+)$'

class IllegalArgumentException(Exception):
    def __init__(self, message):
        super().__init__(message)

def __validate_root_dir(root_dir: str):
    try:
        root_path = Path(root_dir).resolve(strict=True)
    except FileNotFoundError:
        raise IllegalArgumentException(f"path {root_dir} does not exist")
    
    if not root_path.is_dir:
        raise IllegalArgumentException(f"path {root_path} is not a directory")

def __resolve_path(full_path: str) -> Path | None:
    try:
        return Path(full_path).resolve(strict=True)
    except FileNotFoundError:
        raise None
    
def __is_in_scope(full_path: str) -> bool:
    # TODO - yanicksenn: Make this configurable or simply put it into static config file.
    sub_paths_to_ignore = [
        "/.DS_Store",
        "/.hg/",
        "/.git/",
        "/.vscode/"
    ]

    for sub_path_to_ignore in sub_paths_to_ignore:
        if sub_path_to_ignore in full_path:
            return False

    return True

# TODO - yanicksenn: Wrap parameters into a wrapper class.
def search_todos(root_dir: str, context: str | None, debug: bool = False) -> list[str]:
    __validate_root_dir(root_dir)

    todo_pattern = re.compile(__todo_pattern, re.MULTILINE)

    total_start_time = time.time()
    files_read = 0
    
    for (root, _, files) in os.walk(root_dir):
        for file in files:
            file_path = os.path.join(root, file)
            if not __is_in_scope(file_path):
                continue

            resolved_path = __resolve_path(file_path)
            if resolved_path is None:
                continue

            file_content = resolved_path.read_text()
            files_read += 1
            all_todos = re.findall(todo_pattern, file_content)
            context_todos = list(filter(lambda todo: context is None or context == todo[0], all_todos))
            if len(context_todos) == 0:
                continue

            print(file_path)
            for todo in context_todos:
                print(f'\t> {todo[0]}: {todo[1]}')
            print()

    if debug:
        total_duration = datetime.timedelta(seconds = time.time() - total_start_time)
        print()
        print(f'{root_dir = }')
        print(f'{context = }')
        print(f'Read {files_read} files in {str(total_duration)}')

                

            

