package nio.readwrite.largefiles;

import java.io.IOException;

public class LargeFilesMain {

	public static void main(String[] args) throws IOException {
		new LargeFilesLineNumberPrepender().copyAndPrependLineNumbers("input.txt","output-large-files.txt");
	}

}
