//modifiÃ© par Ignazio NOTO

package be.iesca.usecaseimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.iesca.dao.LivreDao;
import be.iesca.dao.UtilisateurDao;
import be.iesca.daoimpl.DaoFactory;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Emprunt;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;
import be.iesca.usecase.GestionLivres;

public class GestionLivresImpl implements GestionLivres{
	
	private Livre livre;
	private LivreDao livreDao;

	public GestionLivresImpl() {
		this.livreDao = (LivreDao) DaoFactory.getInstance().getDaoImpl(LivreDao.class);
	}


	@Override
	public void ajouterLivre(Bundle bundle) {
		boolean operationReussie = false;
		String message = "";
		Livre livre = (Livre) bundle.get(Bundle.LIVRE);
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		if(utilisateur != null) {
			if(livre != null) {
				this.livreDao.ajouterLivre(livre, utilisateur);
				operationReussie = true;
				message = "Livre ajouté";
			}
			else {
				message = "Vous n'avez ajouter aucun livre.";
			}
		}
		else {
			message = "Vous devez être connecté pour ajouter un livre.";
		}
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
	}

	

	@Override
	public void livreParUtilisateur(Bundle bundle) {
		boolean operationReussie = false;
		String message = "";
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		//System.out.println(bundle.get(Bundle.USER));
		if(utilisateur != null) {
			List<Livre> listes = this.livreDao.getLivreByUser(utilisateur.getLogin_user());
			operationReussie = true;
			message = "Livre par utilisateur trouvé.";
			bundle.put(Bundle.LISTE, listes);
		}
		else {
			message = "Vous devez être connecté pour accéder a cette fonction.";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		
		
	}


	@Override
	public void memoriserPret(Bundle bundle) {

		boolean operationReussie = false;
		String message = "";
		Livre livre = (Livre) bundle.get(Bundle.LIVRE);
		String loginEmprunteur = livre.getEmprunteur();
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		if(utilisateur != null) {
			if(livre != null) {
				SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM yyyy");
			    Date now = new Date();
			    String strDate = sdfDate.format(now);
			    
				this.livreDao.memoriserPret(livre, loginEmprunteur, strDate);
				operationReussie = true;
				message = "Prêt mémorisé.";
				//bundle.put(Bundle.LISTE, listes);
			}
			else {
				message = "Vous devez sélectionner un livre à  prêter.";
			}
		}
		else {
			message = "Vous devez être connecté pour accéder a cette fonction.";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		
	}


	@Override
	public void supprimerPret(Bundle bundle) {

		boolean operationReussie = false;
		String message = "";
		Livre livre = (Livre) bundle.get(Bundle.LIVRE);
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		if(utilisateur != null) {
			if(this.livreDao.supprimerPret(livre, utilisateur.getLogin_user())) {
				operationReussie = true;
				message = "Prêt supprimé.";
			}
			else {
				message = "Impossible de supprimer ce prêt.";
			}

		}
		else {
			message = "Vous devez être connecté pour accéder a cette fonction.";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		
	}


	@Override
	public void listerPrets(Bundle bundle) {

		boolean operationReussie = false;
		String message = "";
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		List<Livre> listPrets = null;
		if(utilisateur != null) {
			listPrets = this.livreDao.listerPrets(utilisateur);
			operationReussie = true;
			message = "Liste de prÃªts disponible.";
		}
		else {
			message = "Vous devez être connecté pour accéder a cette fonction.";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LISTE, listPrets);

		
	}


	@Override
	public void listerEmprunts(Bundle bundle) {

		boolean operationReussie = false;
		String message = "";
		Utilisateur utilisateur = (Utilisateur) bundle.get(Bundle.USER);
		List<Livre> listEmprunts = null;
		if(utilisateur != null) {
			listEmprunts = this.livreDao.listerEmprunts(utilisateur);
			operationReussie = true;
			message = "Liste de prêts disponible.";
		}
		else {
			message = "Vous devez être connecté pour accéder a cette fonction.";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LISTE, listEmprunts);
		
	}


	@Override
	public void listerLivres(Bundle bundle) {

		boolean operationReussie = true;
		String message = "";
		List<Livre> listeLivres = null;
		listeLivres = this.livreDao.listerLivres();
		if (listeLivres==null) {
			operationReussie = false;
		} else if (listeLivres.isEmpty())
			message = "Liste vide";
		else if (listeLivres.size() == 1)
			message = "Il y a 1 livre";
		else
			message = "Il y a " + listeLivres.size() + " livres";
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LISTE, listeLivres);
		
	}

}