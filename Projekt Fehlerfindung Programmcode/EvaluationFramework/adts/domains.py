import random
import string
from typing import Any

from adts import ADTDomain


# List domains sample values by choosing a random element from the list they are initialized with
class ListDomain(ADTDomain):
    def __init__(self, values: list[Any]) -> None:
        self.values: list[Any] = values

    # Pick a value at random
    def sample(self) -> Any:
        return random.choice(self.values)


# A byte domain samples stringified hex representations of random byte values
class ByteDomain(ADTDomain):
    def __init__(self) -> None:
        self.values = list(range(256))

    def sample(self) -> str:
        return "\\\\x" + hex(random.choice(self.values))[2:].zfill(2)

# A char domain samples from lowercase and uppercase letters contained in the alphabet
class CharDomain(ListDomain):
    def __init__(self) -> None:
        super().__init__(list(string.ascii_letters))

# An int domain samples from a range of integers between a minimum and maximum value (inclusive)
class IntDomain(ListDomain):
    def __init__(self, min_val, max_val) -> None:
        super().__init__(list(range(min_val, max_val + 1)))


# A bool domain returns either True or False during sampling
class BoolDomain(ListDomain):
    def __init__(self) -> None:
        super().__init__([True, False])
