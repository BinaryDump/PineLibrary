/*@author : Hussin Quentin
 * Modifie : Bordin Sarah
 * version :1
 */
package be.iesca.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import be.iesca.domaine.Bundle;

public class VueControlleurOnglets extends JPanel implements ActionListener {

	private JButton jbDeconnexion;
	private Model model;
	private Bundle bundle;
	
	private JTabbedPane jtpOnglets;
	private JPanel jpBottom;
	private JPanel jpTop;
	
	private VueListeUtilisateurs vueListeUtilisateurs;
	private VueControllerListeLivresUtilisateur vueListeLivresUtilisateur;
	private VueListePretsUtilisateur vueListePretsUtilisateur;
	private VueEmpruntsUtilisateur vueEmpruntsUtilisateurs;
	
	public VueControlleurOnglets(Model model, JFrame jframe) {
		
		this.model = model;
		this.bundle = this.model.getBundle();

		this.jbDeconnexion = new JButton("Me déconnecter");
		this.jbDeconnexion.addActionListener(this);

		this.setLayout(new BorderLayout(5, 5));
		this.jpBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.jpBottom.add(this.jbDeconnexion);
		
		this.vueListeUtilisateurs = new VueListeUtilisateurs(this.model);
		this.vueListeLivresUtilisateur = new VueControllerListeLivresUtilisateur(this.model);
		this.vueListePretsUtilisateur=new VueListePretsUtilisateur(this.model);
		this.vueEmpruntsUtilisateurs = new VueEmpruntsUtilisateur(model);
		
		this.jtpOnglets = new JTabbedPane(JTabbedPane.TOP);
		this.jtpOnglets.add("Mes livres", this.vueListeLivresUtilisateur);
		this.jtpOnglets.add("Liste des utilisateurs", this.vueListeUtilisateurs);
		this.jtpOnglets.add("Mes prêts",this.vueListePretsUtilisateur);
		this.jtpOnglets.add("Mes emprunts",this.vueEmpruntsUtilisateurs);
		
		this.jpTop = new JPanel();
		this.jpTop.setLayout(new GridLayout(1, 1));
		this.jpTop.add(jtpOnglets);
	
		this.add(this.jpTop, BorderLayout.CENTER);
		this.add(this.jpBottom, BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbDeconnexion) {
				this.bundle.put(Bundle.USER, null);
				this.bundle.put(Bundle.MESSAGE, "Veuillez vous connecter.");
				this.model.setBundle(this.bundle);
			}
		}
		
	}
}
