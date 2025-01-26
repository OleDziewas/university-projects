from abc import ABC
from typing import TypeAlias, Optional, Any

from generators import MethodSequence

# Result sequences are represented as a list of (method_name, [param1, param2, ...], result)
ResultSequence: TypeAlias = list[tuple[str, Optional[list[Any]], Optional[Any]]]

# Abstract base class for runners, sharing some logic for executing method sequences on objects
class Runner(ABC):
    def _execute_implementation(self, adt: Any, sequence: MethodSequence, camel_case: bool = False) -> ResultSequence:
        result_sequence = []

        # Consider each method call in the sequence, even if an error happens beforehand
        for (method_name, method_params) in sequence:
            # If no parameters are given, set them to an empty list for destructuring
            if method_params is None:
                method_params = []

            # Some runners need the ability to switch between snake_case and camelCase, so convert names accordingly
            if camel_case:
                actual_method_name = "".join([
                    (word.title() if i else word) for (i, word) in enumerate(method_name.split("_"))
                ])
            else:
                actual_method_name = method_name

            # All errors are caught and stored as the result of the corresponding method call
            try:
                result = getattr(adt, actual_method_name)(*method_params)
            except Exception as e:
                result = "error"

            result_sequence.append((method_name, method_params, result))

        return result_sequence
