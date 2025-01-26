package nio.readwrite.largefiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import nio.readwrite.LineNumberPrepender;

public class LargeFilesLineNumberPrepender implements LineNumberPrepender {

	@Override
	public void copyAndPrependLineNumbers(String input, String output) throws IOException {
		// TODO implementiere hier
		BufferedReader reader = Files.newBufferedReader(Path.of(input), StandardCharsets.UTF_8);
		BufferedWriter writer = Files.newBufferedWriter(Path.of(output), StandardCharsets.UTF_8);

		int i = 1;
		String line = reader.readLine();
		while(line != null){
			line = reader.readLine();
			writer.write(i+line);
			writer.newLine();
			i++;
		}

		writer.close();
	}
}
