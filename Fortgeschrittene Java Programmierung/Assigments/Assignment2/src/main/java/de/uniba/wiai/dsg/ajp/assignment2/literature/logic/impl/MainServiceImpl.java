package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class MainServiceImpl implements MainService {

	/**
	 * Default constructor required for grading.
	 */
	private JAXBContext context;
	public MainServiceImpl() {
		/*
		 * DO NOT REMOVE - REQUIRED FOR GRADING
		 * 
		 * YOU CAN EXTEND IT BELOW THIS COMMENT
		 */
	}

	@Override
	public void validate(String path) throws LiteratureDatabaseException {
		//Überprüfung des Eingabeparameters path
		if (path == null || path.equals("")){
			throw new LiteratureDatabaseException("Leerer oder nicht angegebener Pfad");
		}
		//XML Datei wird vailidiert
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = sf.newSchema(new File("schema1.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(path)));
		}catch(SAXException | IOException e){
			throw new LiteratureDatabaseException("Validierung war fehlerhaft.", e);
		}
	}

	@Override
	public DatabaseService load(String path) throws LiteratureDatabaseException {
		//Überprüfung des Eingabeparameters path
		if (path == null || path.equals("")){
			throw new LiteratureDatabaseException("Leerer oder nicht angegebener Pfad");
		}

		//Leere Datenbank wird erstellt.
		Database database;
		try {
			this.context = JAXBContext.newInstance(Database.class);
			Unmarshaller um = context.createUnmarshaller();
			//Datenbank wird aus XML-Datei geladen.
			database = (Database) um.unmarshal(new File(path));
		} catch (JAXBException | IllegalArgumentException e) {
			throw new LiteratureDatabaseException("Datei konnte nicht unmarshalt werden.", e);
		}
		//Der DatabaseService wird erstellt und die Datenbank übergeben.
		DatabaseServiceImpl dbImpl = new DatabaseServiceImpl();
		dbImpl.setDatabase(database);
		dbImpl.setContext(this.context);
		return dbImpl;
	}

	@Override
	public DatabaseService create() throws LiteratureDatabaseException {
		//Leere Datenbank wird erstellt und Interface wird übergeben
		try {
			this.context = JAXBContext.newInstance(Database.class);
		} catch (JAXBException e) {
			throw new LiteratureDatabaseException("JAXBContext konnte nicht geladen werden", e);
		}
		DatabaseServiceImpl dbImpl = new DatabaseServiceImpl();
		Database database = new Database();
		dbImpl.setDatabase(database);
		dbImpl.setContext(this.context);
		return dbImpl;
	}

}
