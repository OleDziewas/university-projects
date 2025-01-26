package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SimpleTokenFinder implements TokenFinder {

	private final List<CustomDirectory> directories = new ArrayList<>();
	public SimpleTokenFinder() {
		/*
		 * DO NOT REMOVE
		 *
		 * REQUIRED FOR GRADING
		 */
	}

	@Override
	public void search(final SearchTask task) throws TokenFinderException {
		//Überprüfung der Parameter von task
		if (task.getRootFolder()==null || !Files.isDirectory(Path.of(task.getRootFolder()))){
			throw new TokenFinderException("Der angegebene Rootfolder ist fehlerhaft.");
		}
		if (task.getIgnoreFile()==null || !Files.isReadable(Path.of(task.getIgnoreFile()))){
			throw new TokenFinderException("Die angegebene Ignore-Datei existiert nicht oder ist nicht lesbar.");
		}
		if (task.getFileExtension() == null){
			throw new TokenFinderException("Es wurde keine FileExtension angegeben.");
		}
		if (task.getToken()== null || task.getToken().equals("")){
			throw new TokenFinderException("Es wurde kein Token angegeben");
		}
		if (task.getResultFile()==null || !Files.isWritable(Path.of(task.getResultFile()))){
			throw new TokenFinderException("Die angegebene Output-Datei existiert nicht oder ist nicht schreibbar.");
		}

		//CustomWriter zum schreiben der OutputFile erstellen
		CustomWriter writer = new CustomWriter(Path.of(task.getResultFile()));

		//Verzeichnisse finden, die nicht ignoriert werden, und zur Liste directories hinzufuegen.
		directories.add(new CustomDirectory(Path.of(task.getRootFolder()),task));
		try{
			findValidDirectories(Path.of(task.getRootFolder()), task, writer);
		}catch(IOException e){
			throw new TokenFinderException("Verzeichnis konnte nicht durchsucht werden.", e);
		}


		int sumOfTokenOccurences = 0;
		//Jedes Directory nach passenden Dateien durchsuchen und in diesen nach dem Token suchen.
		for (CustomDirectory directory : directories){
			try{
				directory.checkFilesForToken(writer);
				//Gesamtanzahl der gefundenen Tokens berechnen.
				sumOfTokenOccurences += directory.getTokenOccurences();
			}catch(IOException e){
				throw new TokenFinderException("Ordner konnten nicht nach Token durchsucht werden.", e);
			}
		}

		//Ergebnis der Gesamttokens ausgeben
		String outputTokenOccurences = String.format("The project includes **%s** %d times.", task.getToken(), sumOfTokenOccurences);
		System.out.print(outputTokenOccurences);
		writer.setStrToWrite(outputTokenOccurences);
		writer.writeInOutputfile();
	}

	//Rekursiv werden alle Unterordner zu directories hinzugefuegt, die nicht durch die Ignore-Datei gefiltert werden.
	private void findValidDirectories(Path path, final SearchTask task, CustomWriter writer) throws IOException{
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
			for (Path entry : stream){
				if (Files.isDirectory(entry)) {
					if(!directoryIgnored(entry, task, writer)) {
						//Hier geht die Funktion durch Rekursion eine Ebene tiefer im Verzeichnis.
						this.directories.add(new CustomDirectory(entry, task));
						findValidDirectories(entry, task, writer);
					}
				}
			}
		}catch (IOException e){
			System.err.println("Fehler beim Oeffnen des DirectoryStream.");
			throw e;
		}
	}

	//Ueberpruefung ob Verzeichnis in der Ignore-Datei steht.
	private boolean directoryIgnored(Path entry, final SearchTask task, CustomWriter writer){
		try {
			//Ingore.txt einlesen und ueberpruefen, ob der Name des Verzeichnisses in der Ignore-Datei steht.
			BufferedReader reader = Files.newBufferedReader(Path.of(task.getIgnoreFile()), StandardCharsets.UTF_8);
			String line = reader.readLine();
			while(line!= null){
				if (line.equals(entry.getFileName().toString())) {
					String outputDirectory = String.format("%s directory was ignored.%n", entry);
					System.out.println(outputDirectory);
					writer.setStrToWrite(outputDirectory);
					writer.writeInOutputfile();
					reader.close();
					return true;
				}
				line = reader.readLine();
			}
			reader.close();
			return false;

		//Fehler, falls die Datei nicht eingelesen werden kann.
		}catch(IOException e){
			System.err.println("Konnte Ignore-Datei nicht öffnen: " + task.getIgnoreFile());
			return false;
		}
	}

}