package be.iesca.ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;

public class FenetreInscription extends JDialog implements ActionListener {
	
	private JLabel jlUsername;
	private JLabel jlPasswd;
	private JLabel jlPasswdConfirm;
	private JLabel jlFirstname;
	private JLabel jlLastname;
	
	private JTextField jtfUsername;
	private JTextField jtfFirstname;
	private JTextField jtfLastname;
	
	private JPasswordField jpfPasswd;
	private JPasswordField jpfPasswdConfirm;
	
	private JButton jbCancel;
	private JButton jbConfirm;
	
	private GestionnaireUseCases gestionnaire;
	private Bundle bundle;
	private Model model;
	private Utilisateur utilisateur;
	
	public FenetreInscription(JFrame jframe, Model model) {
		super(jframe, true);
		this.gestionnaire = GestionnaireUseCases.getInstance();
		this.bundle = new Bundle();
		this.model = model;
		
		this.jlFirstname = new JLabel("Pr�nom: ");
		this.jlLastname = new JLabel("Nom: ");
		this.jlPasswd = new JLabel("Mot de passe: ");
		this.jlPasswdConfirm = new JLabel("Confirmation de mot de passe: ");
		this.jlUsername = new JLabel("Nom d'utilisateur: ");
		
		this.jlFirstname.setHorizontalAlignment(JLabel.RIGHT);
		this.jlLastname.setHorizontalAlignment(JLabel.RIGHT);
		this.jlPasswd.setHorizontalAlignment(JLabel.RIGHT);
		this.jlPasswdConfirm.setHorizontalAlignment(JLabel.RIGHT);
		this.jlUsername.setHorizontalAlignment(JLabel.RIGHT);
		
		this.jtfFirstname = new JTextField(20);
		this.jtfLastname = new JTextField(20);
		this.jtfUsername = new JTextField(20);
		this.jpfPasswd = new JPasswordField(20);
		this.jpfPasswdConfirm = new JPasswordField(20);
		
		
		this.jbCancel = new JButton("Annuler");
		this.jbConfirm = new JButton("Confirmer");
		
		this.jbCancel.addActionListener(this);
		this.jbConfirm.addActionListener(this);
		
		this.jtfUsername.setToolTipText("Un nom d'utilisateur doit comprendre entre trois et vingt caractères");
		this.jpfPasswd.setToolTipText("Le mot de passe doit comprendre entre 6 et 30 caractères avec un minimum de chiffre et une majuscule.");
		
		this.jbConfirm.setEnabled(false);
		
		this.jtfUsername.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkFields();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		this.jtfFirstname.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkFields();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		this.jtfLastname.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkFields();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		this.jpfPasswd.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkFields();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		this.jpfPasswdConfirm.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkFields();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	
		this.setLayout(new GridLayout(6, 2));
		this.add(this.jlUsername);
		this.add(this.jtfUsername);
		this.add(this.jlPasswd);
		this.add(this.jpfPasswd);
		this.add(this.jlPasswdConfirm);
		this.add(this.jpfPasswdConfirm);
		this.add(this.jlFirstname);
		this.add(this.jtfFirstname);
		this.add(this.jlLastname);
		this.add(this.jtfLastname);
		this.add(this.jbCancel);
		this.add(this.jbConfirm);
		
		this.setTitle("Enregistrement");
		this.pack();
		this.setLocationRelativeTo(jframe);
		
	}
	
	public void checkFields() {
		if(this.jtfFirstname.getText().trim().length() >= 1 &&
				this.jtfFirstname.getText().trim().length() <= 60 &&
				this.jtfLastname.getText().trim().length() >= 1 &&
				this.jtfLastname.getText().trim().length() <= 60 &&
				this.jtfUsername.getText().trim().length() >= 3 &&
				this.jtfUsername.getText().trim().length() <= 20 ) {
			
			boolean containNumber = false;
			boolean containUppercase = false;
			
			String passwd = new String(this.jpfPasswd.getPassword());
			String passwdConfirm = new String(this.jpfPasswdConfirm.getPassword());
			
			if(passwd.equals(passwdConfirm)) {
				for(char c: passwd.toCharArray()) {
					if(Character.isDigit(c)) {
						containNumber = true;
					}
					if(Character.isUpperCase(c)) {
						containUppercase = true;
					}
				}
				
				if(containNumber && containUppercase) {
					this.jbConfirm.setEnabled(true);
				}
				else {
					this.jbConfirm.setEnabled(false);
				}
			}
			else {
				this.jbConfirm.setEnabled(false);
			}
		}
		else {
			this.jbConfirm.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbCancel) {
				this.setVisible(false);
			}
			else if(ev.getSource() == this.jbConfirm) {
				String passwd = new String(this.jpfPasswd.getPassword());
				String passwdConfirm = new String(this.jpfPasswdConfirm.getPassword());
	
				if(!this.jtfFirstname.getText().equals("") &&
						!this.jtfLastname.getText().equals("") &&
						!this.jtfUsername.getText().equals("") &&
						!passwd.equals("") &&
						!passwdConfirm.equals("")) {
					if(passwd.equals(passwdConfirm)) {
						this.utilisateur = new Utilisateur();
						this.utilisateur.setLogin_user(this.jtfUsername.getText());
						this.utilisateur.setMotDePasse_user(passwd);
						this.utilisateur.setNom_user(this.jtfLastname.getText());
						this.utilisateur.setPrenom_user(this.jtfFirstname.getText());
						this.bundle.put(Bundle.USER, this.utilisateur);
						this.gestionnaire.ajouterUser(this.bundle);
						
						if(!(boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
							JOptionPane.showMessageDialog(this, this.bundle.get(Bundle.MESSAGE), "Enregistrement", JOptionPane.ERROR_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(this, this.bundle.get(Bundle.MESSAGE), "Enregistrement", JOptionPane.INFORMATION_MESSAGE);
							this.model.setBundle(this.bundle);
							this.setVisible(false);
						}
					}
					else {
						JOptionPane.showMessageDialog(this,"Les mots de passe ne correspondent pas.", "Enregistrement", JOptionPane.ERROR_MESSAGE);
						this.jpfPasswd.setText("");
						this.jpfPasswdConfirm.setText("");
					}

				}
				
				else {
					JOptionPane.showMessageDialog(this,"Veuillez renseigner la totalité des champs.", "Enregistrement", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public Bundle getBundle() {
		return bundle;
	}

}
