from abc import abstractmethod, ABC
from typing import Any

from algorithms import DiagnosisResult


# Abstract base class for metric calculation on diagnosis results compared to some given assumed truth
class DiagnosisMetric(ABC):
    # Calculate and accumulate metrics based on a single set of results
    @abstractmethod
    def accumulate(self, real_result: DiagnosisResult, pred_result: DiagnosisResult) -> None:
        pass

    # Compress the stored information into some kind of short summary values
    @abstractmethod
    def digest(self) -> Any:
        pass
    
    # Returns the names of the result parameters (e.g. "precision")
    @abstractmethod
    def get_result_names(self) -> list[str]:
        pass
