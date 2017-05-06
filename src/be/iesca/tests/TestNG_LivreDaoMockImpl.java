/*@author : Bordin Sarah
 * modifié par Ignazio
 * Modifié pas Quentin
 * @version : 1
 */

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
import be.iesca.daoimpl.LivreDaoMockImpl;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class TestNG_LivreDaoMockImpl {
	
	private static final String TITRE_COBEN = "Ne le dis à personne ";
	private static final String AUTEUR_COBEN = "Harlan Coben ";
	private static final String EDITEUR_COBEN= "Pocket";
	private static final String	ISBN_COBEN= "226612515";
	private static final String LOGIN_USER="kitsune";
	
	private static final String TITRE_BROWN = "Da vinci code ";
	private static final String AUTEUR_BROWN = "Dan Brown ";
	private static final String EDITEUR_BROWN= "Pocket";
	private static final String	ISBN_BROWN = "2266144340";
	
	private static final String TITRE_BROWN_MOD = "Da vinci code modifié";
	private static final String AUTEUR_BROWN_MOD = "Dan Brown modifié";
	private static final String EDITEUR_BROWN_MOD = "Pocket modifié";
	
	
	private List<Livre>livres;
	private LivreDao livreDao;
	
	private Livre livreCoben;
	private Livre livreBrown;
	
	private Utilisateur utilisateur;
	
	@BeforeClass
	public void initialiserListeLivres(){
		
		livreDao=new LivreDaoMockImpl();	//a decommenter qd classe implementee
		
		livres=new ArrayList<Livre>(2);
		livres.add(new Livre(TITRE_BROWN,AUTEUR_BROWN,EDITEUR_BROWN,ISBN_BROWN,LOGIN_USER));		
		livres.add(new Livre(TITRE_COBEN,AUTEUR_COBEN,EDITEUR_COBEN,ISBN_COBEN,LOGIN_USER));
		
		this.utilisateur = new Utilisateur("Kitsune", "kitsune", "kitsune", "kitsune");
		this.livreBrown = new Livre(TITRE_BROWN,AUTEUR_BROWN,EDITEUR_BROWN,ISBN_BROWN,LOGIN_USER);
		this.livreCoben = new Livre(TITRE_COBEN,AUTEUR_COBEN,EDITEUR_COBEN,ISBN_COBEN,LOGIN_USER);
	}
	
	@Test
	public void testAjouter(){
		for(Livre l :livres){
			Assert.assertTrue(livreDao.ajouterLivre(l, this.utilisateur));
		}		
	}
	@Test(dependsOnMethods = { "testAjouter" })
	public void testLister() {
		List<Livre> livresObtenus = livreDao.listerLivres();
		Assert.assertEquals(livresObtenus.size(), 2);
		Assert.assertTrue(this.livreCoben.equals(livresObtenus.get(0)));
	}
	
//	@Test
//	public void testLivresTries() {
//		Livre livreZ = new Livre(TITRE_COBEN, AUTEUR_COBEN, EDITEUR_COBEN, ISBN_COBEN,LOGIN_USER);
//		Livre livreA = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN,LOGIN_USER);
//		
//		livreDao.ajouterLivre(livreA, utilisateur);
//		livreDao.ajouterLivre(livreZ, utilisateur);
//		
//		List<Livre> livresObtenus = livreDao.listerLivres();
//
//		assertEquals( livresObtenus.get(0), livreZ);
//		assertEquals( livresObtenus.get(1), livreA);
//	}	
//	
	@Test(dependsOnMethods={ "testAjouter"})
	private void testGetLivre(){
		List<Livre> livres = livreDao.getLivre(this.livreBrown.getIsbn());
		Assert.assertTrue(livres.get(0).equals(this.livreBrown));
	}	
	
	@Test(dependsOnMethods = {"testAjouter"})
	private void testModifierLivre(){
		
		Livre livreMod = new Livre(TITRE_BROWN_MOD, AUTEUR_BROWN_MOD, EDITEUR_BROWN_MOD, ISBN_BROWN, this.utilisateur.getLogin_user());
		Assert.assertTrue(livreDao.modifierLivre(livreMod, this.utilisateur));
	}
	
	@Test(dependsOnMethods = {"testAjouter"})
	private void testMemoriserPret(){
		Livre livre = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN, LOGIN_USER);
		SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM yyyy");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
		Assert.assertTrue(livreDao.memoriserPret(livre, this.utilisateur.getLogin_user(), strDate));			
	}
	
	@Test(dependsOnMethods = {"testMemoriserPret"})
	private void testListerPrets(){
		
		Livre livreZ = new Livre(TITRE_COBEN, AUTEUR_COBEN, EDITEUR_COBEN, ISBN_COBEN,LOGIN_USER);
		Livre livreA = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN,LOGIN_USER);
		//List<Emprunt> listPrets = this.livreDao.getPrets();
		//Assert.assertEquals(TITRE_BROWN, listPrets.get(0).getTitre());
		//Assert.assertEquals(TITRE_COBEN, listPrets.get(1).getTitre());
		
		//à décommenter quand Dao finis
		
		
	}
	
	@Test(dependsOnMethods = {"testMemoriserPret"})
	private void testListerEmprunts(){
		Livre livreZ = new Livre(TITRE_COBEN, AUTEUR_COBEN, EDITEUR_COBEN, ISBN_COBEN,LOGIN_USER);
		Livre livreA = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN,LOGIN_USER);
		//List<Emprunt> listEmprunts = this.livreDao.getEmprunts();
		//Assert.assertEquals(TITRE_BROWN, listEmprunts.get(0).getTitre());
		//Assert.assertEquals(TITRE_COBEN, listEmprunts.get(1).getTitre());
		
		//à décommenter quand Dao finis
		
	}

	@Test(dependsOnMethods = {"testListerPrets"})
	private void testSupprimerPret(){
		Livre livre = new Livre(TITRE_BROWN, AUTEUR_BROWN, EDITEUR_BROWN, ISBN_BROWN, LOGIN_USER);
		Assert.assertTrue(livreDao.supprimerPret(livre, ""));			
	}

}
