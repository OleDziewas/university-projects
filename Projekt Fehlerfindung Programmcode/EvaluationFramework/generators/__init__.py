from abc import ABC
from typing import TypeAlias, Any, Optional, Type

from adts import ADTSignature, ADTSubclass

# Method sequences are represented as a list of (method_name, [param1, param2, ...]) or (method_name, None) tuples
MethodSequence: TypeAlias = list[tuple[str, Optional[list[Any]]]]


# Abstract base class for generators, which only have to store the ADT signature for sequence generation
class Generator(ABC):
    def __init__(self, adt: Type[ADTSubclass]) -> None:
        self.signature: ADTSignature = adt.get_signature()
