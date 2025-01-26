from abc import ABC, abstractmethod
from typing import Any, Optional, TypeAlias, TypeVar, Type


# Abstract base class for parameter and return value domains
class ADTDomain(ABC):
    # Provides the ability to generalize generating values, for example from a list, between two numbers, ...
    @abstractmethod
    def sample(self) -> Any:
        pass


# Arguments are represented as a list of domains, but they can be None as well if no arguments are required
ADTArguments: TypeAlias = Optional[list[ADTDomain]]
# Results can either be a single domain or None if nothing is returned
ADTResult: TypeAlias = Optional[ADTDomain]


# Wrapper class for declaring the structure of a method in an ADT
class ADTMethod:
    def __init__(self, name: str, arguments: ADTArguments, result: ADTResult, readonly: bool) -> None:
        self.name: str = name
        self.arguments: ADTArguments = arguments
        self.result: ADTResult = result
        self.readonly = readonly

    # Returns a list of arguments for the method sampled from the argument domains
    def sample_arguments(self) -> Optional[list[Any]]:
        if self.arguments is None:
            return None
        else:
            return [argument.sample() for argument in self.arguments]


# A complete ADT signature consists of a couple methods
ADTSignature: TypeAlias = dict[str, ADTMethod]


# Abstract base class for ADTs which only have to offer a way to access their signature
class ADT(ABC):
    @staticmethod
    @abstractmethod
    def get_signature() -> ADTSignature:
        pass


ADTSubclass = TypeVar("ADTSubclass", bound=Type[ADT])
