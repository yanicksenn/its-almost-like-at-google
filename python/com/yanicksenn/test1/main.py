import sys
from python.com.yanicksenn.flags import flags
from python.com.yanicksenn.test1.lib import run

def main():
    flags.parse(sys.argv)

    name = flags.get("name")
    if name is None: 
        name = "World"

    run(name)

if __name__ == '__main__':
    main()