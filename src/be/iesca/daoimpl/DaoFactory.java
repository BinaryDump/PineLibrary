/*Author Bordin Sarah Version 1*/

package be.iesca.daoimpl;
import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import be.iesca.dao.Dao;
/*
 * Classe charg�e de fournir les instances des daos
 * Dans cette application, il n'y en a qu'un seul.
 */

public class DaoFactory {

	private static final DaoFactory INSTANCE = new DaoFactory();
	private static final String FICHIER_CONFIGURATION = "config.xml";

	private Persistance persistance;
	private BoneCP connectionPool = null;
	
	public static DaoFactory getInstance() {
		return INSTANCE;
	}
	
	private DaoFactory() {
		ParserConfig parser = new ParserConfig();
		try {
			this.persistance = parser.lireConfiguration(FICHIER_CONFIGURATION);
			this.persistance.configurer();
			this.connectionPool = creationConnectionPool();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private BoneCP creationConnectionPool() {
		BoneCP connectionPool = null;
	      try {
	            /*
	             * Cr�ation d'une configuration de pool de connexions via l'objet
	             * BoneCPConfig et les diff�rents setters associ�s.
	             */
	            BoneCPConfig config = new BoneCPConfig();
	            /* Mise en place de l'URL, du nom et du mot de passe */
	            config.setJdbcUrl( persistance.getUrl() );
	            config.setUsername( persistance.getUser() );
	            config.setPassword( persistance.getPassword() );
	            /* Param�trage de la taille du pool */
	            config.setMinConnectionsPerPartition( 5 );
	            config.setMaxConnectionsPerPartition( 10 );
	            config.setPartitionCount( 2 );
	            /* Cr�ation du pool � partir de la configuration, via l'objet BoneCP */
	            connectionPool = new BoneCP( config );
	        } catch ( SQLException e ) {
	            e.printStackTrace();
	        }
		return connectionPool;
	}
	
	// renvoie une connexion 
	public Connection getConnexion() {
		Connection connection = null;
		try {
			connection = this.connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


	public Dao getDaoImpl(Class<? extends Dao> interfaceDao) {
		return this.persistance.getDaoImpl(interfaceDao);
	}

}
