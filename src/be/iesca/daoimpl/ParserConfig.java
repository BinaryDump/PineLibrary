package be.iesca.daoimpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ParserConfig {
	private static final String PERSISTANCE = "persistance";
	private static final String TYPE = "type";
	private static final String DAO_IMPL = "dao";
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";

	// Méthode chargée de construire un objet de type Persistance.
	// Elle initialise ses attributs avec les valeurs extraites du fichier.
	// Elle reçoit en paramètre le nom du fichier de configuration.
	// Elle lance une exception de type XMLParseException si le fichier
	// de configuration est incorrect.
	@SuppressWarnings({ "unchecked" })
	public Persistance lireConfiguration(String fichierConfiguration)
			throws XMLParseException, ClassNotFoundException {
		Persistance persistance = null;
		try {
			// Création d'une instance de XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			// Création d'un eventReader
			InputStream in = new FileInputStream(fichierConfiguration);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			// Lecture du fichier xml
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();

					// Création d'un objet de type Persistance si la balise
					// <persistance> a été trouvée.
					if (startElement.getName().getLocalPart() == PERSISTANCE) {

						// Lecture de l'attributs spécifiant le type de
						// persistance
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						if (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(TYPE)) {
								String type = attribute.getValue();
								try {
									persistance = Persistance.creerInstance(type);
								} catch (Exception ex) {
									throw new XMLParseException(
											"Type de systeme de persistance inconnu : "
													+ attribute.getValue());
								}
							}
						} else {
							throw new XMLParseException(
									"Dans la balise <persistance>,"
											+ " il manque l'attribut spécifiant le type de persistance");
						}
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(DAO_IMPL)) {
							event = eventReader.nextEvent();
							persistance.ajouterDaoImpl(event.asCharacters()
									.getData());
							continue;
						}
					}
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(DRIVER)) {
							event = eventReader.nextEvent();
							persistance.setDriver(event.asCharacters()
									.getData());
							continue;
						}
					}
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(URL)) {
							event = eventReader.nextEvent();
							persistance.setUrl(event.asCharacters().getData());
							continue;
						}
					}
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(USER)) {
							event = eventReader.nextEvent();
							persistance.setUser(event.asCharacters().getData());
							continue;
						}
					}
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(PASSWORD)) {
							event = eventReader.nextEvent();
							persistance.setPassword(event.asCharacters()
									.getData());
							continue;
						}
					}
				}
				// renvoie l'objet de persistance quand il rencontre la balise
				// de fin
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == PERSISTANCE) {
						if (persistance.getMapDaos().isEmpty())
							throw new XMLParseException(
									"Pas de dao chargé!");
						switch (persistance) {
						case MOCK:
							return persistance;
						case DB: {
							if (persistance.getDriver().isEmpty())
								throw new XMLParseException(
										"Il manque la balise <driver>");
							if (persistance.getUrl().isEmpty())
								throw new XMLParseException(
										"Il manque la balise <url>");
							if (persistance.getUser().isEmpty())
								throw new XMLParseException(
										"Il manque la balise <user>");
							if (persistance.getPassword().isEmpty())
								throw new XMLParseException(
										"Il manque la balise <password>");
							return persistance;
						}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		throw new XMLParseException("Fichier de configuration incorrect"); 
	}
}