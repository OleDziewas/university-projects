package profiling.fibonacci;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FibonacciCalculator {

	private long first;

	private long second;

	private String pathToResultFile;

	private long[] results;

	public FibonacciCalculator(String pathToResultFile) {
		this.pathToResultFile = pathToResultFile;
	}

	public long calculateFibonacciNumber(int index) throws IOException {
		if (index <= 0) {
			throw new IllegalArgumentException(
					"Index number must be positive, but was " + index);
		}

		if (index == 1) {
			return 0;
		} else if (index == 2) {
			return 1;
		}

		results = new long[index];
		first = 0;
		second = 1;

		for (int i = 1; i < index - 1; i++) {
			calculateNextStep(i);
		}

		writeResultToFile();
		return determineResult();
	}


	public void calculateNextStep(int index) {
		if (first > second) {
			second += first;
			results[index] = second;
		} else {
			first += second;
			results[index] = first;
		}
	}

	private long determineResult() {
		if (first > second) {
			return first;
		} else {
			return second;
		}
	}

	private void writeResultToFile() throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(
				Paths.get(pathToResultFile), Charset.defaultCharset(),
				StandardOpenOption.CREATE)) {
			for(int i = 0; i< results.length;i++){
				writer.write("["+i+"] "+results[i]);
				writer.newLine();
			}
			
		}
	}

}
