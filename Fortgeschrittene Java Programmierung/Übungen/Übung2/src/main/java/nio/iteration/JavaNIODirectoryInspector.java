package nio.iteration;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaNIODirectoryInspector implements DirectoryInspector{

	@Override
	public void inspect(String path) throws IOException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(path));
		for (Path entry:stream){
			if(Files.isDirectory(entry)){
				System.out.println("Verzeichnis");
			}else if(Files.isRegularFile(entry)){
				System.out.printf("Datei");
			}
		}
		stream.close();
	}

}
