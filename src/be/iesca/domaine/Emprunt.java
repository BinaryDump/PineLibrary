/*@author : Bordin Sarah
 * Version :1
 */

package be.iesca.domaine;

public class Emprunt {
	
	private String login_emprunteur; 
	private String login_preteur;
	private String isbn_livreEmprunte;
	private String date_emprunt;
	
	
	public Emprunt(String login_emprunteur,String login_preteur,String isbn_livreEmprunte,String date_emprunt){
		
		this.login_emprunteur=login_emprunteur;
		this.login_preteur=login_preteur;
		this.isbn_livreEmprunte=isbn_livreEmprunte;
		this.date_emprunt=date_emprunt;
	}


	public String getLogin_emprunteur() {
		return login_emprunteur;
	}


	public void setLogin_emprunteur(String login_emprunteur) {
		this.login_emprunteur = login_emprunteur;
	}


	public String getLogin_preteur() {
		return login_preteur;
	}


	public void setLogin_preteur(String login_preteur) {
		this.login_preteur = login_preteur;
	}


	public String getIsbn_livreEmprunte() {
		return isbn_livreEmprunte;
	}


	public void setIsbn_livreEmprunte(String isbn_livreEmprunte) {
		this.isbn_livreEmprunte = isbn_livreEmprunte;
	}


	public String getDate_emprunt() {
		return date_emprunt;
	}


	public void setDate_emprunt(String date_emprunt) {
		this.date_emprunt = date_emprunt;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn_livreEmprunte == null) ? 0 : isbn_livreEmprunte.hashCode());
		result = prime * result + ((login_emprunteur == null) ? 0 : login_emprunteur.hashCode());
		result = prime * result + ((login_preteur == null) ? 0 : login_preteur.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emprunt other = (Emprunt) obj;
		if (isbn_livreEmprunte == null) {
			if (other.isbn_livreEmprunte != null)
				return false;
		} else if (!isbn_livreEmprunte.equals(other.isbn_livreEmprunte))
			return false;
		if (login_emprunteur == null) {
			if (other.login_emprunteur != null)
				return false;
		} else if (!login_emprunteur.equals(other.login_emprunteur))
			return false;
		if (login_preteur == null) {
			if (other.login_preteur != null)
				return false;
		} else if (!login_preteur.equals(other.login_preteur))
			return false;
		return true;
	}
	
	

}
