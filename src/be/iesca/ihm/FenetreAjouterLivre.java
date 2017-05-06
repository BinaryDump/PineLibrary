package be.iesca.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Livre;

public class FenetreAjouterLivre extends JDialog implements ActionListener {
	
	private JLabel jlTitre;
	private JLabel jlAuteur;
	private JLabel jlEditeur;
	private JLabel jlIsbn;
	
	private JTextField jtfTitre;
	private JTextField jtfAuteur;
	private JTextField jtfEditeur;
	private JTextField jtfIsbn;
	
	private JButton jbCancel;
	private JButton jbConfirm;
	
	private GestionnaireUseCases gestionnaire;
	private Bundle bundle;
	private Model model;
	private Livre livre;
	private VueControllerListeLivresUtilisateur vueControllerListeLivresUtilisateur;
	
	public FenetreAjouterLivre(VueControllerListeLivresUtilisateur vueControllerListeLivresUtilisateur, Model model) 
	{
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.model = model;
		this.bundle = this.model.getBundle();
		this.vueControllerListeLivresUtilisateur = vueControllerListeLivresUtilisateur;
		
		this.jlAuteur = new JLabel("Auteur: ");
		this.jlEditeur = new JLabel("…diteur: ");
		this.jlIsbn = new JLabel("ISBN: ");
		this.jlTitre = new JLabel("Titre: ");
		
		this.jbCancel = new JButton("Annuler");
		this.jbConfirm = new JButton("Ajouter");
		this.jbConfirm.setEnabled(false);
		
		this.jbCancel.addActionListener(this);
		this.jbConfirm.addActionListener(this);
		
		this.jtfAuteur = new JTextField(50);
		this.jtfEditeur = new JTextField(50);
		this.jtfIsbn = new JTextField(50);
		this.jtfTitre = new JTextField(50);
		
		this.jtfAuteur.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFieldsFull();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {	
			}
		});
		
		this.jtfEditeur.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFieldsFull();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {	
			}
		});
		
		this.jtfIsbn.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFieldsFull();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {	
			}
		});
		
		this.jtfTitre.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFieldsFull();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {	
			}
		});
		
		this.setLayout(new GridLayout(5, 2, 5, 5));
		this.add(jlTitre);
		this.add(jtfTitre);
		this.add(jlAuteur);
		this.add(jtfAuteur);
		this.add(jlEditeur);
		this.add(jtfEditeur);
		this.add(jlIsbn);
		this.add(jtfIsbn);
		this.add(jbCancel);
		this.add(jbConfirm);
		
		this.setTitle("Ajouter un livre");
		this.pack();
		this.setSize(new Dimension(400, 200));
		this.setLocationRelativeTo(vueControllerListeLivresUtilisateur);
		
	
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
	public void checkFieldsFull() {
		if(this.jtfAuteur.getText().trim().length() > 0 &&
				this.jtfEditeur.getText().trim().length() > 0 &&
				this.jtfIsbn.getText().trim().length() > 0 &&
				this.jtfTitre.getText().trim().length() > 0) {
			this.jbConfirm.setEnabled(true);
		}
		else {
			this.jbConfirm.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbCancel) {
				this.jtfAuteur.setText("");
				this.jtfTitre.setText("");
				this.jtfEditeur.setText("");
				this.jtfIsbn.setText("");
				this.setVisible(false);
			}
			else if(ev.getSource() == this.jbConfirm) {
				if(!this.jtfAuteur.getText().equals("") &&
						!this.jtfEditeur.getText().equals("") &&
						!this.jtfIsbn.getText().equals("") &&
						!this.jtfTitre.getText().equals("")) {
					this.livre = new Livre(this.jtfTitre.getText(),
							this.jtfAuteur.getText(), 
							this.jtfEditeur.getText(), 
							this.jtfIsbn.getText(),
							this.model.getBundle().USER);
						
					this.bundle.put(Bundle.LIVRE, this.livre);
					this.gestionnaire.ajouterLivre(this.bundle);
					if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
						JOptionPane.showMessageDialog(this,this.bundle.get(Bundle.MESSAGE), "Enregistrement", JOptionPane.INFORMATION_MESSAGE);
						this.setVisible(false);
						this.jtfAuteur.setText("");
						this.jtfTitre.setText("");
						this.jtfEditeur.setText("");
						this.jtfIsbn.setText("");
						this.model.setBundle(this.bundle);
					}
					else {
						JOptionPane.showMessageDialog(this,this.bundle.get(Bundle.MESSAGE), "Enregistrement", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(this,"Veuillez renseigner la totalit√© des champs.", "Enregistrement", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}

}
