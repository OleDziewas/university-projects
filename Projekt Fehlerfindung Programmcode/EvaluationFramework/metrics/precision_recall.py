from typing import Type

from adts import ADTSubclass
from algorithms import DiagnosisResult
from metrics import DiagnosisMetric


# Metric calculating precision and recall on flattened results
class PrecisionRecallMetric(DiagnosisMetric):
    def __init__(self, adt: Type[ADTSubclass]) -> None:
        # State for remembering true/false positives/negatives
        self.tp = 0
        self.tn = 0
        self.fp = 0
        self.fn = 0

        # Total samples and time used for calculating mean time
        self.total_samples = 0
        self.total_time = 0.0

        # Accumulator for worst and best exam scores
        self.total_worst_exams = 0.0
        self.total_best_exams = 0.0

        # Generate a list of all ADT methods in all lowercase and without underscores
        self.methods = [x.replace("_", "")
                        for x in list(adt.get_signature().keys())]

    def accumulate(self, real_result: DiagnosisResult, pred_result: DiagnosisResult) -> None:
        # Add the sample and the time for mean time calculation
        self.total_samples += 1
        self.total_time += pred_result[1]

        # Flatten both results for easier comparison
        flattened_prediction = list(set([
            atom.lower().replace("_", "") for sublist in pred_result[0] for atom in sublist
        ]))
        flattened_truth = list(set(real_result[0][0]))

        # Classify each method into true/false positives/negatives
        for method in self.methods:
            if method in flattened_truth and method in flattened_prediction:
                self.tp += 1
            elif method in flattened_truth and method not in flattened_prediction:
                self.fn += 1
            elif method not in flattened_truth and method in flattened_prediction:
                self.fp += 1
            elif method not in flattened_truth and method not in flattened_prediction:
                self.tn += 1

        if len(set(flattened_truth).intersection(set(flattened_prediction))) != 0:
            # Prediction contains at least one correct component
            # Worst case: Examine all of the correct components before reaching the faulty one
            worst_exam = (len(set(flattened_prediction).difference(
                set(flattened_truth))) + 1) / len(self.methods)
            self.total_worst_exams += worst_exam

            # Best case: First examined component is faulty
            self.total_best_exams += 1.0 / len(self.methods)
        else:
            # Prediction is completely wrong
            # Worst case: Examine every single program component until the fault is found
            self.total_worst_exams += 1.0

            # Best case: The first component examined after the predictions is faulty
            best_exam = (len(flattened_prediction) + 1) / len(self.methods)
            self.total_best_exams += best_exam

    # Calculate precision, recall and the mean time
    def digest(self) -> tuple[float, float, float, float, float, float, float]:
        mean_time = self.total_time / (self.total_samples + 1e-30) * 1000.0

        if self.tp == 0 and self.tn == 0 and self.fp == 0 and self.fn == 0:
            precision = 1.0
            recall = 1.0
        else:
            precision = self.tp / (self.tp + self.fp + 1e-30)
            recall = self.tp / (self.tp + self.fn + 1e-30)

        f1_score = 2 * (recall * precision) / (recall + precision + 1e-30)
        accuracy = (self.tp + self.tn) / (self.tp +
                                          self.tn + self.fp + self.fn + 1e-30)

        worst_exam = self.total_worst_exams / (self.total_samples + 1e-30)
        best_exam = self.total_best_exams / (self.total_samples + 1e-30)

        return precision, recall, f1_score, accuracy, worst_exam, best_exam, mean_time

    def get_result_names(self) -> list[str]:
        return ["precision", "recall", "f1_score", "accuracy", "worst_exam", "best_exam", "mean_time_ms"]
