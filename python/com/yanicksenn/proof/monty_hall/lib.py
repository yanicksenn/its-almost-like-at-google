from enum import Enum
import random

class DoorPrice(Enum):
    FERRARI = 1
    GOAT = 2

class DoorException(Exception):
    def __init__(self, message):
        super().__init__(message)

class DoorState(Enum):
    AVAILABLE = 1
    REVEALED = 2
    CHOSEN = 3

class Door():
    __price: DoorPrice
    __state: DoorState

    def __init__(self, price: DoorPrice):
        super().__init__()
        self.__id = id
        self.__price = price
        self.__state = DoorState.AVAILABLE

    def id(self) -> int:
        return self.__id
    
    def price(self) -> DoorPrice:
        return self.__price
    
    def state(self) -> DoorState:
        return self.__state
    
    def choose(self):
        if self.__state == DoorState.CHOSEN:
            raise DoorException(f"cannot choose door {self.__id} because it has already been chosen.")

        if self.__state == DoorState.REVEALED:
            raise DoorException(f"cannot choose door {self.__id} because it has already been revealed.")
        
        self.__state = DoorState.CHOSEN
    
    def unchoose(self):
        if self.__state == DoorState.AVAILABLE:
            raise DoorException(f"cannot unchoose door {self.__id} because it is still available.")

        if self.__state == DoorState.REVEALED:
            raise DoorException(f"cannot unchoose door {self.__id} because it has already been revealed.")
        
        self.__state = DoorState.AVAILABLE
    
    def reveal(self):
        if self.__state == DoorState.REVEALED:
            raise DoorException(f"cannot reveal door {self.__id} because it has already been revealed.")

        if self.__state == DoorState.CHOSEN:
            raise DoorException(f"cannot reveal door {self.__id} because it has already been chosen.")
        
        self.__state = DoorState.REVEALED

    def __str__(self):
     return f"Door with {self.__price}: {self.__state}"

probabilities = {
    DoorPrice.FERRARI: 1,
    DoorPrice.GOAT: 2
}

def __info(message: str):
    print(f"INFO: {message}")

def __assert_amount_of_doors_with_state(amount: int, doors: list[Door], state: DoorState):
    assert len(list(filter(lambda e: e.state() == state, doors))) == amount

def __assert_doors_with_state(doors: list[Door], states: dict[DoorState, int]):
    total_doors = sum(states.values())
    for (door_state, amount) in states.items():
        __assert_amount_of_doors_with_state(amount, doors, door_state)
    assert len(doors) == total_doors

def __lets_make_a_deal() -> bool:
    doors: list[Door] = []
    for (door_price, amount) in probabilities.items():
        for _ in range(amount):
            doors.append(Door(door_price))
    __assert_doors_with_state(doors, {
        DoorState.AVAILABLE: 3
    })

    random.shuffle(doors)

    # Choose any available door.
    chosen_door_idx = random.randint(0, len(doors) - 1)
    chosen_door = doors[chosen_door_idx]
    chosen_door.choose()
    __assert_doors_with_state(doors, {
        DoorState.AVAILABLE: 2,
        DoorState.CHOSEN: 1
    })

    # Reveal door with a goat.
    available_doors = list(filter(lambda e: e.state() == DoorState.AVAILABLE and e.price() == DoorPrice.GOAT, doors))
    revealed_door_idx = random.randint(0, len(available_doors) - 1)
    revealed_door = available_doors[revealed_door_idx]
    revealed_door.reveal()
    __assert_doors_with_state(doors, {
        DoorState.AVAILABLE: 1,
        DoorState.CHOSEN: 1,
        DoorState.REVEALED: 1
    })

    # Change the chosen door to the other one available.
    remaining_doors = list(filter(lambda e: e.state() == DoorState.AVAILABLE, doors))
    assert len(remaining_doors) == 1

    remaining_door = remaining_doors[0]
    remaining_door.choose()
    chosen_door.unchoose()
    __assert_doors_with_state(doors, {
        DoorState.AVAILABLE: 1,
        DoorState.CHOSEN: 1,
        DoorState.REVEALED: 1
    })

    # Returns whether the Ferrari was won.
    return remaining_door.price() == DoorPrice.FERRARI

def run(runs: int):
    correct = 0
    for _ in range(runs):
        if __lets_make_a_deal():
            correct += 1
    
    __info(f"Total played: {runs}")
    __info(f"Total correct: {correct}")

    # This will always approach 66%
    __info(f"Won: {(correct / runs) * 100}%")

