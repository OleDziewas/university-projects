import random
from typing import Type

import iteration_utilities

from adts import ADTSubclass
from generators import Generator, MethodSequence


# Generate random method sequences of random length from an ADT signature
class RandomGenerator(Generator):
    # Store the signature for the generation process later on
    def __init__(self, adt: Type[ADTSubclass]) -> None:
        super().__init__(adt)

    # Create a list of count method sequences of maximum length max_len where methods are weighted by weights
    def create_sequences(self, count: int, max_len: int, weights: dict[str, int]) -> list[MethodSequence]:
        sequences: list[MethodSequence] = []

        # Apply the weights to influence the method distribution
        distributed_methods = self.__modify_method_distribution(weights)

        # Generate new sequences until the target count has been reached
        for _ in range(count):
            # Sequences have to have at least length one (so that something except the constructor is called)
            seq_length = random.randrange(1, max_len)
            method_names = iteration_utilities.random_combination(
                distributed_methods, replacement=True, r=seq_length)
            shuffled_method_names = [
                "create"] + list(iteration_utilities.random_permutation(method_names))

            # Sample values for the method parameters from the domains given in the ADT signature
            method_seq = []
            for method_name in shuffled_method_names:
                method_seq.append(
                    (method_name, self.signature[method_name].sample_arguments()))

            sequences.append(method_seq)

        return sequences

    # Increase how often method names occur based on the given weights
    def __modify_method_distribution(self, weights: dict[str, int]) -> list[str]:
        distribution = []

        for method_name in self.signature.keys():
            # Remove create from the set of possible methods as it is only used once at the start
            if method_name == "create":
                continue

            # If no weights were supplied, just assume a weight of one; otherwise include the method name n times
            if method_name not in weights:
                distribution.append(method_name)
            else:
                distribution += [method_name] * weights[method_name]

        return distribution
