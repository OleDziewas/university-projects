package nio.readwrite.smallfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import nio.readwrite.LineNumberPrepender;

public class SmallFilesLineNumberPrepender implements LineNumberPrepender {

	@Override
	public void copyAndPrependLineNumbers(String input, String output) throws IOException {
		List<String> lines = Files.readAllLines(Path.of(input), StandardCharsets.UTF_8);
		for (int i = 0; i < lines.size(); i++) {
			lines.set(i, (i+1)+ " " + lines.get(i));
		}
		Files.write(Path.of(output), lines, StandardCharsets.UTF_8);
	}

}
