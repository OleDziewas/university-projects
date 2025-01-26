package nio.readwrite.smallfiles;

import java.io.IOException;

public class SmallFilesMain {

    public static void main(String[] args) throws IOException {
        new SmallFilesLineNumberPrepender().copyAndPrependLineNumbers("input.txt","output-small-files.txt");
    }

}
