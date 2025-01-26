package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.ValidationHelper;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class DatabaseServiceImpl implements DatabaseService {

	private Database database;
	private JAXBContext context;

	public void setDatabase(Database database) {
		this.database = database;
	}

	public void setContext(JAXBContext context) {
		this.context = context;
	}

	@Override
	public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
			throws LiteratureDatabaseException {
		// Eingabeparameter werden ueberprueft.
		try {
			validatePublicationVariables(title, yearPublished, type, authorIDs, id);
		}catch(LiteratureDatabaseException e){
			throw new LiteratureDatabaseException("Eingabeparameter waren fehlerhaft.",e);
		}

		//Die Werte der Publikation werden gesetzt.
		Publication publication = new Publication();
		publication.setTitle(title);
		publication.setYearPublished(yearPublished);
		publication.setType(type);
		publication.setId(id);

		//Wenn ein Autor der Datenbank eine ID hat, die sich in authorIDs befindet,
		//wird er zu den Autoren der Publikation hinzugefügt.
		List<Author> authors = new LinkedList<>();
		for (Author author : database.getAuthors()){
			if (authorIDs.contains(author.getId())){
				authors.add(author);
				//Dem Autor wird die Publikation hinzugefügt.
				List<Publication> authorsPublications = author.getPublications();
				authorsPublications.add(publication);
				author.setPublications(authorsPublications);
			}
		}
		if (authors.isEmpty()){
			throw new LiteratureDatabaseException("Kein korrekter Autor angegeben.");
		}
		publication.setAuthors(authors);
		//Publikation wird zur Datenbank hinzugefügt.
		List<Publication> databasePublications = database.getPublications();
		databasePublications.add(publication);
		database.setPublications(databasePublications);
	}

	private void validatePublicationVariables(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id) throws LiteratureDatabaseException{
		if (title== null|| title.equals("")) {
			throw new LiteratureDatabaseException("Fehlerhafter Titel.");
		}
		if(yearPublished <0){
			throw new LiteratureDatabaseException("Fehlerhaftes Erscheinungsjahr.");
		}
		if (type == null){
			throw new LiteratureDatabaseException("Fehlerhafte Publikationstyp.");
		}
		if (isAuthorsIDsFalse(authorIDs)){
			throw new LiteratureDatabaseException("Fehlerhafte Autoren IDs.");
		}
		if (isPublicationIDFalse(id)){
			throw new LiteratureDatabaseException("Fehlerhafte Publiaktions ID.");
		}
	}

	private boolean isAuthorsIDsFalse(List<String> authorIDs){
		if(authorIDs == null || authorIDs.size()== 0){
			return true;
		}
		//Falls Author ID zwei Mal vorkommt wird true zurueckgegeben
		List<String> authorIDCopy = new LinkedList<>(authorIDs);
		for (String authorID:authorIDs){
			authorIDCopy.remove(authorID);
			if (authorIDCopy.contains(authorID)){
				return true;
			}
		}
		return false;
	}
	private boolean isPublicationIDFalse(String id){
		if (id == null || id.equals("") || !ValidationHelper.isId(id)){
			return true;
		}
		//Wenn ID schon vorhinden wird eine Exception geworfen
		List<Publication> publications = database.getPublications();
		for (Publication publication : publications){
			if (publication.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void removePublicationByID(String id) throws LiteratureDatabaseException {
		//Werte werden ueberprueft.
		if (id == null || id.equals("") || !ValidationHelper.isId(id)){
			throw new LiteratureDatabaseException("Fehlerhafte ID.");
		}
		//Löschen der Publikation in der Datenbank.
		List<Publication> databasePublications = database.getPublications();
		Publication publicationToBeRemoved = null;
		for(Publication publication : databasePublications){
			if (publication.getId().equals(id)){
				publicationToBeRemoved = publication;
				break;

			}
		}
		if(publicationToBeRemoved!= null) {
			databasePublications.remove(publicationToBeRemoved);
			//Löschen der Publikation bei den Autoren.
			List<Author> authorsOfPublication = publicationToBeRemoved.getAuthors();
			for (Author author : authorsOfPublication) {
				List<Publication> publicationsOfAuthor = author.getPublications();
				publicationsOfAuthor.remove(publicationToBeRemoved);
				author.setPublications(publicationsOfAuthor);
			}
			database.setPublications(databasePublications);
		}
	}

	@Override
	public void removeAuthorByID(String id) throws LiteratureDatabaseException {
		// Werte werden ueberprueft.
		if (id == null || id.equals("") || !ValidationHelper.isId(id)){
			throw new LiteratureDatabaseException("Fehlerhafte ID.");
		}
		//Löschen der Publikation in der Datenbank
		List<Author> databaseAuthors = database.getAuthors();
		Author authorToBeRemoved = null;
		for(Author author : databaseAuthors){
			if (author.getId().equals(id)){
				authorToBeRemoved = author;
				break;
			}
		}
		if(authorToBeRemoved!= null) {
			databaseAuthors.remove(authorToBeRemoved);
			//Löschen der Publikation bei den Autoren
			List<Publication> publicationsOfAuthor = authorToBeRemoved.getPublications();
			for (Publication publication : publicationsOfAuthor) {
				List<Author> authorsOfPublication = publication.getAuthors();
				authorsOfPublication.remove(authorToBeRemoved);
				publication.setAuthors(authorsOfPublication);
			}
			database.setAuthors(databaseAuthors);
		}
	}

	@Override
	public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {
		//Werte werden ueberprueft.
		if (name == null || name.equals("")){
			throw new LiteratureDatabaseException("Name ist fehlerhaft");
		}
		if (email == null || email.equals("") || !ValidationHelper.isEmail(email)){
			throw new LiteratureDatabaseException("Email ist fehlerhaft");
		}
		if (isAuthorIDFalse(id)){
			throw new LiteratureDatabaseException("Die Autor ID ist fehlerhaft.");
		}
		//Werte des Autors werden gesetzt.
		Author author = new Author();
		author.setName(name);
		author.setEmail(email);
		author.setId(id);

		//Autor wird in der Datenbank gesetzt.
		List<Author> databaseAuthors = database.getAuthors();
		databaseAuthors.add(author);
		database.setAuthors(databaseAuthors);
	}
	private boolean isAuthorIDFalse(String id){
		if (id == null || id.equals("") || !ValidationHelper.isId(id)){
			return true;
		}
		//Wenn ID schon vorhinden wird eine Exception geworfen
		List<Author> authors = database.getAuthors();
		for (Author author : authors){
			if (author.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Publication> getPublications() {
		return database.getPublications();
	}

	@Override
	public List<Author> getAuthors() {
		return database.getAuthors();
	}

	@Override
	public void clear() {
		//Löscht alle Elemente in der Datenbank.
		database.setPublications(new LinkedList<>());
		database.setAuthors(new LinkedList<>());
	}

	@Override
	public void printXMLToConsole() throws LiteratureDatabaseException {
		//database wird gemarshalt und auf die Konsole ausgegeben.
		try {
			Marshaller ms = this.context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(this.database, System.out);
		} catch (JAXBException e) {
			throw new LiteratureDatabaseException("Klasse konnte nicht gemarshalt werden.", e);
		}

	}

	@Override
	public void saveXMLToFile(String path) throws LiteratureDatabaseException {
		// database wird gemarshalt und in einer XML-Datei gespeichert.
		try {
			Marshaller ms = this.context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(this.database, new File(path));
		} catch (JAXBException e) {
			throw new LiteratureDatabaseException("Klasse konnte nicht gemarshalt werden.", e);
		}
	}

}
