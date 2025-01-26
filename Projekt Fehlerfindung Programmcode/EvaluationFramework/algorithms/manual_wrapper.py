import json

from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Wrap manually annotated results from a json file
class ManualDiagnosisWrapper(DiagnosisAlgorithm):
    def __init__(self, name: str, result_file: str) -> None:
        super().__init__(name)

        with open(result_file, "r") as f:
            self.results = json.loads(f.read())

    def diagnose(self, submission_id: str, _1: list[ResultSequence], _2: list[ResultSequence]) -> DiagnosisResult:
        # Load and return the correct annotation and set the time taken to 0.0
        errors = self.results[submission_id]

        return [errors if "correct" not in errors else []], 0.0

    # List all the submissions that were annotated in the first place and that can actually compile
    def available_submissions(self):
        return [key for key in list(self.results.keys()) if "compile" not in self.results[key]]
