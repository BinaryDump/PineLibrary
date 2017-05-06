//@author Ignazio NOTO
//modifié par : Bordin Sarah

package be.iesca.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import be.iesca.dao.LivreDao;
import be.iesca.domaine.Emprunt;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class LivreDaoMockImpl implements LivreDao {
	
	private HashMap<String, Livre> mapLivres;
	private TreeMap<String, Emprunt> mapEmprunts;
	
	
	public  LivreDaoMockImpl() {
		Comparator<String> comp = new ComparateurLivres();
		this.mapLivres = new HashMap();
		this.mapEmprunts = new TreeMap<String,Emprunt>(comp);
	}
	

	@Override
	public List<Livre> listerLivres() {
		Collection<Livre> col = Collections.unmodifiableCollection(this.mapLivres.values());
		return new ArrayList<Livre>(col);
	}

	@Override
	public boolean ajouterLivre(Livre livre, Utilisateur utilisateur) {
		//System.out.println(this.mapLivres.size());
		try{
			if(this.mapLivres.containsKey(livre.getIsbn())){
				return false;
			}
			else{
			this.mapLivres.put(livre.getIsbn(), livre);
			
			}
		} catch (Exception ex){
			return false;
		}
		
		return true;
	
		
	}

	
	private class ComparateurLivres implements Comparator<String> {
		@Override
		public int compare(String nom1, String nom2) {
			return nom1.compareTo(nom2);
		}
	}


	@Override
	public List<Livre> getLivre(String isbn) {
		List<Livre> listeLivres = new ArrayList<Livre>();
		try{
			for(Livre l: this.mapLivres.values()) {
				if(l.getIsbn().equals(isbn)) {
					listeLivres.add(l);
				}
			}
			
			return listeLivres;
		
		} catch (Exception ex){
			return null;
		}
	}


	@Override
	public boolean supprimerLivre(String titre, Utilisateur utilisateur) {
		try {
			if (this.mapLivres.remove(titre) == null)
				return false;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}


	@Override
	public boolean modifierLivre(Livre livre, Utilisateur utilisateur) {
		try {
			if(this.mapLivres.containsKey(livre.getIsbn())) {
				this.mapLivres.put(livre.getIsbn(), livre);
				return true;
			}
			else {
				return false;
			}
//			if (this.mapLivres.remove(livre.getTitre()) == null)
//				return false;
//			this.mapLivres.put(livre.getTitre(), livre);
//			return true;
		} catch (Exception ex) {
			return false;
		}
	}


	@Override
	public List<Livre> getLivreByUser(String login_user) {
		Collection<Livre> col = Collections.unmodifiableCollection(this.mapLivres.values());
		List<Livre> listeLivres = new ArrayList<Livre>();
		try{
			for(Livre l: col) {
				listeLivres.add(l);
			}
			return listeLivres;
		
		} catch (Exception ex){
			return null;
		}
	}

	@Override
	public boolean supprimerPret(Livre livre, String utilisateur) {
		try {
			if(this.mapEmprunts.containsKey(livre.getIsbn())){
				this.mapEmprunts.remove(livre.getIsbn());
				return true;
			}
			else {
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	public boolean memoriserPret(Livre livre, String emprunteur, String date) {
		try {
			Emprunt emprunt = new Emprunt(emprunteur, 
					livre.getLoginUser(),
					livre.getIsbn(),
					date);

			this.mapEmprunts.put(livre.getIsbn(), emprunt);
			return true;
		} catch(Exception e) {
			return false;
		}
	}


	@Override
	public ArrayList<Livre> listerPrets(Utilisateur utilisateur) {
		Collection<Emprunt> col = Collections.unmodifiableCollection(this.mapEmprunts.values());
		ArrayList<Livre> livres = new ArrayList<>();
		
		for(Emprunt e: col) {
			List<Livre> livres_ = this.getLivre(e.getIsbn_livreEmprunte());
			if(livres_.get(0).getLoginUser().equals(utilisateur.getLogin_user())) {
				livres.add(livres_.get(0));
			}
		}
		return livres;

	}


	@Override
	public ArrayList<Livre> listerEmprunts(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return null;
	}


}
