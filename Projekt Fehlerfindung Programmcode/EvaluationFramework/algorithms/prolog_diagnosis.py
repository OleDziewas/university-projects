import subprocess
import time
from itertools import chain, combinations
from typing import Type

from adts import ADTSubclass
from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Use a version of the original ASP approach to determine the smallest set of abnormal components explaining every fault
class PrologDiagnosis(DiagnosisAlgorithm):
    def __init__(self, name: str, adt: Type[ADTSubclass], prolog_file: str, include_create: bool) -> None:
        super().__init__(name)
        self.methods = list(adt.get_signature().keys())

        if not include_create:
            self.methods.remove("create")

        self.method_powerset = [list(x) for x in chain.from_iterable(
            combinations(self.methods, r) for r in range(len(self.methods) + 1)
        )]

        with open(prolog_file, "r") as f:
            self.prolog_spec = f.read()

    def diagnose(
            self,
            _: str,
            true_method_seqs: list[ResultSequence],
            obs_method_seqs: list[ResultSequence]
    ) -> DiagnosisResult:
        start_time = time.time()
        results = []

        # Collect all of the traces with disagreements and cut them off at the first error
        wrong_traces = []

        for i in range(len(true_method_seqs)):
            seq_obs = obs_method_seqs[i]
            seq_truth = true_method_seqs[i]

            # Check whether the observed and expected traces match
            if seq_obs == seq_truth:
                continue

            # If they do not match, add the observed trace to the set of failed traces
            cutoff_index = len(seq_obs)
            for j in range(len(seq_obs)):
                if seq_obs[j] != seq_truth[j]:
                    cutoff_index = j
                    break

            wrong_traces.append(seq_obs[:cutoff_index + 1])

        if not wrong_traces:
            return [[]], (time.time() - start_time)

        # Remove duplicate traces
        deduplicated = []
        dedup_keys = []

        for trace in wrong_traces:
            dedup_key = " / ".join([x for (x, _, _) in trace])

            if dedup_key not in dedup_keys:
                deduplicated.append(trace)
                dedup_keys.append(dedup_key)

        # Automatically create a prolog program representing all of the failed traces
        counter = 0
        test_data = "main :-\n"
        for wrong in deduplicated:
            s = f"  create({wrong[0][1][0]}, V{counter})"
            counter += 1

            for op in wrong[1:]:
                if op[0] == "pop":
                    s += f", pop(V{counter - 1}, '{op[2]}', V{counter})"
                    counter += 1
                elif op[0] == "push":
                    s += f", push(V{counter - 1}, '{op[1][0]}', V{counter})"
                    if op[2] != "error":
                        s += f", V{counter} \\= error"
                    else:
                        s += f", V{counter} == error"
                    counter += 1
                elif op[0] == "is_full":
                    s += f", isfull(V{counter - 1}, {str(op[2]).lower()})"
                elif op[0] == "is_empty":
                    s += f", isempty(V{counter - 1}, {str(op[2]).lower()})"
            test_data += s + ', !,\n'
        test_data += '  write("Success"), halt.\nmain :- write("Fail"), halt.'

        # Iterate over all possible diagnosis results
        for hypothesis in self.method_powerset:
            hypothesis_applicable = True

            # Supress supersets of existing diagnosis results
            for valid_hypothesis in results:
                if set(valid_hypothesis) <= set(hypothesis):
                    hypothesis_applicable = False
                    break

            if not hypothesis_applicable:
                continue

            # Declare all of the components in the current hypothesis as abnormal
            abnormalities = "\n".join(
                [f'ab({method.replace("_", "").lower()}).' for method in hypothesis]) + "\n\n"

            # Write the complete program to a file
            with open("assets/diag.pl", "w") as diag:
                diag.write(abnormalities if len(hypothesis)
                           != 0 else "ab(_) :- fail.\n")
                diag.write(self.prolog_spec)
                diag.write(test_data)

            # Check whether or not the current hypothesis explains all of the erroneous traces
            try:
                result = subprocess.check_output(
                    ["swipl", "assets/diag.pl"], stderr=subprocess.PIPE)
                if result == b"Success":
                    results.append(hypothesis)
            except:
                pass

        total_time = time.time() - start_time

        return results, total_time
