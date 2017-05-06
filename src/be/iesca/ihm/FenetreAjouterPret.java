/*@author : Bordin Sarah
 * Corrigé par Quentin
 * version : 1
 */

package be.iesca.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;



@SuppressWarnings("serial")
public class FenetreAjouterPret extends JDialog implements ActionListener, ChangeListener {

	
	private GestionnaireUseCases gestionnaire;
	private Bundle bundle;
	private Model model;
	
	private JComboBox<String>jcboxLivres;
	private JComboBox<String>jcboxUsers;
	
	private List<Utilisateur>listeUsers;
	private ArrayList<Livre> listeLivres;
	
	private JLabel jlLivres;
	private JLabel jlUsers;

	private JButton jbAjouter;
	private JButton jbAnnuler;
	
	private DefaultComboBoxModel<String> dcbmModelLivres;
	private DefaultComboBoxModel<String> dcbmModelUsers;
	
	public FenetreAjouterPret(VueListePretsUtilisateur vueListePretUtilisateur,Model model){
	
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.model = model;
		this.bundle = this.model.getBundle();
		this.model.addChangeListener(this);

		this.setLayout(new GridLayout(3,2));
		this.setTitle("Ajouter un prêt");
		this.pack();
		this.setSize(new Dimension(500, 125));
		this.setLocationRelativeTo(vueListePretUtilisateur);

		this.dcbmModelLivres = new DefaultComboBoxModel<>();
		this.dcbmModelUsers = new DefaultComboBoxModel<>();
		
		this.jcboxLivres = new JComboBox<>(this.dcbmModelLivres);
		this.jcboxUsers = new JComboBox<>(this.dcbmModelUsers);
		
		this.jlUsers = new JLabel(" Choisir l'utilisateur : ");
		this.jlLivres = new JLabel(" Choisir un livre : ");
		
		this.jbAjouter = new JButton("Ajouter");
		this.jbAnnuler = new JButton("Annuler");
		
		this.jbAjouter.addActionListener(this);
		this.jbAnnuler.addActionListener(this);
		this.jcboxLivres.addActionListener(this);
		this.jcboxUsers.addActionListener(this);
			
		this.add(jlUsers);
		this.add(jcboxUsers);
		this.add(jlLivres);
		this.add(jcboxLivres);
		this.add(jbAjouter);	
		this.add(jbAnnuler);			

	}
	
	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void stateChanged(ChangeEvent e) {
		this.bundle = this.model.getBundle();

		this.gestionnaire.livreParUtilisateur(this.bundle);
		this.listeLivres = (ArrayList<Livre>) bundle.get(Bundle.LISTE);
		this.gestionnaire.listerUser(this.bundle);
		this.listeUsers = (ArrayList<Utilisateur>) bundle.get(Bundle.LISTE);

		this.dcbmModelLivres.removeAllElements();
		this.dcbmModelUsers.removeAllElements();
		
		try {
			for (int i = 0; i < this.listeLivres.size(); i++) {
				this.dcbmModelLivres.addElement(this.listeLivres.get(i).toString());
			}

			for (int i = 0; i < this.listeUsers.size(); i++) {
				this.dcbmModelUsers.addElement(this.listeUsers.get(i).toString());
			}
		} catch(Exception e_) {
			// a la déconnexion, il envoie une exception nullpointerexception
		}


	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbAnnuler){
				this.setVisible(false);
			}
		
			if(ev.getSource() == this.jbAjouter) {
				Object indexBook = jcboxLivres.getSelectedIndex();
				Object indexUser = jcboxUsers.getSelectedIndex();
				 
				Utilisateur userChoisi = this.listeUsers.get((int) indexUser);
				Livre livreChoisi=this.listeLivres.get((int) indexBook);
				livreChoisi.setEmprunteur(userChoisi.getLogin_user());

				this.bundle.put(Bundle.LIVRE,livreChoisi);
				
				this.gestionnaire.memoriserPret(bundle);
				this.gestionnaire.listerPrets(this.bundle);
			
				if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
					JOptionPane.showMessageDialog(this,"Ajout du prêt réussi", "pret", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					this.model.setBundle(this.bundle);
				}
				else {
					JOptionPane.showMessageDialog(this,"Ajout du prêt impossible", "ajout", JOptionPane.ERROR_MESSAGE);
				}
			}							
		}
	}
}

