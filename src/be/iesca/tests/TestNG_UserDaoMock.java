/* 
 * @Auteur: Quentin
 * @modifié: Sarah
 */
package be.iesca.tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import be.iesca.dao.UtilisateurDao;
import be.iesca.daoimpl.UserDaoMockImpl;
import be.iesca.domaine.Utilisateur;


public class TestNG_UserDaoMock {
 
	private static final String NOM_CALY = "Hussin";
	private static final String PRENOM_CALY = "Quentin";
	private static final String PASSWD_CALY = "aaa";
	private static final String USERNAME_CALY = "azerty";

	private static final String NOM_SARAH = "Bordin";
	private static final String PRENOM_SARAH = "Sarah";
	private static final String PASSWD_SARAH = "sarah";
	private static final String USERNAME_SARAH = "lalwende";

	private static final String NOM_TOTO = "Toto";
	private static final String PASSWD_TOTO = "toto";
	private static final String USERNAME_TOTO = "Tatoune";
	private static final String PRENOM_TOTO = "Toto";
	
	private List<Utilisateur>utilisateur;
	private UtilisateurDao userDao;
	
	@BeforeClass
	//initialisation dao + utilisateur
	public void init() {
		//userDao =(UtilisateurDao) DaoFactory.getInstance().getDaoImpl(UtilisateurDao.class);
		userDao = new UserDaoMockImpl();
		
		utilisateur = new ArrayList<Utilisateur>(3);
		//ajout des utilisateurs
		utilisateur.add(new Utilisateur("caly","quentin","Hussin","Quentin"));
		utilisateur.add(new Utilisateur("lalwende","sarah","Bordin","Sarah"));
		utilisateur.add(new Utilisateur("pythonit","ignazio","Noto","Ignazio"));
	}
	
	/*@author : Bordin Sarah*/
	@Test 
	//inscription utlisateur
	public void inscription() {
	
		assertTrue(userDao.ajouterUser(new Utilisateur("lalwende","sarah","Bordin","Sarah")));
	}
		
	@Test (dependsOnMethods={"inscription"})
	public void connexion() {
		Assert.assertNotNull(userDao.getUtilisateur("lalwende", "sarah"));
		Utilisateur user = userDao.getUtilisateur("lalwende", "sarah");
		assertEquals("lalwende",user.getLogin_user());
		assertEquals("sarah", user.getMotDePasse_user());

	}
	

	@Test
	//test d'échec de connexion d'un utlisateur inexistant 
	public void connexionUserNotExisting() {
		
		for(int i = 0; i<utilisateur.size(); i++){			
			
			Assert.assertNull(userDao.getUtilisateur(USERNAME_TOTO, PASSWD_TOTO));
		}
		
	}
}