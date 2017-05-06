/*
 * Author Bordin Sarah Version 1
 * Modifi� par Quentin (31 mars)
 * */


package be.iesca.usecaseimpl;

import java.util.List;

import be.iesca.dao.LivreDao;
import be.iesca.dao.UtilisateurDao;
import be.iesca.daoimpl.DaoFactory;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;
import be.iesca.usecase.GestionUsers;

public class GestionUsersImpl implements GestionUsers {
	private UtilisateurDao userDao;
	private LivreDao livreDao;

	public GestionUsersImpl() {
		this.userDao = (UtilisateurDao) DaoFactory.getInstance().getDaoImpl(UtilisateurDao.class);
	}


	//cle bundle contient un user à identifier (à connecter)
	@Override
	public void connecterUser(Bundle bundle) {
		Utilisateur userDb = null;
		boolean operationReussie = false;
		String message = "";
		Utilisateur user = (Utilisateur) bundle.get(Bundle.USER);
		
		if(user == null) {
			message = "Pas d'utilisateur";
		}
		else {
			String username = user.getLogin_user();
			String password = user.getMotDePasse_user();
			userDb = this.userDao.getUtilisateur(username, password);
			
			if(userDb == null) {
				message = "Echec lors de l'identification";
			}
			else {
				message = "Vous �tes connect�.";
				operationReussie = true;
				bundle.put(Bundle.USER, userDb);
			}
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
	}


	// Auteur: Quentin
	// Cette méthode permet l'ajout d'utilisateur dans la base de donnée.
	@Override
	public void ajouterUser(Bundle bundle) {
		boolean registration = false;
		String message = "";
		Utilisateur user = (Utilisateur) bundle.get(Bundle.USER);
		
		if(user != null) {
			this.userDao.ajouterUser(user);
			message = "Utilisateur inscrit correctement";
			bundle.put(Bundle.USER, user);
			registration = true;
		}
		else {
			message = "Une erreur s'est produite lors de l'inscription";
		}
		bundle.put(Bundle.OPERATION_REUSSIE, registration);
		bundle.put(Bundle.MESSAGE, message);
		
	}

	// Auteur: Quentin
	// Cette méthode permet la déconnexion d'un utilisateur connecté
	// Pré-condition: être connecté
	@Override
	public void deconnecterUser(Bundle bundle) {
		String message;
		boolean operationReussie = false;
		
		if(bundle.get(Bundle.USER) != null) {
			bundle.put(Bundle.USER, null);
			operationReussie = true;
			message = "Utilisateur d�connect�";
		}
		else {
			message = "Aucun utilisateur connect�";
		}
		
		bundle.put(Bundle.OPERATION_REUSSIE, operationReussie);
		bundle.put(Bundle.MESSAGE, message);
	}

	/*@author : Bordin Sarah
	 * @version: 1
	 */	
	
	@Override
	public void listerUser(Bundle bundle) {
		boolean liste = true;
		String message="";
		List<Utilisateur>listeUser = null;
		listeUser = this.userDao.listerUsers();
		if(listeUser==null){
			liste=false;
		}
		else if (listeUser.isEmpty()){
			message = "la liste ne contient aucun utilisateur";
		}else if(listeUser.size()==1){
			message = "vous �tes le seul utilisateur pr�sent sur le syst�me";
		}else
			message = "il y a "+listeUser.size()+"utilisateurs";
		bundle.put(bundle.OPERATION_REUSSIE,liste);
		bundle.put(bundle.MESSAGE,message);
		bundle.put(bundle.LISTE,listeUser);
	}
	
	
	
		
}


