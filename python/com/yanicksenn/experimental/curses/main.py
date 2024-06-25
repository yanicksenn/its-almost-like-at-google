from curses import wrapper
from python.com.yanicksenn.experimental.curses.lib import option_dialog
from python.com.yanicksenn.experimental.curses.lib import OptionDialogConfig

def main():
    # TODO - yanicksenn: Text and options should be taken from the flags as well.
    config = OptionDialogConfig("Whats your favorite animal?", [
        "Cats",
        "Dogs",
        "Cows"
    ])
    selection = wrapper(option_dialog, config)
    # TODO - yanicksenn: Write selection into output file. The output file path should be passed from a flag.

if __name__ == '__main__':
    main()