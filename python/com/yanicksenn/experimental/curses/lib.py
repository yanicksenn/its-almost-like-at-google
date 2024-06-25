import curses
from dataclasses import dataclass

@dataclass
class OptionDialogConfig():
    text: str
    options: list[str]

def option_dialog(stdscr, config: OptionDialogConfig):
    if config is None:
        raise ValueError("config must not be none.")

    curses.cbreak()
    curses.curs_set(0)
    curses.init_pair(1, curses.COLOR_WHITE, curses.COLOR_BLUE)
    stdscr.bkgd(' ', curses.color_pair(1) | curses.A_BOLD)
    stdscr.clear()

    # Print text into window.
    stdscr.addstr(1, 1, config.text)

    # Print options into window.
    y_start = 3
    y = y_start
    for option in config.options:
        stdscr.addstr(y, 3, option)
        y += 1

    selection = 0
    while True:
        y = y_start + selection
        stdscr.addstr(y, 1, ">")
        stdscr.refresh()
        
        char = stdscr.getch()
        stdscr.addstr(y, 1, " ")
        if char == curses.KEY_UP:
            selection = max(selection - 1, 0)

        if char == curses.KEY_DOWN:
            selection = min(selection + 1, len(config.options) - 1)
        
        if char == 10: # curses.KEY_ENTER:
            return selection

