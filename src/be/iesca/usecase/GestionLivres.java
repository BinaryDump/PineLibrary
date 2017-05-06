/*Author Bordin Sarah Version 1*/
//modifié Ignazio Noto

package be.iesca.usecase;

import be.iesca.domaine.Bundle;

public interface GestionLivres {
	void ajouterLivre(Bundle bundle);
	void livreParUtilisateur(Bundle bundle);
	void listerLivres(Bundle bundle);
	void memoriserPret(Bundle bundle);
	void supprimerPret(Bundle bundle);
	void listerPrets(Bundle bundle);
	void listerEmprunts(Bundle bundle);
	
}
