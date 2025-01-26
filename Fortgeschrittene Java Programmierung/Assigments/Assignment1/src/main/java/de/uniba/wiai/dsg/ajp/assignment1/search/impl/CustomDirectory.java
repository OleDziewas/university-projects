package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomDirectory {
    private final Path path;
    private final String token;
    private final String fileExtension;

    private int tokenOccurences;

    public CustomDirectory(Path path, SearchTask task) {
        this.path = path;
        this.token = task.getToken();
        this.fileExtension = "*" + task.getFileExtension();
        this.tokenOccurences = 0;
    }

    public void checkFilesForToken(CustomWriter writer) throws IOException{
        //Dateien werden nacheinander nach dem Token durchsucht.
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(this.path, this.fileExtension)){
            for (Path entry : stream){
                if (Files.isRegularFile(entry)) {
                    findTokenInFile(entry, writer);
                }
            }
        }catch (IOException e){
            System.err.println("Fehler beim Oeffnen des DirectoryStream.");
            throw e;
        }
    }

    private void findTokenInFile(Path entry, CustomWriter writer){
        try {
            //Datei wird eingelesen.
            BufferedReader reader = Files.newBufferedReader(entry, StandardCharsets.UTF_8);
            String line = reader.readLine();
            int tokenInFileCount = 0;
            int lineCount = 1;
            while(line != null){
                //Wenn das Token in der Zeile vorkommt, wird untersucht, wie häufig und an welchen Stellen das Token vorkommt.
                if (line.contains(this.token)){
                    int i= 0;
                    //Einzelne Tokenfunde werden bearbeitet
                    while(line.indexOf(this.token, i)!=-1){
                        i = line.indexOf(this.token, i)+1;
                        //Ausgabe wird formatiert
                        String formattedLine = line.substring(0,i-1)+"**"+this.token+"**"+line.substring(i-1+this.token.length());
                        String outputLine = String.format("%s:%d,%d> %s", entry,lineCount, i-1, formattedLine);
                        System.out.println(outputLine);
                        writer.setStrToWrite(outputLine);
                        writer.writeInOutputfile();
                        tokenInFileCount++;
                    }
                }
                line = reader.readLine();
                lineCount++;
            }
            //Anzahl der gefunden Tokens wird ausgegeben.
            this.tokenOccurences += tokenInFileCount;
            String outputFile = String.format("%s includes **%s** %d times.%n", entry, this.token, tokenInFileCount);
            System.out.println(outputFile);
            writer.setStrToWrite(outputFile);
            writer.writeInOutputfile();
            reader.close();
        }catch(IOException e){
            System.err.println("Datei konnte nicht eingelesen werden: " + entry);
        }
    }

    public int getTokenOccurences(){
        return this.tokenOccurences;
    }
}
