from algorithms import DiagnosisAlgorithm
from metrics import DiagnosisMetric
from visualization import Visualizer
import copy
from runners import ResultSequence

# Visualize the results of a diagnosis test run on the console


class ConsoleVisualizer(Visualizer):
    def __init__(
            self,
            ground_truth: DiagnosisAlgorithm,
            diagnosis_algos: list[DiagnosisAlgorithm],
            metric: DiagnosisMetric
    ) -> None:
        self.ground_truth = ground_truth
        self.diagnosis_algos = diagnosis_algos
        self.metric = metric
        self.diagnosis_results = {algo: copy.deepcopy(
            self.metric) for algo in self.diagnosis_algos}

    def add_data(self, submission_id: str, reference_trace: list[ResultSequence], jpype_trace: list[ResultSequence]):
        real_result = self.ground_truth.diagnose(submission_id, [], [])

        for algo in self.diagnosis_results:
            if (type(algo).__name__ != "ASPDiagnosisWrapper"):
                pred_result = algo.diagnose(
                    submission_id, reference_trace, jpype_trace)
            else:
                pred_result = algo.diagnose(submission_id, [], [])

            self.diagnosis_results[algo].accumulate(real_result, pred_result)

    def visualize(self):
        for algo in self.diagnosis_results:
            print(f"{algo.name} prediction results against {self.ground_truth.name}:")
            result = list(self.diagnosis_results[algo].digest())
            names = self.metric.get_result_names()
            for value in zip(names, result):
                print("> {name}: {x:.4f}".format(name=value[0], x=value[1]))
