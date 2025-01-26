package nio.iteration;

import java.io.IOException;

public interface DirectoryInspector {

	void inspect(String path) throws IOException;

}
