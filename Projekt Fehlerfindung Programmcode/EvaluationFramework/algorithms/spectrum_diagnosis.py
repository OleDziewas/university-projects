import time
import math
from typing import Type

from adts import ADTSubclass
from algorithms import DiagnosisAlgorithm, DiagnosisResult
from runners import ResultSequence


# Run spectrum based diagnosis by observing the relative frequency of the components in all of the traces
class SpectrumDiagnosis(DiagnosisAlgorithm):
    def __init__(self, name: str, adt: Type[ADTSubclass], threshold: float, metric_name: str, include_create: bool) -> None:
        super().__init__(name)
        self.methods = list(adt.get_signature().keys())

        if not include_create:
            self.methods.remove("create")

        self.threshold = threshold
        self.metric_name = metric_name
        self.metrics = {
            "tarantula": self.calc_tarantula,
            "jaccard": self.calc_jaccard,
            "ochiai": self.calc_ochiai,
            "zoltar": self.calc_zoltar,
            "op": self.calc_op,
            "o": self.calc_o,
            "kulczynski2": self.calc_kulczynski2,
            "mccon": self.mc_con,
            "dstar": self.dstar,
            "minus": self.minus
        }

    def diagnose(
        self,
        _: str,
        true_method_seqs: list[ResultSequence],
        obs_method_seqs: list[ResultSequence]
    ) -> DiagnosisResult:
        start_time = time.time()

        # Initialize dict for every ADT method with zero values
        trace_data = {
            method: {
                "cef": 0,
                "cnf": 0,
                "cep": 0,
                "cnp": 0,
                "score": 0.0
            } for method in self.methods
        }

        # Iterate over all of the given traces
        for seq_index in range(0, len(true_method_seqs)):
            true_seq = true_method_seqs[seq_index]
            obs_seq = obs_method_seqs[seq_index]

            # Find the first disagreement between the expected and observed traces
            diff_index = -1
            for i in range(0, len(true_seq)):
                if true_seq[i] != obs_seq[i]:
                    diff_index = i
                    break

            # If there is no disagreement, increment the "component executed in passed" and "component not executed in passed" counts
            if diff_index == -1:
                used_methods = [method for method, _, _ in true_seq]

                for method in self.methods:
                    if method in used_methods:
                        trace_data[method]["cep"] += 1
                    else:
                        trace_data[method]["cnp"] += 1
            else:
                # Otherwise, increment the "component executed in failed" and "componente not executed in failed" counts up to the disagreement
                used_methods = [method for method, _,
                                _ in true_seq[:diff_index + 1]]

                for method in self.methods:
                    if method in used_methods:
                        trace_data[method]["cef"] += 1
                    else:
                        trace_data[method]["cnf"] += 1

        # Calculate the score for each component using the predefined metric
        for method in self.methods:
            trace_data[method]["score"] = self.metrics[self.metric_name](
                trace_data[method]["cef"],
                trace_data[method]["cnf"],
                trace_data[method]["cep"],
                trace_data[method]["cnp"],
            )

        errors = [method for method in trace_data.keys(
        ) if trace_data[method]["score"] > self.threshold]

        total_time = time.time() - start_time

        return [errors], total_time

    def calc_tarantula(self, cef, cnf, cep, cnp):
        tarantula_top = cef / (cef + cnf + 1e-30)
        tarantula_bottom = tarantula_top + (cep / (cep + cnp + 1e-30))
        return (tarantula_top / (tarantula_bottom + 1e-30))

    def calc_ochiai(self, cef, cnf, cep, cnp):
        return (cef / (math.sqrt((cef + cnf) * (cef + cep)) + 1e-30))

    def calc_jaccard(self, cef, cnf, cep, cnp):
        return (cef / (cef + cnf + cep + 1e-30))

    def calc_zoltar(self, cef, cnf, cep, cnp):
        return (cef / (cef + cnf + cep + 10000 * ((cnf * cep) / (cef + 1e-30)) + 1e-30))

    def calc_op(self, cef, cnf, cep, cnp):
        return (cef - (cep / (cep + cnp + 1)))

    def calc_o(self, cef, cnf, cep, cnp):
        return (-1.0 if cnf > 0 else cnp)

    def calc_kulczynski2(self, cef, cnf, cep, cnp):
        return (0.5 * ((cef / (cef + cnf + 1e-30)) + (cef / (cef + cep + 1e-30))))

    def mc_con(self, cef, cnf, cep, cnp):
        return (((cef ** 2) - cnf * cep) / ((cef + cnf) * (cef + cep) + 1e-30))

    def dstar(self, cef, cnf, cep, cnp):
        return (cef ** 2) / (cnf + cep + 1e-30)

    def minus(self, cef, cnf, cep, cnp):
        term1 = cef / (cef + cnf + 1e-30)
        term2 = cep / (cep + cnp + 1e-30)
        return ((term1 / (term1 + term2 + 1e-30)) - ((1.0 - term1) / (1.0 - term1 + 1 - term2 + 1e-30)))
