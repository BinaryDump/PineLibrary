

/* 
 * Auteur: Quentin
 */

package be.iesca.ihm;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.iesca.domaine.Bundle;

@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame implements ChangeListener {
	
	private Model model;
	private VueControlleurConnexion vueControlleurConnexion;
	private VueControlleurOnglets vueControlleurLivres;
	
	public FenetrePrincipale() {
		super("PineLibrary");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setIconImage(new ImageIcon(this.getClass().getResource("iconinapp.png")).getImage());
		
		this.model = new Model("Veuillez vous connecter.");
		this.model.addChangeListener(this);
	
		this.vueControlleurConnexion = new VueControlleurConnexion(this.model, this);
		this.vueControlleurLivres = new VueControlleurOnglets(this.model, this);
		this.add(this.vueControlleurConnexion, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		String message = (String) this.model.getBundle().get(Bundle.MESSAGE);
		if(message.equals("Vous êtes connecté.")) {
			this.vueControlleurConnexion.setVisible(false);
			this.remove(this.vueControlleurConnexion);
			this.add(this.vueControlleurLivres, BorderLayout.CENTER);
			this.vueControlleurLivres.setVisible(true);
		}
		else if(message.equals("Veuillez vous connecter.")) {
			this.vueControlleurLivres.setVisible(false);
			this.remove(this.vueControlleurLivres);
			this.vueControlleurConnexion.setVisible(true);
			this.add(this.vueControlleurConnexion);
		}
	}
	
}
