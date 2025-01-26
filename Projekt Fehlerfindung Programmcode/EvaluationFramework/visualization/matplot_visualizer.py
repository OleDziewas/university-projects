from generators import MethodSequence
from algorithms import DiagnosisAlgorithm
from metrics import DiagnosisMetric
from visualization import Visualizer
import copy
from runners import ResultSequence
import matplotlib.pyplot as plt


class MatPlotVisualizer(Visualizer):
    def __init__(self, ground_truth: DiagnosisAlgorithm,
                 diagnosis_algos: list[DiagnosisAlgorithm],
                 metric: DiagnosisMetric) -> None:
        self.ground_truth = ground_truth
        self.diagnosis_algos = diagnosis_algos
        self.metric = metric
        self.diagnosis_results = {algo: copy.deepcopy(
            self.metric) for algo in self.diagnosis_algos}
        self.parameters_per_testcase = []

    def add_data(self, submission_id: str, reference_trace: list[ResultSequence], jpype_trace: list[ResultSequence]):
        self.diagnosis_results = {algo: copy.deepcopy(
            self.metric) for algo in self.diagnosis_algos}
        real_result = self.ground_truth.diagnose(submission_id, [], [])
        parameters_per_algo = []
        for algo in self.diagnosis_results:
            if (type(algo).__name__ != "ASPDiagnosisWrapper"):
                pred_result = algo.diagnose(
                    submission_id, reference_trace, jpype_trace)
            else:
                pred_result = algo.diagnose(submission_id, [], [])
            self.diagnosis_results[algo].accumulate(real_result, pred_result)
            parameters_per_algo.append(self.diagnosis_results[algo].digest())
        self.parameters_per_testcase.append(parameters_per_algo)

    def visualize(self, seq_length: int, metric_not_to_print: list[str]):
        x = list(range(1, seq_length+1))
        i = 0
        fig, axs = plt.subplots(len(self.diagnosis_algos))
        for algo in self.diagnosis_results:

            lines = {name: [] for name in self.metric.get_result_names()}
            for parameters in self.parameters_per_testcase:
                e = 0
                for line in lines.keys():
                    lines[line].append(parameters[i][e])
                    e += 1

            for line in lines.keys():
                metrics = self.metric.get_result_names()
                for metric in metric_not_to_print:
                    metrics.remove(metric)

                if (line in metrics):
                    axs[i].plot(x, lines[line], label=line)

            axs[i].set_title(type(algo).__name__)
            i += 1

        for ax in axs.flat:
            ax.set(xlabel='Number of Testcases')

        # Hide x labels and tick labels for top plots and y ticks for right plots.
        for ax in axs.flat:
            ax.label_outer()
        for ax in axs:
            handles, labels = ax.get_legend_handles_labels()
            ax.set(ylim=(0, 1.2))

        fig.legend(handles, labels, loc='upper right')
        plt.show()
