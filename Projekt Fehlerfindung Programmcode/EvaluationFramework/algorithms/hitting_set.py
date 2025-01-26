import json
import time

from pysat.examples.hitman import Hitman

from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Calculate the minimal hitting of components present in the set of all erroneous traces
class HittingSetDiagnosis(DiagnosisAlgorithm):
    def __init__(self, name: str, include_create: bool) -> None:
        super().__init__(name)
        self.include_create = include_create

    def diagnose(
            self,
            _: str,
            true_method_seqs: list[ResultSequence],
            obs_method_seqs: list[ResultSequence]
    ) -> DiagnosisResult:
        start_time = time.time()
        results = []

        # Collect the components in all erreoneous traces
        erroneous_traces = []

        for i in range(len(true_method_seqs)):
            seq_obs = obs_method_seqs[i]
            seq_truth = true_method_seqs[i]

            # Check whether the observed and expected traces match
            if seq_obs == seq_truth:
                continue

            # If they do not match, cut the traces at the first error
            cutoff_index = len(seq_obs)
            for j in range(len(seq_obs)):
                if seq_obs[j] != seq_truth[j]:
                    cutoff_index = j
                    break

            # Then extract the component names and remove create if needed
            potential_defects = [method for (
                method, _, _) in seq_obs[:cutoff_index + 1]]

            if not self.include_create:
                potential_defects.remove("create")

            erroneous_traces.append(potential_defects)

        # Calculate the minimal hitting set
        with Hitman(bootstrap_with=erroneous_traces, htype="sorted") as hitman:
            for hs in hitman.enumerate():
                results.append(hs)

        total_time = time.time() - start_time

        return results, total_time
