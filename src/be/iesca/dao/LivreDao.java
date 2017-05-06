/*Author Bordin Sarah Version 1*/

package be.iesca.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.iesca.domaine.Bundle;
import be.iesca.domaine.Emprunt;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public interface LivreDao extends Dao {

	List<Livre>listerLivres();
	boolean ajouterLivre(Livre livre, Utilisateur utilisateur);
	List<Livre> getLivre(String titre);
	List<Livre> getLivreByUser(String login_user);
	boolean supprimerLivre(String titre, Utilisateur utilisateur);
	boolean modifierLivre(Livre livre, Utilisateur utilisateur);
	boolean memoriserPret(Livre livre, String emprunteur, String date);
	boolean supprimerPret(Livre livre, String utilisateur);
	ArrayList<Livre> listerPrets(Utilisateur utilisateur);
	ArrayList<Livre> listerEmprunts(Utilisateur utilisateur);
	
}
