import subprocess
from typing import Type

from adts import ADTSubclass
from generators import Generator, MethodSequence


# Generate suitable test cases from an ADT signature
class ADTEquationGenerator(Generator):
    # Store the signature and specification file for the generation process later on
    def __init__(self, adt: Type[ADTSubclass], adt_file: str) -> None:
        super().__init__(adt)

        self.adt_file = adt_file

    # Create and convert suitable tests from the stored specification file
    def create_sequences(self) -> list[MethodSequence]:
        try:
            _ = subprocess.check_output(
                ["swipl", self.adt_file], stderr=subprocess.PIPE).decode("ascii")
        except:
            pass

        # TODO: Actual conversion from Prolog to Python; just return constant set for now
        method_sequences = [
            [('create', [4]), ('isempty', None)],
            [('create', [1]), ('pop', None)],
            [('create', [2]), ('push', ['n']),
             ('push', ['q']), ('isfull', None)],
            [('create', [1]), ('isfull', None)],
            [('create', [3]), ('push', ['d']), ('push', ['j']),
             ('push', ['d']), ('push', ['p'])],
            [('create', [2]), ('isempty', None),
             ('push', ['o']), ('isempty', None)],
            [('create', [4]), ('pop', None),
             ('push', ['h']), ('isempty', None)],
            [('create', [1]), ('push', ['z']), ('isfull', None),
             ('push', ['p']), ('isempty', None)],
            [('create', [3]), ('push', ['g']), ('isfull', None),
             ('push', ['j']), ('isempty', None)],
            [('create', [2]), ('push', ['d']), ('push', ['c']),
             ('push', ['b']), ('push', ['m']), ('isempty', None)],
            [('create', [4]), ('pop', None), ('push', ['f']), ('pop', None)],
            [('create', [4]), ('push', ['k']),
             ('isfull', None), ('push', ['k']), ('pop', None)],
            [('create', [1]), ('pop', None), ('push', ['f']), ('pop', None)],
            [('create', [3]), ('push', ['e']), ('push', ['p']), ('push', ['n']),
             ('isfull', None), ('push', ['a']), ('pop', None)],
            [('create', [2]), ('push', ['e']),
             ('isfull', None), ('push', ['r']), ('pop', None)],
            [('create', [1]), ('push', ['j']),
             ('push', ['w']), ('push', ['j']), ('pop', None)],
        ]

        return method_sequences
