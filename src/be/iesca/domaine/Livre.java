/*@author: Bordin Sarah*/
package be.iesca.domaine;

public class Livre {
	private String titre;
	private String auteur;
	private String editeur;
	private String isbn;
	private String loginUser;
	private String date_empreint;
	private String emprunteur;

	private boolean available;
	
	
	public Livre(String titre,String auteur,String editeur,String isbn, String loginUser){	
		super();
		this.titre=titre;
		this.auteur=auteur;
		this.editeur=editeur;
		this.isbn=isbn;
		this.loginUser=loginUser;
		this.available=true;
		this.date_empreint = null;
	}
	
	public Livre() {
		super();
		this.titre = "";
		this.auteur = "";
		this.editeur = "";
		this.isbn = "";
		this.loginUser = "";
		
	}
	
	public String getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(String emprunteur) {
		this.emprunteur = emprunteur;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public Livre(Livre livre) {
		super();
		this.titre = livre.titre;
		this.auteur = livre.auteur;
		this.editeur = livre.editeur;
		this.isbn = livre.isbn;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getEditeur() {
		return editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getDate_empreint() {
		return date_empreint;
	}

	public void setDate_empreint(String date_empreint) {
		this.date_empreint = date_empreint;
	}

	@Override
	public String toString() {
		return titre+ " | "+auteur;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livre other = (Livre) obj;
		if (auteur == null) {
			if (other.auteur != null)
				return false;
		} else if (!auteur.equals(other.auteur))
			return false;
		if (editeur == null) {
			if (other.editeur != null)
				return false;
		} else if (!editeur.equals(other.editeur))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre)){
			return false;		
		}
		return true;
	}
}
