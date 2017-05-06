/*Author Bordin Sarah Version 1*/

package be.iesca.dao;

import java.util.List;

import be.iesca.domaine.Utilisateur;

public interface UtilisateurDao extends Dao {
	Utilisateur getUtilisateur(String login_user, String motDePasseUser);
	boolean ajouterUser(Utilisateur user);
	List<Utilisateur> listerUsers();

}
