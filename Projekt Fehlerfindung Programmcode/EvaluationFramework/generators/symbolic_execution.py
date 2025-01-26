from typing import Type
from adts import ADTSubclass
from generators import Generator, MethodSequence


# Generate suitable test cases from symbolic execution of a target program
class SymbolicExecutionGenerator(Generator):
    # Store the signature for the generation process later on
    def __init__(self, adt: Type[ADTSubclass]) -> None:
        super().__init__(adt)

    # Create and convert suitable tests from the symbolic execution run
    def create_sequences(self) -> list[MethodSequence]:
        # TODO: Actual symbolic execution pre- and postprocessing, just set to constant for now

        method_sequences = [
            [('create', [1]), ('pop', None), ('push', ['q']),
             ('isempty', None), ('push', ['g'])],
            [('create', [1]), ('pop', None), ('push', ['d']),
             ('push', ['m']), ('push', ['v'])],
            [('create', [1]), ('pop', None), ('pop', None), ('pop', None)],
            [('create', [1]), ('isempty', None),
             ('push', ['w']), ('push', ['q']), ('push', ['d'])],
            [('create', [1]), ('isfull', None), ('push', ['g']),
             ('push', ['d']), ('push', ['o'])],
            [('create', [1]), ('push', ['t']), ('isfull', None),
             ('push', ['z']), ('push', ['y'])],
            [('create', [1]), ('push', ['j']), ('isempty', None),
             ('push', ['h']), ('push', ['d'])],
            [('create', [1]), ('push', ['d']), ('push', ['r']), ('push', ['s'])],
            [('create', [1]), ('push', ['t']), ('pop', None),
             ('push', ['d']), ('push', ['i'])],
        ]

        return method_sequences
