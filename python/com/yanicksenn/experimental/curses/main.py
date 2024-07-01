import sys
from curses import wrapper
from python.com.yanicksenn.libraries.flags import flags
from python.com.yanicksenn.experimental.curses.lib import option_dialog
from python.com.yanicksenn.experimental.curses.lib import OptionDialogConfig

def main():
    flags.parse(sys.argv)
    # TODO - yanicksenn: Text and options should be taken from the flags as well.
    config = OptionDialogConfig("Lorem      ipsum dolor sit amet, consectetur adipiscing elit. Donec porta tortor et lacinia     sagittis. Sed dignissim lobortis augue,   id ultricies elit malesuada ac. Morbi commodo, ante            quis auctor scelerisque, nisl mauris ultricies ipsum,          ac fringilla leo sapien non eros. Sed sollicitudin neque ante, ut vulputate diam finibus at.                    Vivamus a mi nec neque tempor rutrum. Vestibulum porta dapibus velit, quis          cursus massa tincidunt ac. Pellentesque ac arcu nunc. Quisque lectus dui, suscipit et efficitur nec, suscipit vitae diam?", [
        "Cats",
        "Dogs",
        "Cows"
    ])
    selection = wrapper(option_dialog, config)
    # TODO - yanicksenn: Write selection into output file. The output file path should be passed from a flag.

if __name__ == '__main__':
    main()