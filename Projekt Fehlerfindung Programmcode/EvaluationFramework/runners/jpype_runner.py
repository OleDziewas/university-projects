import jpype

from generators import MethodSequence
from runners import Runner, ResultSequence


# Run method sequences on precompiled submissions in a jar file
class JPypeRunner(Runner):
    # Initialize a JVM instance in the background while loading the submissions from the given jar file
    def __init__(self, submissions_file: str) -> None:
        super().__init__()
        jpype.startJVM(classpath=[submissions_file])

        system_class = jpype.JClass("java.lang.System")
        print_stream_class = jpype.JClass("java.io.PrintStream")
        file_class = jpype.JClass("java.io.File")

        system_class.setOut(print_stream_class(file_class("/dev/null")))
        system_class.setErr(print_stream_class(file_class("/dev/null")))

    # Execute each method sequence in the list individually and provide the results as a list again
    def execute_sequences(self, submission_id: str, sequences: list[MethodSequence]) -> list[ResultSequence]:
        result_sequences = []

        for sequence in sequences:
            result_sequences.append(self.execute_sequence(submission_id, sequence))

        return result_sequences

    # Run one method sequence on a given fully qualified class name in the jar file given in the constructor
    def execute_sequence(self, submission_id: str, sequence: MethodSequence) -> ResultSequence:
        # Use the first method to call the constructor
        create_params = sequence[0][1]

        # Convert the submission id to the corresponding fully qualified java class name
        id_year = submission_id[:4]
        id_user = submission_id[4:11].replace("anon", "Anon")
        id_run = submission_id[11:]
        submission_class_name = f"compiled.year{id_year}.user{id_user}.run{id_run}.PublicAPI"
        # submission_class_name = f"compiled.name{submission_id}.PublicAPI"
        submission_stack = jpype.JClass(submission_class_name)(*create_params)

        # Then execute the remaining method calls
        result_sequence = [("create", create_params, None)]
        result_sequence += self._execute_implementation(submission_stack, sequence[1:], True)

        return result_sequence
