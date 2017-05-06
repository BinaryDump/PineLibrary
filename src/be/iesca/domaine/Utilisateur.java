package be.iesca.domaine;

public class Utilisateur {
	
	private String login_user;
	private String motDePasse_user;
	private String nom_user;
	private String prenom_user;
	

	
	public Utilisateur() {
		this.login_user = "";
		this.motDePasse_user = "";
	} 

	public Utilisateur(String login_user, String motDePasse_user, String nom_user, String prenom_user) {
		this.login_user = login_user;
		this.motDePasse_user = motDePasse_user;
		this.nom_user = nom_user;
		this.prenom_user = prenom_user;
	}
	
	public Utilisateur(Utilisateur utilisateur) {
		this.login_user = utilisateur.getLogin_user();
		this.motDePasse_user = utilisateur.getMotDePasse_user();
		this.nom_user = utilisateur.getNom_user();
		this.prenom_user = utilisateur.getPrenom_user();
	}


	@Override
	public String toString() {
		return nom_user+" - "+prenom_user;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public String getMotDePasse_user() {
		return motDePasse_user;
	}

	public void setMotDePasse_user(String motDePasse_user) {
		this.motDePasse_user = motDePasse_user;
	}

	public String getNom_user() {
		return nom_user;
	}

	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}

	public String getPrenom_user() {
		return prenom_user;
	}

	public void setPrenom_user(String prenom_user) {
		this.prenom_user = prenom_user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login_user == null) ? 0 : login_user.hashCode());
		result = prime * result + ((nom_user == null) ? 0 : nom_user.hashCode());
		result = prime * result + ((prenom_user == null) ? 0 : prenom_user.hashCode());
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
		Utilisateur other = (Utilisateur) obj;
		if (login_user == null) {
			if (other.login_user != null)
				return false;
		} else if (!login_user.equals(other.login_user))
			return false;
		if (nom_user == null) {
			if (other.nom_user != null)
				return false;
		} else if (!nom_user.equals(other.nom_user))
			return false;
		if (prenom_user == null) {
			if (other.prenom_user != null)
				return false;
		} else if (!prenom_user.equals(other.prenom_user))
			return false;
		return true;
	}



	
	
}

