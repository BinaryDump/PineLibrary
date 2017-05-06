/*Author Bordin Sarah Version 1*/
package be.iesca.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import be.iesca.dao.UtilisateurDao;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;

public class UserDaoMockImpl implements UtilisateurDao {
	private Map<String, Utilisateur> mapUsers;
	private Bundle bundle=new Bundle();

	public UserDaoMockImpl() {
		Comparator<String> comp = new ComparateurUser();
		this.mapUsers = new TreeMap<String, Utilisateur>(comp);
	}

	
	public Utilisateur getUtilisateur(String login_user, String motDePasseUser) {
		try {
			Utilisateur user = this.mapUsers.get(login_user);
			if (user==null) 
				return null;
			if(user.getMotDePasse_user()==null)
				return null;
			return new Utilisateur(user);
		} catch (Exception ex) {
			return null;
		}
		
	}


	private class ComparateurUser implements Comparator<String> {
		@Override
		public int compare(String nom1, String nom2) {
			return nom1.compareTo(nom2);
		}
	}

	@Override
	public boolean ajouterUser(Utilisateur user) {
		
		try {
			if (this.mapUsers.containsKey(user.getLogin_user()))
				return false;
			this.mapUsers.put(user.getLogin_user(), user);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}


	@Override
	public List<Utilisateur> listerUsers() {
		
		List<Utilisateur> utilisateurs = new ArrayList<>();
		for (Utilisateur u: this.mapUsers.values()) {
			utilisateurs.add(u);
		}
		
		return utilisateurs;
	}

}
