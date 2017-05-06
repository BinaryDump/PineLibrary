
/* 
 * Auteur: Quentin
 * Modifié: Ignazio NOTO
 */

package be.iesca.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;

public class VueControlleurConnexion extends JPanel implements ChangeListener, ActionListener {
	
	private JLabel jlUser;
	private JLabel jlPassword;
	private JLabel jlNotAccountYet;
	private JTextField jtfUser;
	private JPasswordField jpfPassword;
	private JButton jbConnection;
	private JButton jbGetAccount;
	
	private Model model;
	private Bundle bundle;
	private GestionnaireUseCases gestionnaire;
	private JFrame jframe;
	private Utilisateur utilisateur;
	
	private JPanel jpTitle;
	private JPanel jpForm;
	private JPanel jpCenter;
	
	private FenetreInscription inscription;
	
	public VueControlleurConnexion(Model model, JFrame jframe) {
		this.bundle = new Bundle();
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.jframe = jframe;
		this.utilisateur = new Utilisateur();
		
		JLabel logoLabel = new JLabel(new ImageIcon("iconinapp.png")); //logo [le fichier doit être à la base du projet, pas dans src]
		
		JPanel jpPanLogo = new JPanel();
		jpPanLogo.setLayout(new BorderLayout());
		jpPanLogo.add(logoLabel, BorderLayout.SOUTH);
		
		
		this.setPreferredSize(new Dimension(600, 400));
		
		this.jlUser = new JLabel("Utilisateur: ");
		this.jlPassword = new JLabel("Mot de passe: ");
		this.jlNotAccountYet = new JLabel("Pas encore de compte ?");
		
		//this.jlConnection.setHorizontalAlignment(JLabel.CENTER);
		this.jlUser.setHorizontalAlignment(JLabel.RIGHT);
		this.jlPassword.setHorizontalAlignment(JLabel.RIGHT);
		this.jlNotAccountYet.setHorizontalAlignment(JLabel.RIGHT);

		
		this.jtfUser = new JTextField(20);
		this.jpfPassword = new JPasswordField(20);
		
		this.jbConnection = new JButton("Se connecter");
		this.jbGetAccount = new JButton("S'enregistrer");

		this.jbConnection.addActionListener(this);
		this.jbGetAccount.addActionListener(this);
		
		if(model != null) {
			this.model = model;
			this.model.addChangeListener(this);
			this.inscription = new FenetreInscription(this.jframe, this.model);
			
		}
		
		this.setLayout(new BorderLayout());
		
		this.jpTitle = new JPanel();
		this.jpForm = new JPanel();
		this.jpTitle.setLayout(new FlowLayout());
		this.jpForm.setLayout(new GridLayout(4, 2, 10, 10));
				
		this.jpForm.add(jlUser);
		this.jpForm.add(jtfUser);
		this.jpForm.add(jlPassword);
		this.jpForm.add(jpfPassword);
		this.jpForm.add(new JLabel(""));
		this.jpForm.add(jbConnection);
		this.jpForm.add(jlNotAccountYet);
		this.jpForm.add(jbGetAccount);
		
		this.jpCenter = new JPanel();
		this.jpCenter.setPreferredSize(new Dimension(400,100));
		this.jpCenter.add(this.jpForm);
		
		this.add(jpCenter, BorderLayout.CENTER);
		this.add(jpPanLogo,BorderLayout.NORTH);


		// only for dev
		this.jtfUser.setText("Kitsune");
		this.jpfPassword.setText("kitsune");
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		String message = (String) this.model.getBundle().get(Bundle.MESSAGE);
		if(message.equals("Utilisateur inscrit correctement")) {
			this.utilisateur = (Utilisateur) this.model.getBundle().get(Bundle.USER);
			this.jtfUser.setText(this.utilisateur.getLogin_user());
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbConnection) {
				this.utilisateur.setLogin_user(this.jtfUser.getText());
				this.utilisateur.setMotDePasse_user(new String(this.jpfPassword.getPassword()));
				this.bundle.put(Bundle.USER, this.utilisateur);
				this.gestionnaire.connecterUser(this.bundle);
				if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
					this.jpfPassword.setText("");
					this.model.setBundle(this.bundle);
				}
				else {
					JOptionPane.showMessageDialog(this, bundle.get(Bundle.MESSAGE), "Identification", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(ev.getSource() == this.jbGetAccount) {
				this.inscription.setVisible(true);
			}
		}
	}
	
	public Bundle getBundle() {
		return this.bundle;
	}

}
