import sys
from python.com.yanicksenn.libraries.flags import flags
from python.com.yanicksenn.tools.todos.search_todos_lib import search_todos

def main():
    flags.parse(sys.argv)

    # TODO - yanicksenn: Introduce new flags method for python only to raise an error when flag is absent.
    root_dir = flags.get("root_dir")
    if root_dir is None:
        raise AssertionError("root_dir must be set")

    context = flags.get("context")
    debug = flags.is_toggled("debug")

    try:
        search_todos(root_dir, context, debug)
    except Exception as err:
        print(f"ERROR: {str(err)}")
        sys.exit(1)

if __name__ == '__main__':
    main()