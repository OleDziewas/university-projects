package de.uniba.wiai.dsg.ajp.assignment2.literature;


import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.ConsoleHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		startMainMenu();
	}

	public static void startMainMenu(){
		//MainService wird gestartet
		MainService main = new MainServiceImpl();
		DatabaseService database;
		ConsoleHelper console = ConsoleHelper.build();
		String mainMenuString =
				"(1) Load and Validate Literature Database\n" +
				"(2) Create New Literature Database\n" +
				"(0) Exit System\n";
		int choice;
		//Wahl wird solange getroffen bis Programm beendet.
		try {
			choice = console.askIntegerInRange(mainMenuString, 0,2);
			while(choice != 0){
				if (choice == 1){
					//Datenbank wird aus bestehender Datei geladen.
					String answer = console.askNonEmptyString("Geben sie den Pfad zur Datenbank an: ");
					database = main.load(answer);
					main.validate(answer);
				}else{
					//Neue Datenbank wird erstellt.
					database = main.create();
				}
				startSubMenu(database, console);
				choice = console.askIntegerInRange(mainMenuString, 0,2);
			}
		} catch (IOException e) {
			System.err.println("Eingabe konnte nicht eingelesen werden");
		} catch (LiteratureDatabaseException e){
			System.err.println(e.getMessage());
		}
	}

	private static void startSubMenu(DatabaseService database, ConsoleHelper console){
		String subMenuString =
				"(1) Add Author\n" +
				"(2) Remove Author\n" +
				"(3) Add Publication\n" +
				"(4) Remove Publication\n" +
				"(5) List Authors\n" +
				"(6) List Publications\n" +
				"(7) Print XML on Console\n"+
				"(8) Save XML to File\n" +
				"(0) Back to main menu/close without saving\n";
		int choice;
		//Wahl wird solange getroffen, bis das Untermenue verlassen wird.
		try {
			choice = console.askIntegerInRange(subMenuString, 0, 8);
			while(choice != 0){
				switch (choice){
					case 1:
						//Autor wird hinzugefuegt.
						addAuthor(database, console);
						break;
					case 2:
						//Autor wird entfernt.
						String authorId = console.askNonEmptyString("Geben sie die ID des Autors an: ");
						database.removeAuthorByID(authorId);
						break;
					case 3:
						//Publikation wird hinzugefuegt
						addPublication(database, console);
						break;
					case 4:
						//Publikation wird entfernt.
						String publicationId = console.askNonEmptyString("Geben sie die ID der Publikation an: ");
						database.removePublicationByID(publicationId);
						break;
					case 5:
						//Autoren werden ausgegeben.
						List<Author> authors = database.getAuthors();
						authors.forEach(author -> System.out.println(author.getName()));
						break;
					case 6:
						//Publikationen werden ausgegeben
						List<Publication> publications = database.getPublications();
						publications.forEach(publication -> System.out.println(publication.getTitle()));
						break;
					case 7:
						//Datenbank wird auf Console ausgegeben
						database.printXMLToConsole();
						break;
					case 8:
						//Datenbank wird in XML-Datei gespeichert
						String path = console.askNonEmptyString("Geben sie den Namen der zu speichernden Datei ein: ");
						database.saveXMLToFile(path);
						break;
				}
				choice = console.askIntegerInRange(subMenuString, 0, 8);
			}
		}catch (IOException e) {
			System.err.println("Eingabe konnte nicht eingelesen werden");
		}catch(LiteratureDatabaseException e){
			System.err.println(e.getMessage());
		}
	}

	private static void addAuthor(DatabaseService database, ConsoleHelper console){
		//Argumente werden eingelesen und aufgearbeitet
		try {
			String name = console.askNonEmptyString("Geben Sie den Namen des Autors ein: ");
			String email = console.askNonEmptyString("Geben Sie die Email des Autors ein: ");
			String id = console.askNonEmptyString("Geben Sie die ID des Autors ein: ");
			database.addAuthor(name, email, id);
		}catch(IOException e){
			System.err.println("Eingabe konnte nicht gelesen werden.");
		}catch(LiteratureDatabaseException e){
			System.err.println(e.getMessage());
		}
	}

	private static void addPublication(DatabaseService database, ConsoleHelper console){
		//Argumente werden eingelesen und aufgearbeitet
		try {
			String title = console.askNonEmptyString("Geben Sie den Titel der Publikation ein: ");
			int yearPublished  = console.askInteger("Geben Sie das Veröffentlichungsjahr der Publikation ein: ");
			String type = console.askNonEmptyString("Geben sie den Typ der Publikation an: (Article, Techreport, Book, Masterthesis, Phdthesis oder inproceedings)");
			type = type.toUpperCase();
			String authorIDsAsString = console.askNonEmptyString("Geben sie die Autoren-IDs mit Komma getrennt an.");
			authorIDsAsString = authorIDsAsString.replaceAll(" ", "");
			String[] authorIDsAsArray = authorIDsAsString.split(",");
			List<String> authorIDs = Arrays.asList(authorIDsAsArray);
			String id = console.askNonEmptyString("Geben Sie die ID der Publikation ein: ");
			database.addPublication(title, yearPublished, PublicationType.valueOf(type),authorIDs,id);
		}catch(IOException e){
			System.err.println("Eingabe konnte nicht gelesen werden.");
		}catch(LiteratureDatabaseException e){
			System.err.println(e.getMessage());
		}catch(IllegalArgumentException e){
			System.err.println("Fehlerhafter Enum Typ");
		}
	}
}
