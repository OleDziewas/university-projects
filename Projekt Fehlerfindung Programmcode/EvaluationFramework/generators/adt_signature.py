from typing import Type

from adts import ADTSubclass
from generators import Generator, MethodSequence
import itertools


# Generate suitable test cases from an ADT signature by differentiating between observer operations and modifiers
class ADTSignatureGenerator(Generator):
    # Store the signature for the generation process later on
    def __init__(self, adt: Type[ADTSubclass]) -> None:
        super().__init__(adt)

    # Create a list of all possible method sequences of maximum length max_len
    def create_sequences(self, max_len: int) -> list[MethodSequence]:
        sequences: list[MethodSequence] = []

        # Get a list of all modifiers and observers based on the signature
        observers = [x for x in self.signature if self.signature[x].readonly]
        modifiers = [
            x for x in self.signature if not self.signature[x].readonly]
        modifiers.remove("create")

        # Generate all possible combinations for the method sequence length max_len
        method_product = itertools.product(
            modifiers, repeat=max_len)

        # Iterate over every combination of length sequence_length
        for method_comb_noobs in method_product:
            method_comb = []
            for method in method_comb_noobs:
                method_comb.append(method)
                method_comb += observers

            # Sample suitable parameters for every method name
            method_seq = [
                (method_name, self.signature[method_name].sample_arguments()) for method_name in method_comb
            ]

            method_seq.insert(
                0, ("create", self.signature["create"].sample_arguments()))
            sequences.append(method_seq)

        return sequences
