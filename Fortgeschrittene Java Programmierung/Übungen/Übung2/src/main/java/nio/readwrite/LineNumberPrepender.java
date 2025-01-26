package nio.readwrite;

import java.io.IOException;

public interface LineNumberPrepender {

	void copyAndPrependLineNumbers(String input, String output) throws IOException;

}
