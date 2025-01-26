from adts import ADT, ADTMethod, ADTSignature
from adts.domains import CharDomain, IntDomain, BoolDomain


# Stack ADT implementation with a limited static capacity
class Stack(ADT):
    # ADT consists of a constructor and methods for pushing, popping and checking whether the stack is full or empty
    @staticmethod
    def get_signature() -> ADTSignature:
        # Sample chars from the alphabet and the integer 3
        char_dom = CharDomain()
        int_dom = IntDomain(3, 3)
        bool_dom = BoolDomain()

        method_list = [
            ADTMethod("create", [int_dom], None, False),
            ADTMethod("push", [char_dom], None, False),
            ADTMethod("pop", None, char_dom, False),
            ADTMethod("is_full", None, bool_dom, True),
            ADTMethod("is_empty", None, bool_dom, True)
        ]

        return {
            method.name: method for method in method_list
        }

    # Remember the capacity limit for later and create a list for element storage
    def __init__(self, capacity: int) -> None:
        self.capacity: int = capacity
        self.content: list[str] = []

    # Add the value on top of the stack if the capacity has not been reached yet, otherwise raise an error
    def push(self, value: str) -> None:
        if len(self.content) >= self.capacity:
            raise RuntimeError("Cannot push to a full stack")

        self.content.append(value)

    # Return the value from the top of the stack, remove it and raise an error if the stack is empty
    def pop(self) -> str:
        if len(self.content) == 0:
            raise RuntimeError("Cannot pop from an empty stack")

        return self.content.pop()

    # Check whether the stack capacity has been reached
    def is_full(self) -> bool:
        return len(self.content) >= self.capacity

    # Check whether there are currently no elements on the stack
    def is_empty(self) -> bool:
        return len(self.content) == 0
