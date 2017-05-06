
/* 
 * Auteur: Quentin
 */

package be.iesca.controleur;

import java.util.List;

import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;
import be.iesca.usecase.GestionLivres;
import be.iesca.usecase.GestionUsers;
import be.iesca.usecaseimpl.GestionLivresImpl;
import be.iesca.usecaseimpl.GestionUsersImpl;

public class GestionnaireUseCases implements GestionUsers , GestionLivres{

	private static final GestionnaireUseCases INSTANCE = new GestionnaireUseCases(); 
	
	private Utilisateur utilisateur;
	private GestionUsers gestionUsers;
	private GestionLivres gestionLivres;
	
	public static GestionnaireUseCases getInstance() {
		return INSTANCE;
	}
	
	private GestionnaireUseCases() {
		this.gestionUsers = new GestionUsersImpl();
		this.gestionLivres= new GestionLivresImpl();
		this.utilisateur = null;
	}
	
	@Override
	public void connecterUser(Bundle bundle) {
		if (bundle.get(Bundle.USER) != null) {
			this.gestionUsers.connecterUser(bundle);
			if((boolean) bundle.get(Bundle.OPERATION_REUSSIE)) {
				this.utilisateur = (Utilisateur) bundle.get(Bundle.USER);
			}
		}
		else {
			bundle.put(Bundle.MESSAGE, "Op�ration impossible: aucun utilisateur demand�.");
			bundle.put(Bundle.OPERATION_REUSSIE, false);
		}
	}

	@Override
	public void ajouterUser(Bundle bundle) {

		if(bundle.get(Bundle.USER) == null) {
			bundle.put(Bundle.MESSAGE, "Op�ration impossible: un utilisateur connect� ne peut s'enregistrer");
			bundle.put(Bundle.OPERATION_REUSSIE, false);
		}
		else {
			this.gestionUsers.ajouterUser(bundle);
		}
		
	}

	@Override
	public void deconnecterUser(Bundle bundle) {
		if(this.utilisateur == null) {
			bundle.put(Bundle.MESSAGE, "Op�ration impossible: pas d'utilisateur connect�");
			bundle.put(Bundle.OPERATION_REUSSIE, false);
		}
		else {
			this.gestionUsers.deconnecterUser(bundle);
		}
	}
	
	/*@author:Bordin Sarah
	 *@version : 1
	 */

	@Override
	public void listerUser(Bundle bundle) {
		
		if (bundle.get(Bundle.USER) == null) { // pas de user 
			bundle.put(Bundle.MESSAGE,
					"Op�ration impossible. Pas d'utilisateur connect�.");
			bundle.put(Bundle.OPERATION_REUSSIE, false);
		} else {
			this.gestionUsers.listerUser(bundle);
		}
	}
	/*@author : Bordin Sarah
	 * @version : 1
	 */
	@Override
	public void ajouterLivre(Bundle bundle) {
		
		if(bundle.get(Bundle.USER)==null){
			bundle.put(Bundle.MESSAGE,"operation impossible, pas d'utlisateurs connect�");
			bundle.put(Bundle.OPERATION_REUSSIE,false);	
		}else if(bundle.get(Bundle.LIVRE)==null){
			bundle.put(Bundle.MESSAGE,"Op�ration impossible , pas de livres dans la liste");
			bundle.put(Bundle.OPERATION_REUSSIE,false);			
		}else
		{
			this.gestionLivres.ajouterLivre(bundle);
			bundle.put(Bundle.MESSAGE,"Livre ajout� avec succ�s");
			bundle.put(Bundle.OPERATION_REUSSIE,true);
		}		
	}

	
	/*@author : Bordin Sarah
	 * @version : 1
	 */
	@Override
	public void listerLivres(Bundle bundle) {
		if(bundle.get(Bundle.USER)==null){
			bundle.put(Bundle.MESSAGE,"operation impossible, pas d'utlisateurs connect�");
			bundle.put(Bundle.OPERATION_REUSSIE,false);	
		}
		else
		{
			this.gestionLivres.listerLivres(bundle);
		}
		
	}

	@Override
	public void livreParUtilisateur(Bundle bundle) {
		//System.out.println(bundle.get(Bundle.USER));
		if(bundle.get(Bundle.USER) != null) {
			this.gestionLivres.livreParUtilisateur(bundle);
		}
		else {
			bundle.put(Bundle.MESSAGE, "Vous devez �tre connect� pour acc�der a cette fonctionnalit�.");
		}
	}

	@Override
	public void memoriserPret(Bundle bundle) {
		if(bundle.get(Bundle.USER) != null) {
			if(bundle.get(Bundle.LIVRE) != null) {
				this.gestionLivres.memoriserPret(bundle);
			}
			else {
				bundle.put(Bundle.MESSAGE, "Vous devez s�lectionner un livre � m�moriser.");
			}
		}
		else {
			bundle.put(Bundle.MESSAGE, "Vous devez �tre connect� pour acc�der a cette fonctionnalit�.");
		}
		
	}

	@Override
	public void supprimerPret(Bundle bundle) {
		if(bundle.get(Bundle.USER) != null) {
			if(bundle.get(Bundle.LIVRE) != null) {
				this.gestionLivres.supprimerPret(bundle);
			}
			else {
				bundle.put(Bundle.MESSAGE, "Vous devez s�lectionner un livre � supprimer.");
			}
		}
		else {
			bundle.put(Bundle.MESSAGE, "Vous devez �tre connect� pour acc�der a cette fonctionnalit�.");
		}		
	}

	@Override
	public void listerPrets(Bundle bundle) {
		if(bundle.get(Bundle.USER) != null) {
			this.gestionLivres.listerPrets(bundle);
		}
		else {
			bundle.put(Bundle.MESSAGE, "Vous devez �tre connect� pour acc�der a cette fonctionnalit�.");
		}
		
		
	}

	@Override
	public void listerEmprunts(Bundle bundle) {
		if(bundle.get(Bundle.USER) != null) {
			this.gestionLivres.listerEmprunts(bundle);
		}
		else {
			bundle.put(Bundle.MESSAGE, "Vous devez �tre connect� pour acc�der a cette fonctionnalit�.");
		}
	}
	
	

	
	
}
