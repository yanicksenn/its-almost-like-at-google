import curses
from dataclasses import dataclass

@dataclass
class OptionDialogConfig():
    text: str
    options: list[str]
    default_selection: int = 0

class OptionDialogModel():
    __config: OptionDialogConfig
    __current_selection: int

    def __init__(self, config: OptionDialogConfig) -> None:
        super().__init__()
        if config is None:
            raise ValueError("config must not be none.")
        self.__config = config
        self.__current_selection = config.default_selection

    def config(self):
        return self.__config

    def current_selection(self):
        return self.__current_selection

    def move_up(self):
        self.__current_selection = max(self.__current_selection - 1, 0)

    def move_down(self):
        self.__current_selection = min(self.__current_selection + 1, len(self.__config.options) - 1)

class OptionDialogRenderer():
    __model: OptionDialogModel

    def __init__(self, model: OptionDialogModel) -> None:
        super().__init__()
        if model is None:
            raise ValueError("model must not be none.")
        self.__model = model
    
    def model(self) -> OptionDialogModel:
        return self.__model
    
    def config(self) -> OptionDialogModel:
        return self.model().config()
    
    def render(self, stdscr):
        model = self.model()
        config = model.config()

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

        while True:
            y = y_start + model.current_selection()
            stdscr.addstr(y, 1, ">")
            
            char = stdscr.getch()
            stdscr.addstr(y, 1, " ")
            if char == curses.KEY_UP:
                model.move_up()

            if char == curses.KEY_DOWN:
                model.move_down()
            
            if char == 10: # curses.KEY_ENTER:
                break


def option_dialog(stdscr, config: OptionDialogConfig):
    if config is None:
        raise ValueError("config must not be none.")
    
    model = OptionDialogModel(config)
    renderer = OptionDialogRenderer(model)

    renderer.render(stdscr)

    return model.current_selection()

