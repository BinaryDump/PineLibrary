/*
 * Test intégration
 * test le gestionnaire des utilisateurs
 * modifie : Ignazio
 */

package be.iesca.tests;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;
import be.iesca.usecase.GestionLivres;
import be.iesca.usecase.GestionUsers;
import be.iesca.usecaseimpl.GestionUsersImpl;
import be.iesca.usecaseimpl.GestionLivresImpl;

public class TestNG_GestionnaireUtilisateur {
     
	private static final String USERNAME_KITSUNE = "Kitsune";
	private static final String PASSWORD_KITSUNE = "kitsune";
	private static final String FIRSTNAME_KITSUNE = "Kitsune";
	private static final String LASTNAME_KITSUNE = "Renard";	
	private static final String PASSWD_TOTO = "toto";
	private static final String USERNAME_TOTO = "Tatoune";	
	private GestionnaireUseCases gestionnaire;
	private Bundle bundle;
	private Utilisateur user;
	private GestionUsers gestionUser;
	private GestionLivres gestionLivres;
	
	/*@Author : Quentin*/
	@BeforeClass
	//initialisation bundle, gestonnaire
	public void init() {
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.bundle = new Bundle();
		this.user = new Utilisateur();
		this.gestionUser = new GestionUsersImpl();
		this.gestionLivres=new GestionLivresImpl();
	}
	
	/*@Author : Quentin*/
	@Test
	//enregistrement utilisateur
	public void enregistrement() {
		
		this.user.setLogin_user(USERNAME_KITSUNE);
		this.user.setMotDePasse_user(PASSWORD_KITSUNE);
		this.user.setNom_user(LASTNAME_KITSUNE);
		this.user.setPrenom_user(FIRSTNAME_KITSUNE);
		this.bundle.put(Bundle.USER, this.user);
		this.gestionnaire.ajouterUser(this.bundle);
		
		Utilisateur utilisateurDb = (Utilisateur) this.bundle.get(Bundle.USER);
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(utilisateurDb);
		Assert.assertEquals(this.user, utilisateurDb);
		
	}
	
	/*@Author : Quentin*/
	@Test(dependsOnMethods = {"enregistrement"})
	//connexion utilisateur
	public void connexion() {
		this.user = new Utilisateur();
		this.bundle.put(Bundle.USER, null);
		
		this.user.setLogin_user(USERNAME_KITSUNE);
		this.user.setMotDePasse_user(PASSWORD_KITSUNE);
		this.user.setNom_user(LASTNAME_KITSUNE);
		this.user.setPrenom_user(FIRSTNAME_KITSUNE);
		
		this.bundle.put(Bundle.USER, this.user);
		this.gestionnaire.connecterUser(this.bundle);
		Utilisateur utilisateurDb = (Utilisateur) this.bundle.get(Bundle.USER);
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(this.bundle.get(Bundle.USER));
		Assert.assertEquals(utilisateurDb, this.user);
		
	}
	
	// @author: Quentin
	@Test(dependsOnMethods = {"connexion"})
	//lister les utilisateur du système
	public void listeUtilisateur() {
		this.gestionUser.listerUser(this.bundle);
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(this.bundle.get(Bundle.LISTE));
		for(Utilisateur u: (List<Utilisateur>) this.bundle.get(Bundle.LISTE)) {
			Assert.assertEquals(u.getLogin_user(), "Kitsune");
		}
	}
	
	/*@Author : Quentin*/
	@Test (dependsOnMethods={"listeUtilisateur"})
	//deconnection d'un utilisateur
	public void deconnexion() {
		this.gestionUser.deconnecterUser(this.bundle);
		Assert.assertNull(bundle.get(Bundle.USER));
		Assert.assertTrue((Boolean) bundle.get(Bundle.OPERATION_REUSSIE));
	}
	
	/*@Author : Quentin*/
	@Test (dependsOnMethods={"deconnexion"})
	//test d'échec de connexion d'un utilisateur inexistant
	public void connexionUserNotExisting() {
		Utilisateur user = new Utilisateur();
		user.setLogin_user(USERNAME_TOTO);
		user.setMotDePasse_user(PASSWD_TOTO);
		this.bundle.put(Bundle.USER, user);
		this.gestionUser.connecterUser(this.bundle);
		Assert.assertFalse((Boolean) bundle.get(Bundle.OPERATION_REUSSIE));
	}
}
	
	