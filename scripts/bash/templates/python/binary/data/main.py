import sys
from python.com.yanicksenn.libraries.flags import flags
from python.com.yanicksenn.libraries.logging import logging
from __PACKAGE_ROOT.__PACKAGE_NAME.lib import run

def main():
    flags.parse(sys.argv)

    name = flags.get("name")
    if name is None: 
        name = "World"

    logging.info(f"All params pass.")
    run(name)

if __name__ == '__main__':
    main()