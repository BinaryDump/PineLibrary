/*@author : Bordin Sarah
 * Version :1
 */
package be.iesca.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;
import be.iesca.usecase.GestionLivres;
import be.iesca.usecase.GestionUsers;
import be.iesca.usecaseimpl.GestionLivresImpl;
import be.iesca.usecaseimpl.GestionUsersImpl;

public class TestNG_GestionnaireLivre {
	
	private static final String USERNAME_KITSUNE = "Kitsune";
	private static final String PASSWORD_KITSUNE = "kitsune";
	private static final String FIRSTNAME_KITSUNE = "Kitsune";
	private static final String LASTNAME_KITSUNE = "Renard";
	
	private static final String USERNAME_LALWENDE = "Lalwende";
	private static final String PASSWORD_LALWENDE = "lalwende";
	private static final String FIRSTNAME_LALWENDE = "Sarah";
	private static final String LASTNAME_LALWENDE = "Lalwende";
	
	private static final String TITRE_COBEN = "Ne le dis à personne";
	private static final String AUTEUR_COBEN = "Harlan Coben";
	private static final String EDITEUR_COBEN= "Pocket";
	private static final String	ISBN_COBEN= "226612515";
	
	private static final String TITRE_BROWN = "Da vinci code";
	private static final String AUTEUR_BROWN = "Dan Brown";
	private static final String EDITEUR_BROWN= "Pocket";
	private static final String	ISBN_BROWN = "2266144340";
	
	private static final String TITRE_NYLUND = "Halo: La chute de Reach";
	private static final String AUTEUR_NYLUND = "Eric Nylund";
	private static final String EDITEUR_NYLUND = "Milady";
	private static final String ISBN_NYLUND = "978-2-8112-1079-3";
	
	private GestionnaireUseCases gestionnaire;
	private Bundle bundle;
	private Utilisateur userKitsune;
	private Utilisateur user;
	private GestionUsers gestionUser;
	private GestionLivres gestionLivres;
	
	private Livre livreBrown;
	private Livre livreCoben;
	private Livre livreNylund;
	

	@BeforeClass
	//initialisation du bundle, des gestionnaires et des livres
	public void init() {
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.bundle = new Bundle();
		this.user = new Utilisateur();
		this.gestionUser = new GestionUsersImpl();
		this.gestionLivres=new GestionLivresImpl();
		
		this.livreBrown = new Livre();
		this.livreCoben = new Livre();
		this.livreNylund = new Livre();
		
		this.livreBrown.setAuteur(AUTEUR_BROWN);
		this.livreBrown.setEditeur(AUTEUR_BROWN);
		this.livreBrown.setIsbn(ISBN_BROWN);
		this.livreBrown.setLoginUser(USERNAME_KITSUNE);
		this.livreBrown.setTitre(TITRE_BROWN);
		
		this.livreCoben.setAuteur(AUTEUR_COBEN);
		this.livreCoben.setEditeur(EDITEUR_COBEN);
		this.livreCoben.setIsbn(ISBN_COBEN);
		this.livreCoben.setLoginUser(USERNAME_KITSUNE);
		this.livreCoben.setTitre(TITRE_COBEN);
		
		this.livreNylund.setAuteur(AUTEUR_NYLUND);
		this.livreNylund.setEditeur(EDITEUR_NYLUND);
		this.livreNylund.setIsbn(ISBN_NYLUND);
		this.livreNylund.setLoginUser(USERNAME_LALWENDE);
		this.livreNylund.setTitre(TITRE_NYLUND);
		
		this.userKitsune = new Utilisateur(
				USERNAME_KITSUNE, PASSWORD_KITSUNE, LASTNAME_KITSUNE, FIRSTNAME_KITSUNE
		);
	}

	@Test
	//enregistrement d'un utilsateur
	public void enregistrement() {
		this.user.setLogin_user(USERNAME_KITSUNE);
		this.user.setMotDePasse_user(PASSWORD_KITSUNE);
		this.user.setNom_user(LASTNAME_KITSUNE);
		this.user.setPrenom_user(FIRSTNAME_KITSUNE);
		this.bundle.put(Bundle.USER, this.user);
		this.gestionnaire.ajouterUser(this.bundle);
		
		Utilisateur userBundle = (Utilisateur) this.bundle.get(Bundle.USER);
		Assert.assertNotNull(userBundle);
		Assert.assertTrue(userBundle.equals(this.user));
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		
		this.bundle.put(Bundle.USER, this.userKitsune);
		this.gestionnaire.ajouterUser(this.bundle);
		
		
	}

	@Test(dependsOnMethods = {"enregistrement"})
	//connexion d'un utilisateur
	public void connexion() {
		this.bundle.put(Bundle.USER, null);
		this.user = new Utilisateur();
		this.user.setLogin_user(USERNAME_KITSUNE);
		this.user.setMotDePasse_user(PASSWORD_KITSUNE);
		this.user.setNom_user(LASTNAME_KITSUNE);
		this.user.setPrenom_user(FIRSTNAME_KITSUNE);
		
		this.bundle.put(Bundle.USER, this.user);
		this.gestionnaire.connecterUser(this.bundle);
		
		Utilisateur userBundle = (Utilisateur) this.bundle.get(Bundle.USER);
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(userBundle);
		Assert.assertTrue(this.user.equals(userBundle));
	}
	
	@Test(dependsOnMethods = {"connexion"})
	//ajout de livres dans le bundle
	public void ajoutLivres() {

		Livre livreBrownBundle;
		Livre livreCobenBundle;
		Livre livreNylundBundle;
		
		this.bundle.put(Bundle.LIVRE, this.livreBrown);
		this.gestionLivres.ajouterLivre(this.bundle);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		livreBrownBundle = (Livre) this.bundle.get(Bundle.LIVRE);
		Assert.assertNotNull(livreBrownBundle);
		Assert.assertTrue(this.livreBrown.equals(livreBrownBundle));
		
		this.bundle.put(Bundle.LIVRE, this.livreCoben);
		this.gestionLivres.ajouterLivre(this.bundle);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		livreCobenBundle = (Livre) this.bundle.get(Bundle.LIVRE);
		Assert.assertNotNull(livreCobenBundle);
		Assert.assertTrue(this.livreCoben.equals(livreCobenBundle));
		
		this.bundle.put(Bundle.LIVRE,  this.livreNylund);
		this.gestionLivres.ajouterLivre(this.bundle);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		livreNylundBundle = (Livre) this.bundle.get(Bundle.LIVRE);
		Assert.assertNotNull(livreNylundBundle);
		Assert.assertTrue(this.livreNylund.equals(livreNylundBundle));

	}
	
	
	//obtenir les livres d'un utilisateur
	@Test(dependsOnMethods = {"ajoutLivres"})
	public void getLivreByUser() {
		
		this.gestionLivres.livreParUtilisateur(this.bundle);
		
		@SuppressWarnings("unchecked")
		List<Livre> list = (List<Livre>) this.bundle.get(Bundle.LISTE);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE))		;
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 3);
		
	}

	@Test (dependsOnMethods={"ajoutLivres"})
	//ister les livres d'un utilisateur
	public void listerLivres(){
		
		this.gestionLivres.listerLivres(bundle);
		List<Livre> livreObtenu = (List<Livre>) bundle.get(bundle.LISTE);
		Assert.assertTrue((boolean)bundle.get(bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(livreObtenu);
		Assert.assertEquals(livreObtenu.size(), 3);
		
	}
	
	@Test(dependsOnMethods = {"listerLivres"})
	public void ajouterPret() {
		this.livreCoben.setEmprunteur(this.userKitsune.getLogin_user());
		this.bundle.put(Bundle.LIVRE, this.livreCoben);
//		this.bundle.put(Bundle.USER, this.userKitsune);
		this.gestionLivres.memoriserPret(this.bundle);
		Assert.assertTrue((Boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Livre livreDb = (Livre) this.bundle.get(Bundle.LIVRE);
		Assert.assertTrue(livreDb.equals(this.livreCoben));
	}
	
	@Test(dependsOnMethods = {"ajouterPret"})
	public void listerPrets() {
		this.bundle.put(Bundle.USER, this.user);
		this.gestionLivres.listerPrets(this.bundle);
		ArrayList<Livre> listeLivres = (ArrayList<Livre>) this.bundle.get(Bundle.LISTE);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(listeLivres);
		Assert.assertEquals(listeLivres.size(), 1);
		Livre livrePrete = listeLivres.get(0);
		
		Assert.assertTrue(livrePrete.equals(this.livreCoben));
	}
	
	@Test(dependsOnMethods = {"listerPrets"})
	public void listerEmprunts() {
		this.bundle.put(Bundle.USER, this.user);
		this.gestionLivres.listerPrets(this.bundle);
		ArrayList<Livre> listeLivres = (ArrayList<Livre>) this.bundle.get(Bundle.LISTE);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(listeLivres);
		Assert.assertEquals(listeLivres.size(), 1);
		Livre livrePrete = listeLivres.get(0);
		
		Assert.assertTrue(livrePrete.equals(this.livreCoben));
	}
	
	@Test(dependsOnMethods = {"listerPrets"})
	public void supprimerUnPret() {
		this.bundle.put(Bundle.USER, this.userKitsune);
		this.bundle.put(Bundle.LIVRE, this.livreCoben);
		this.gestionLivres.supprimerPret(this.bundle);
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
	}
	
	@Test(dependsOnMethods = {"supprimerUnPret"})
	public void listePretsApresSuppression() {
		this.bundle.put(Bundle.USER, this.user);
		this.gestionLivres.listerPrets(this.bundle);
		ArrayList<Livre> listeLivres = (ArrayList<Livre>) this.bundle.get(Bundle.LISTE);
		
		Assert.assertTrue((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE));
		Assert.assertNotNull(listeLivres);
		Assert.assertEquals(listeLivres.size(), 0);
	}

}



