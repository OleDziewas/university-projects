import random
import time
from adts.stack import Stack
from algorithms.asp_wrapper import ASPDiagnosisWrapper
from algorithms.manual_wrapper import ManualDiagnosisWrapper
from algorithms.hitting_set import HittingSetDiagnosis
from algorithms.hypo_check import HypothesisChecker
from algorithms.prolog_diagnosis import PrologDiagnosis
from algorithms.spectrum_diagnosis import SpectrumDiagnosis
from generators.adt_equations import ADTEquationGenerator
from generators.adt_signature import ADTSignatureGenerator
from generators.allseq import AllPossibilitiesGenerator
from generators.random import RandomGenerator
from generators.symbolic_execution import SymbolicExecutionGenerator
from metrics.precision_recall import PrecisionRecallMetric
from runners.jpype_runner import JPypeRunner
from runners.reference_runner import ReferenceRunner
from visualization.console_visualizer import ConsoleVisualizer

if __name__ == "__main__":
    start_time = time.time()
    random.seed(42)

    # Manual annotations (assumed to be the ground truth)
    manual_diagnosis = ManualDiagnosisWrapper(
        "Manual Diagnosis", "assets/manual-results.json"
    )

    # All available diagnosis algorithms
    asp_diag = ASPDiagnosisWrapper("ASP Diagnosis", "assets/asp-results.json")
    hit_diag = HittingSetDiagnosis("Hitting Set", True)
    hypo_diag = HypothesisChecker("Hypothesis Checker", Stack, True)
    prolog_diag = PrologDiagnosis("Prolog Diagnosis", Stack, "assets/stack.pl", True)
    tarantula_diag = SpectrumDiagnosis(
        "Tarantula Diagnosis", Stack, 0.5, "tarantula", True
    )
    ochiai_diag = SpectrumDiagnosis("Ochiai Diagnosis", Stack, 0.5, "ochiai", True)
    mccon_diag = SpectrumDiagnosis("McCon Diagnosis", Stack, 0.0, "mccon", True)

    # Initialize test case generator
    generator = SymbolicExecutionGenerator(Stack)
    method_seqs = generator.create_sequences()
    print(f"Testing with {len(method_seqs)} test cases")

    # Execute method sequences using the JPype and reference runners
    jpype_runner = JPypeRunner("assets/submissions.jar")
    reference_runner = ReferenceRunner(Stack)

    diags = [asp_diag, hypo_diag, prolog_diag, tarantula_diag, ochiai_diag, mccon_diag]

    # Visualization
    console_vis = ConsoleVisualizer(
        manual_diagnosis, diags, PrecisionRecallMetric(Stack)
    )

    # Iterate over all submissions with available ground truth
    for i, submission_id in enumerate(manual_diagnosis.available_submissions()):
        print(i, submission_id)

        all_jtraces = []
        all_rtraces = []

        for method_seq in method_seqs:
            jpype_trace = jpype_runner.execute_sequence(submission_id, method_seq)
            ref_trace = reference_runner.execute_sequence(method_seq)
            all_jtraces.append(jpype_trace)
            all_rtraces.append(ref_trace)

        console_vis.add_data(submission_id, all_rtraces, all_jtraces)

    console_vis.visualize()
    print(f"Took {time.time() - start_time} to test with {len(method_seqs)} test cases")
