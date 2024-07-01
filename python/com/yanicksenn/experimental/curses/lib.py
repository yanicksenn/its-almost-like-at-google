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
    
    def __render_text_left(self, stdscr, y_start: int, text: str) -> int:
        _, cols = stdscr.getmaxyx()
        padding_horizontal = 1
        max_width = cols - (padding_horizontal * 2)
        
        lines: list[str] = []
        current_line: str = ""
        current_word: str = ""
        for char in text:
            if char.isspace(): 
                # Repeated empty characters are not added at the beginning of new lines
                # to prevent weird looking indentations.
                current_len = len(current_line) + len(current_word)
                if current_len == 0:
                    continue
                
                # Default case when a word is finished. Just appen the word an the
                # character to the current line.
                if current_len < max_width:
                    current_line += current_word
                    current_line += char

                else:
                    if current_len == max_width:
                        current_line += current_word
                        lines.append(current_line)

                        # Empty character is not appended here to ensure that new lines
                        # are not starting with an indentation.
                        current_line = ""

                    else:
                        lines.append(current_line)
                        current_line = current_word
                        current_line += char
                current_word = ""
            else:
                current_word += char

        # Fit the last word on the current line if possible. Otherwise
        # complete the current line and add the last word to a new line.
        if len(current_line) + len(current_word) <= max_width:
            lines.append(current_line + current_word)

        else:
            lines.append(current_line)
            lines.append(current_word)

        lines_len = len(lines)
        for i in range(lines_len):
            stdscr.addstr(y_start + i, 1, lines[i])
        
        return lines_len

    def __render_options(self, stdscr, y_start: int, options: list[str]) -> int:
        y = y_start
        for option in options:
            stdscr.addstr(y, 3, option)
            y += 1
        
        return len(options)

    def __render_static_content(self, stdscr, config, y_start: int):
        y = y_start
        header_y_start = y
        header_len = self.__render_text_left(stdscr, header_y_start, config.text)
        header_margin = 1
        y += header_len + header_margin

        # Print options into window.
        options_y_start = y
        options_len = self.__render_options(stdscr, options_y_start, config.options)
        options_margin = 1
        y += options_len + options_margin
        return (header_y_start, options_y_start)
    
    def render(self, stdscr):
        model = self.model()
        config = model.config()

        # Initialize the view.
        curses.cbreak()
        curses.curs_set(0)
        curses.init_pair(1, curses.COLOR_WHITE, curses.COLOR_BLUE)
        stdscr.bkgd(' ', curses.color_pair(1) | curses.A_BOLD)
        stdscr.clear()

        y_start = 1
        (_, y_options) = self.__render_static_content(stdscr, config, y_start)

        while True:
            y = y_options + model.current_selection()
            stdscr.addstr(y, 1, ">")
            
            char = stdscr.getch()
            stdscr.addstr(y, 1, " ")
            if char == curses.KEY_UP:
                model.move_up()

            if char == curses.KEY_DOWN:
                model.move_down()

            if char == curses.KEY_RESIZE:
                stdscr.clear()
                (_, y_options) = self.__render_static_content(stdscr, config, y_start)
            
            if char == 10: # curses.KEY_ENTER:
                break


def option_dialog(stdscr, config: OptionDialogConfig):
    if config is None:
        raise ValueError("config must not be none.")
    
    model = OptionDialogModel(config)
    renderer = OptionDialogRenderer(model)

    renderer.render(stdscr)

    return model.current_selection()

