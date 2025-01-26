from abc import abstractmethod, ABC
from typing import TypeAlias

from runners import ResultSequence

# Diagnosis results are represented as ([[c1, c2], [c2, c3]], time_taken) where time_taken is the runtime of the
# algorithm and [[c1, c2], [c2, c3]] means that the error is either in c1 and c2 or in c2 and c3
DiagnosisResult: TypeAlias = tuple[list[list[str]], float]


# Abstract base class for diagnosis algorithms
class DiagnosisAlgorithm(ABC):
    def __init__(self, name: str) -> None:
        self.name = name

    # Generate a diagnosis result from a submission id and a list of result sequences
    @abstractmethod
    def diagnose(
        self,
        submission_id: str,
        true_method_seqs: list[ResultSequence],
        obs_method_seqs: list[ResultSequence],
    ) -> DiagnosisResult:
        pass
