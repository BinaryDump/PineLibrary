//@author Sarah Bordin
//modifié : Ignazio NOTO

package be.iesca.tests;

import static org.testng.AssertJUnit.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import be.iesca.dao.LivreDao;
import be.iesca.daoimpl.LivreDaoImpl;
import be.iesca.domaine.Emprunt;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class TestNG_LivreDaoImpl{
	private static final String TITRE_COBEN = "Ne le dis à personne";
	private static final String AUTEUR_COBEN = "Harlan Coben";
	private static final String EDITEUR_COBEN= "Pocket";
	private static final String	ISBN_COBEN= "226612515";
	private static final String	LOGIN_USER= "Kitsune";
	
	private static final String TITRE_BROWN = "Da vinci code";
	private static final String AUTEUR_BROWN = "Dan Brown";
	private static final String EDITEUR_BROWN= "Pocket";
	private static final String	ISBN_BROWN = "2266144340";
	
	private static final String TITRE_BROWN_MOD = "Da vinci code modifié";
	private static final String AUTEUR_BROWN_MOD = "Dan Brown modifié";
	private static final String EDITEUR_BROWN_MOD = "Pocket modifié";

	private List<Livre>livres;
	private LivreDao livreDao;	
	private Utilisateur utilisateur;
	
	@BeforeClass
	//initialisation des livres, du dao et de l'utilisateur
	public void initialiserListeLivres(){
		
		livreDao=new LivreDaoImpl();	
		livres=new ArrayList<Livre>(2);
		livres.add(new Livre(TITRE_BROWN,AUTEUR_BROWN,EDITEUR_BROWN,ISBN_BROWN,LOGIN_USER));
		livres.add(new Livre(TITRE_COBEN,AUTEUR_COBEN,EDITEUR_COBEN,ISBN_COBEN,LOGIN_USER));
		this.utilisateur = new Utilisateur("lalwende", "sarah", "Sarah", "Bordin");
	}

	@Test
	//ajout d'un livre 
	public void testAjouter(){
		for(Livre l :livres){
			Assert.assertTrue(livreDao.ajouterLivre(l, this.utilisateur));
		}		
	}
	
	@Test(dependsOnMethods = { "testAjouter" })
	//lister les livres 
	public void testLister() {
		List<Livre> livresObtenus = livreDao.listerLivres();
		for (int i = 0; i < livres.size(); i++) {
			assertEquals(livresObtenus.get(i), livres.get(i));
		}
	}
	
	@Test
	//lister les livres triés
	private void testLivresTries() {
		Livre livreZ = new Livre(TITRE_COBEN, AUTEUR_COBEN, EDITEUR_COBEN, ISBN_COBEN,LOGIN_USER);
		Livre livreA = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN,LOGIN_USER);
		//livreDao.ajouterLivre(livreA,LOGIN_USER);
		//livreDao.ajouterLivre(livreZ,LOGIN_USER);
		List<Livre> livresObtenus = livreDao.listerLivres();
		assertEquals( livresObtenus.get(0), livreA);
		assertEquals( livresObtenus.get(1), livreZ);
	}
	
	
	@Test(dependsOnMethods={ "testAjouter"})
	//obtenir un livre
	private void testgetLivre(){
		List<Livre> livres = livreDao.getLivre(TITRE_BROWN);
		for(Livre l : livres) {
			assertEquals(l.getTitre(),TITRE_BROWN);
		}
	}
	
	@Test(dependsOnMethods = {"testgetLivre"})
	//lister les livres par utilisateur
	private void livreParUtilisateur() {
		List<Livre> livres = this.livreDao.getLivreByUser("lalwende");
		Assert.assertNotNull(livres);
		Assert.assertEquals(TITRE_BROWN, livres.get(0).getTitre());
		Assert.assertEquals(TITRE_COBEN, livres.get(1).getTitre());
	}
	

	@Test(dependsOnMethods = {"testMemoriserPret"})
	//lister les prets de l'utilisateur
	private void testListerPrets(){
		
		Livre livre = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN, LOGIN_USER);
		
		ArrayList<Livre> listPrets = this.livreDao.listerPrets(this.utilisateur);
		Assert.assertEquals(listPrets.size(), 1);
		Assert.assertEquals(livre, listPrets.get(0));

	}

	@Test(dependsOnMethods = {"testListerPrets"})
	//mofifier un livre
	private void testModifierLivre(){
		
		Livre livreMod = new Livre(TITRE_BROWN_MOD, AUTEUR_BROWN_MOD, EDITEUR_BROWN_MOD, ISBN_BROWN, this.utilisateur.getLogin_user());
		Assert.assertTrue(livreDao.modifierLivre(livreMod, this.utilisateur));
	}
	
	@Test(dependsOnMethods = {"testAjouter"})
	//memoriser un prêt
	private void testMemoriserPret(){
		Livre livre = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN, LOGIN_USER);
		SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM yyyy");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    
		Assert.assertTrue(livreDao.memoriserPret(livre, this.utilisateur.getLogin_user(), strDate));	
		
	}
	
	
	@Test(dependsOnMethods = {"testListerPrets"})
	//supprimer un prêt quand le livre est rendu
	private void testSupprimerPret(){
		Livre livre = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN, LOGIN_USER);
		Assert.assertTrue(livreDao.supprimerPret(livre, "Kitsune"));
	}
	
	@Test(dependsOnMethods = {"testSupprimerPret"})
	//verifier la suppression
	private void testListerPretsApresSuppression(){

		ArrayList<Livre> listPrets = this.livreDao.listerPrets(this.utilisateur);
		Assert.assertEquals(listPrets.size(), 0);

	}
	@Test(dependsOnMethods = {"testMemoriserPret"})
	//liter les emprunts
	private void testListerEmprunts(){
		Livre livreZ = new Livre(TITRE_COBEN, AUTEUR_COBEN, EDITEUR_COBEN, ISBN_COBEN,LOGIN_USER);
		Livre livreA = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN,LOGIN_USER);
		
//		ArrayList<Livre> listEmprunts = this.livreDao.listerEmprunts(this.utilisateur);
//		System.out.println(listEmprunts.size());
//		Livre livreBd = listEmprunts.get(0);
//		Assert.assertEquals(livreA, livreBd);
		
		//à décommenter quand Dao finis
		
	}
	
	
	
	
}
