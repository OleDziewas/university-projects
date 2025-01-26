import json

from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Wrap precomputed ASP results from a json file
class ASPDiagnosisWrapper(DiagnosisAlgorithm):
    def __init__(self, name: str, result_file: str) -> None:
        super().__init__(name)
        with open(result_file, "r") as f:
            self.results = json.loads(f.read())

    def diagnose(self, submission_id: str, _1: list[ResultSequence], _2: list[ResultSequence]) -> DiagnosisResult:
        # Lookup the precomputed results
        result_entry = self.results[submission_id]

        # The total time is the sum of the grounding and solving time
        total_time = result_entry["grounding_time"] + \
            result_entry["solving_time"]

        # Switch all method names to lowercase for better comparability between camel case, snake case and the manual
        # annotation format
        results = [[atom.lower() for atom in sublist]
                   for sublist in result_entry["errors"]]

        return results, total_time
