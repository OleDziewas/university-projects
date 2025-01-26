package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CustomWriter {

    private final List<String> strToWrite = new ArrayList<>();
    private final Path outputPath;

    public CustomWriter(Path outputPath){
        this.outputPath = outputPath;
    }

    public void setStrToWrite(String str){
        this.strToWrite.add(str);
    }

    public void writeInOutputfile(){
        try {
            Files.write(outputPath, strToWrite, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        }catch(IOException e){
            System.err.println("Output-Datei konnte nicht geoeffnet oder erstellt werden: "+ outputPath);
        }
    }
}
