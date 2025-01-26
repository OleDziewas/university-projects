from typing import Type

from adts import ADTSubclass
from generators import MethodSequence
from runners import Runner, ResultSequence


# Run method sequences on reference implementations provided as python classes
class ReferenceRunner(Runner):
    # Store the given class for instantiation later on
    def __init__(self, reference_implementation: Type[ADTSubclass]) -> None:
        super().__init__()
        self.reference_implementation = reference_implementation

    # Execute each method sequence in the list individually and provide the results as a list again
    def execute_sequences(self, sequences: list[MethodSequence]) -> list[ResultSequence]:
        result_sequences = []

        for sequence in sequences:
            result_sequences.append(self.execute_sequence(sequence))

        return result_sequences

    # Run one method sequence on the reference implementation given in the constructor
    def execute_sequence(self, sequence: MethodSequence) -> ResultSequence:
        # Use the first method to call the constructor
        create_params = sequence[0][1]
        submission_stack = self.reference_implementation(*create_params)

        # Then execute the remaining method calls
        result_sequence = [("create", create_params, None)]
        result_sequence += self._execute_implementation(submission_stack, sequence[1:])

        return result_sequence
