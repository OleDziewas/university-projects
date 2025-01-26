from abc import ABC, abstractmethod
from runners import ResultSequence


class Visualizer(ABC):
    @abstractmethod
    def add_data(self,
                 submission_id: str,
                 reference_trace: list[ResultSequence],
                 jpype_trace: list[ResultSequence]):
        pass

    @abstractmethod
    def visualize(self):
        pass
