//@author : Ignazio NOTO
// Modifi� par Quentin
package be.iesca.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.iesca.dao.LivreDao;
import be.iesca.domaine.Emprunt;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class LivreDaoImpl implements LivreDao {
	
	private static final String GET = "SELECT * FROM Livres l WHERE l.titre = ? ORDER BY auteur, titre ASC";
	private static final String AJOUT = "insert into livres(titre, auteur, editeur, isbn, login_user) SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS ( SELECT * FROM livres WHERE isbn=? AND login_user=?);";
	private static final String MAJ = "UPDATE Livres SET titre= ?, auteur= ?, editeur= ?, isbn=? WHERE login_user= ? AND isbn=?";
	private static final String LISTER = "SELECT * FROM Livres l ORDER BY auteur, titre ASC";
	private static final String GET_BY_USER = "SELECT * FROM livres l WHERE l.login_user=?";
	private static final String GET_LOANS = "SELECT l.*, e.date_emprunt, e.login_emprunteur FROM livres l, emprunts e WHERE l.isbn=e.isbn_livre_emprunte AND l.login_user=? ORDER BY auteur, titre ASC";
	private static final String SAVE_LOANS = "INSERT INTO emprunts(login_emprunteur, login_preteur, isbn_livre_emprunte, date_emprunt) SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT * FROM emprunts WHERE isbn_livre_emprunte=? AND login_emprunteur=?);";
	private static final String DELETE_LOANS = "DELETE FROM emprunts WHERE isbn_livre_emprunte=? AND login_preteur=?";
	private static final String GET_EMPRUNTS = "SELECT l.*, e.date_emprunt, e.login_emprunteur FROM livres l, emprunts e WHERE l.isbn=e.isbn_livre_emprunte AND e.login_emprunteur=? ORDER BY auteur, titre ASC";

	public LivreDaoImpl(){
		
	}
	
	private void cloturer(ResultSet rs, PreparedStatement ps, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ex) {
		}
		try {
			if (ps != null)
				ps.close();
		} catch (Exception ex) {
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception ex) {
		}
	}

	@Override
	public List<Livre> listerLivres() {
		List<Livre> liste = new ArrayList<Livre>();
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try{
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(LISTER);
			rs = ps.executeQuery();
			while(rs.next()){
				Livre livre = new Livre(rs.getString("titre"), 
						rs.getString("auteur"), 
						rs.getString("editeur"), 
						rs.getString("isbn"),
						rs.getString("login_user"));
				liste.add(livre);
			}
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return liste;
	}

	@Override
	public boolean ajouterLivre(Livre livre, Utilisateur utilisateur) {
		boolean ajoutReussi = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {

			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(AJOUT);
			ps.setString(1, livre.getTitre().trim());
			ps.setString(2, livre.getAuteur().trim());
			ps.setString(3, livre.getEditeur().trim());
			ps.setString(4, livre.getIsbn().trim());
			ps.setString(5, utilisateur.getLogin_user().trim());
			ps.setString(6, livre.getIsbn().trim());
			ps.setString(7, utilisateur.getLogin_user().trim());
			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				ajoutReussi = true;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		return ajoutReussi;
	}

	@Override
	public List<Livre> getLivre(String titre) {
		List<Livre> livres = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET);
			ps.setString(1, titre.trim());
			rs = ps.executeQuery();
			while(rs.next()) {
				while(rs.next()){
					Livre livre = new Livre(rs.getString("titre"), 
							rs.getString("auteur"), 
							rs.getString("editeur"), 
							rs.getString("isbn"),
							rs.getString("login_user"));
					livres.add(livre);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return livres;
	}

	@Override
	public boolean supprimerLivre(String titre, Utilisateur utilisateur) {

		/* pas encore impl�ment�*/
		// et ne doit pas �tre impl�ment� :p
		
		return false;
	}

	@Override
	public boolean modifierLivre(Livre livre, Utilisateur utilisateur) {
		boolean modificationReussie = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(MAJ);
			String titre = livre.getTitre().trim();
			ps.setString(1, livre.getTitre().trim());
			ps.setString(2, livre.getAuteur().trim());
			ps.setString(3, livre.getEditeur().trim());
			ps.setString(4, livre.getIsbn().trim());
			ps.setString(5, utilisateur.getLogin_user().trim());
			ps.setString(6, livre.getIsbn().trim());
			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				modificationReussie = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		return modificationReussie;
	}

	@Override
	public List<Livre> getLivreByUser(String login_user) {
		List<Livre> liste = new ArrayList<Livre>();
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try{
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET_BY_USER);
			ps.setString(1, login_user);
			rs = ps.executeQuery();
			while(rs.next()){
				Livre livre = new Livre(rs.getString("titre"), 
						rs.getString("auteur"), 
						rs.getString("editeur"), 
						rs.getString("isbn"),
						rs.getString("login_user"));
				liste.add(livre);
			}
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return liste;
	}

	@Override
	public boolean supprimerPret(Livre livre, String utilisateur) {
		
		boolean result = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(DELETE_LOANS);
			ps.setString(1, livre.getIsbn());
			ps.setString(2, utilisateur);
		
			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				result = true;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		return result;
	}



	@Override
	public ArrayList<Livre> listerPrets(Utilisateur utilisateur) {
		ArrayList<Livre> prets = new ArrayList<Livre>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET_LOANS);
			ps.setString(1, utilisateur.getLogin_user());
			rs = ps.executeQuery();

			while(rs.next()) {
				Livre livre = new Livre(rs.getString("titre"), 
						rs.getString("auteur"), 
						rs.getString("editeur"), 
						rs.getString("isbn"),
						rs.getString("login_user"));
				livre.setEmprunteur(rs.getString("login_emprunteur"));
				livre.setDate_empreint(rs.getString("date_emprunt"));
				prets.add(livre);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		
		return prets;
	}
	@Override
	public boolean memoriserPret(Livre livre, String emprunteur, String date) {
		
		boolean result = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(SAVE_LOANS);
			ps.setString(1, emprunteur.trim());
			ps.setString(2, livre.getLoginUser().trim());
			ps.setString(3, livre.getIsbn().trim());
			ps.setString(4, date.trim());
			ps.setString(5, livre.getIsbn().trim());
			ps.setString(6, emprunteur);
			
			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				result = true;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		return result;
	}

	@Override
	public ArrayList<Livre> listerEmprunts(Utilisateur utilisateur) {
		ArrayList<Livre> emprunts = new ArrayList<Livre>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET_EMPRUNTS);
			ps.setString(1, utilisateur.getLogin_user());
			rs = ps.executeQuery();

			while(rs.next()) {
				Livre livre = new Livre(rs.getString("titre"), 
						rs.getString("auteur"), 
						rs.getString("editeur"), 
						rs.getString("isbn"),
						rs.getString("login_user"));
				livre.setEmprunteur(rs.getString("login_emprunteur"));
				livre.setDate_empreint(rs.getString("date_emprunt"));
				emprunts.add(livre);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cloturer(null, ps, con);
		}
		
		return emprunts;
	}
	
}
