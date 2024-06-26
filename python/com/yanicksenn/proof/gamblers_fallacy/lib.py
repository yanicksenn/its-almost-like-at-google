from enum import Enum
from python.com.yanicksenn.libraries.logging import logging
import random

class Color(Enum):
    RED = 1
    BLACK = 2
    GREEN = 3

probabilities = {
    Color.RED: 18,
    Color.BLACK: 18,
    Color.GREEN: 2,
}

def __roll(probabilities: dict[Color, int]) -> tuple[Color, int]:
    slot_amount = sum(probabilities.values())
    slot = random.randint(0, slot_amount - 1)
    slots = []
    for (color, amount) in probabilities.items():
        for _ in range(amount):
            slots.append(color)
    return (slots[slot], slot)

def __collect_metrics(results: list[tuple[Color, int]]):
    # Proving the Gambler's Fallacy. Proofing that previous runs 
    # are not indicative of future runs.
    # https://en.wikipedia.org/wiki/Gambler%27s_fallacy
    runs = len(results)
    if runs >= 3:
        played = 0
        correct = 0

        playing_colors = [Color.RED, Color.BLACK]
        for i in range(2, runs):
            prev2_color = results[i - 2][0]
            prev1_color = results[i - 1][0]
            curr_color = results[i][0]

            if prev1_color == prev2_color and prev1_color in playing_colors:
                played += 1
                if prev1_color == Color.RED and curr_color == Color.BLACK:
                    correct += 1
                
                elif prev1_color == Color.BLACK and curr_color == Color.RED:
                    correct += 1
                
                elif prev1_color == Color.GREEN:
                    played -= 1

        if played == 0:
            logging.warning(f'No runs with twice the same color consecutively.')

        else:
            logging.info(f"Total played: {played}")
            logging.info(f"Total correct: {correct}")

            # This will always approach 18/38 %
            logging.info(f"Won: {(correct / played) * 100}%")


def run(runs: int):
    results: list[tuple[Color, int]] = []
    for _ in range(runs):
        results.append(__roll(probabilities))

    __collect_metrics(results)
