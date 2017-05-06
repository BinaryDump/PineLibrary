
package be.iesca.daoimpl;

import java.util.HashMap;
import java.util.Map;

import javax.management.modelmbean.XMLParseException;

import be.iesca.dao.Dao;

/*
 * Une instance de cette classe encapsule les donn�es caract�risant un syst�me
 * de persistance. Elle sera soit de type MOCK, soit de type DB.
 * Si, elle est de type MOCK, les attributs driver, user et password ne seront
 * pas utilis�s.
 * Le parser assignera aux attributs les valeurs extraites du fichier XML. 
 */

public enum Persistance {
	MOCK, DB;

	// map contenant les noms des classes d'impl�mentation des daos (valeur)
	// la clef est le nom de l'interface dao qu'elle impl�mente
	private Map<String, String> mapDaos = new HashMap<String, String>(2);
	private String driver; // nom du driver jdbc
	private String url; // url d'acc�s � la db
	private String user; // login d'un utilisateur enregistr� dans la db
	private String password; // mot de passe de cet utilisateur

	// constructeur priv� (ne pas modifier)
	// utilisez la m�thode statique : creerInstance(String type)
	private Persistance() {
	}

	// cr�ation et initialisation d'une instance de type Persistance
	// d'un certain type
	public static Persistance creerInstance(String type) {
		Persistance persistance = Persistance.valueOf(type);

		// initialisation obligatoire car il r�utilise un objet pr�c�dent
		// car si d�j� pr�sent en m�moire et ne le r�initialise pas de lui-m�me!
		persistance.mapDaos.clear();
		persistance.driver = "";
		persistance.url = "";
		persistance.user = "";
		persistance.password = "";

		return persistance;
	}

	// le daoFactory appelera cette m�thode afin de charger
	// le driver jdbc sp�cifi� dans le fichier de configuration
	public void configurer() {
		switch (this) {
		case MOCK:
			break; // pas de driver � charger
		case DB:
			// Devenu inutile depuis le jdbc4, car il et charg� automatiquement
			// par le
			// driver manager. Obligatoire pour les versions pr�c�dentes.
			charger(this.driver);
			break;
		}
	}

	// chargement de la classe en m�moire
	private void charger(String nomClasse) {
		try {
			Class.forName(nomClasse);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// renvoie l'instance du dao dont on sp�cifie l'interface
	public Dao getDaoImpl(Class<? extends Dao> interfaceDao) {
		Dao dao = null;
		String nomInterfaceDao = "-";
		String nomDaoImpl = "-";
		try {
			nomInterfaceDao = interfaceDao.getName();
			nomDaoImpl = this.mapDaos.get(nomInterfaceDao);
			Class<?> classeDaoImpl = Class.forName(nomDaoImpl);
			dao = (Dao) classeDaoImpl.newInstance();
		} catch (NullPointerException ex) {
			System.out.println("Classe non pr�sente dans la map!");
			System.out.println("interface : " + nomInterfaceDao);
			System.out.println("classe    : " + nomDaoImpl);
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao;
	}

	@SuppressWarnings("unchecked")
	// ajout dans la map du nom de la classe d'implémentation d'un dao
	public void ajouterDaoImpl(String nomDaoImpl) throws XMLParseException,
			ClassNotFoundException {
		Class<Dao> classeDaoImpl;
		Class<?>[] interfaces;
		classeDaoImpl = (Class<Dao>) Class.forName(nomDaoImpl);
		interfaces = (Class<?>[]) classeDaoImpl.getInterfaces();
		for (Class<?> i : interfaces) {
			// teste si d�j� une impl�mentation de ce type
			String nomInterfaceDao = i.getName();
			if (this.mapDaos.containsKey(nomInterfaceDao)) {
				throw new XMLParseException(nomDaoImpl
						+ "--> apparait plusieurs"
						+ " fois dans le fichier de configuration!");
			}
			// teste si l'interface est de type Dao
			Class<?>[] ancetre = i.getInterfaces();
			if (ancetre.length == 1 && ancetre[0] != null
					&& ancetre[0].getName().equals(Dao.class.getName())) {
				this.mapDaos.put(nomInterfaceDao, nomDaoImpl);
			}
		}
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	protected Map<String, String> getMapDaos() {
		return this.mapDaos;
	}
}
