
/* 
 * @Auteur: Quentin
 * modifié: Ignazio
 */

package be.iesca.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import be.iesca.dao.UtilisateurDao;
import be.iesca.domaine.Utilisateur;

public class UserDaoImpl implements UtilisateurDao {

	
	private static final String GET_USER = "SELECT * FROM utilisateurs WHERE login_user=? AND motDePasse_user = crypt(?, 'clefdecryptage')";
	private static final String NEW_USER = "INSERT INTO utilisateurs(login_user, motDePasse_user, nom_user, prenom_user) SELECT ?,crypt(?, 'clefdecryptage'),?,? WHERE NOT EXISTS (	SELECT * FROM utilisateurs WHERE login_user=?)";
	private static final String GET_USERS = "SELECT login_user, nom_user, prenom_user FROM utilisateurs";
	
	
	public UserDaoImpl() {
		// nothing to do
	}

	@Override
	public boolean ajouterUser(Utilisateur user) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(NEW_USER);
			ps.setString(1, user.getLogin_user().trim());
			ps.setString(2, user.getMotDePasse_user().trim());
			ps.setString(3, user.getNom_user().trim());
			ps.setString(4, user.getPrenom_user().trim());
			ps.setString(5, user.getLogin_user().trim());
			int resultat = ps.executeUpdate();
			return (resultat == 1) ? true : false; 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return false;
	}

	@Override
	public List<Utilisateur> listerUsers() {
		ArrayList<Utilisateur> usersList = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET_USERS);
			rs = ps.executeQuery();

			while(rs.next()) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setLogin_user(rs.getString("login_user"));
				utilisateur.setNom_user(rs.getString("nom_user"));
				utilisateur.setPrenom_user(rs.getString("prenom_user"));
				usersList.add(utilisateur);
			}
			
			return usersList;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return null;
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
	public Utilisateur getUtilisateur(String login_user, String motDePasseUser) {
		Utilisateur utilisateur = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			ps = con.prepareStatement(GET_USER);
			ps.setString(1, login_user.trim());
			ps.setString(2, motDePasseUser.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				utilisateur = new Utilisateur();
				utilisateur.setLogin_user(rs.getString("login_user"));
				utilisateur.setMotDePasse_user(rs.getString("motDePasse_user"));
				utilisateur.setNom_user(rs.getString("nom_user"));
				utilisateur.setPrenom_user(rs.getString("prenom_user"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			cloturer(rs, ps, con);
		}
		return utilisateur;
	}



}
