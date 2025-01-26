import time
from itertools import chain, combinations
from typing import Type

from adts import ADTSubclass
from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Start with the powerset of all applicable hypotheses and gradually remove the ones that do not match
class HypothesisChecker(DiagnosisAlgorithm):
    def __init__(self, name: str, adt: Type[ADTSubclass], include_create: bool) -> None:
        super().__init__(name)
        self.methods = list(adt.get_signature().keys())

        if not include_create:
            self.methods.remove("create")

        self.method_powerset = [list(x) for x in chain.from_iterable(
            combinations(self.methods, r) for r in range(len(self.methods) + 1)
        )]

    def diagnose(
            self,
            _: str,
            true_method_seqs: list[ResultSequence],
            obs_method_seqs: list[ResultSequence]
    ) -> DiagnosisResult:
        start_time = time.time()
        results = []

        # Iterate over all possible hypotheses and check if they are applicable
        for hypothesis in self.method_powerset:
            hypothesis_applicable = True

            # If a subset of the current hypothesis is already a solution, disregard the superset
            for valid_hypothesis in results:
                if set(valid_hypothesis) <= set(hypothesis):
                    hypothesis_applicable = False
                    break

            if not hypothesis_applicable:
                continue

            # Iterate over all provided traces and check for disagreements
            cut_obs = []
            cut_truth = []

            for i in range(len(true_method_seqs)):
                seq_obs = obs_method_seqs[i]
                seq_truth = true_method_seqs[i]

                # Cut the trace at the first component that can be abnormal according to the current hypothesis
                cutoff_index = -1
                for method_index, (method_name, _1, _2) in enumerate(seq_obs):
                    if method_name in hypothesis:
                        cutoff_index = method_index
                        break

                if cutoff_index == -1:
                    cutoff_index = len(seq_obs)

                # Append the truncated traces to the complete list
                cut_obs.append(seq_obs[:cutoff_index])
                cut_truth.append(seq_truth[:cutoff_index])

            # Check whether or not all truncated traces are errorless, otherwise we are missing an abnormal component
            for i in range(len(true_method_seqs)):
                if cut_obs[i] != cut_truth[i]:
                    hypothesis_applicable = False
                    break

            # Add matching hypotheses to the set of results
            if hypothesis_applicable:
                results.append(hypothesis)

        total_time = time.time() - start_time

        return results, total_time
