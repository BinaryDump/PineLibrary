package be.iesca.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class VueControllerListeLivresUtilisateur extends JPanel implements ActionListener, ChangeListener {
	
	private Model model;
	private Bundle bundle;
	private GestionnaireUseCases gestionnaire;
	
	private JScrollPane jspPane;
	private DefaultTableModel dtmModel;
	private JTable jtTable;
	private JPanel jpTop;
	private JPanel jpBottom;
	private JButton jbAddBook;
	
	private FenetreAjouterLivre fenAjouterLivre;
	
	public VueControllerListeLivresUtilisateur(Model model) {
		this.model = model;
		this.bundle = this.model.getBundle();
		this.model.addChangeListener(this);
		
		this.gestionnaire = GestionnaireUseCases.getInstance();
		
		this.setLayout(new BorderLayout());
		
		String[] columnNames = {"Titre", "Auteur", "Éditeur", "ISBN"};
		this.dtmModel = new DefaultTableModel(columnNames, 0);
		
		this.jtTable = new JTable(this.dtmModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		this.jtTable.getTableHeader().setReorderingAllowed(false);		
		this.jspPane = new JScrollPane(this.jtTable);


		this.jpBottom = new JPanel();
		this.jpBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.jbAddBook = new JButton("Ajouter un livre");
		this.jbAddBook.addActionListener(this);
		
		this.jpBottom.add(this.jbAddBook);
		
		this.jpTop = new JPanel();
		this.jpTop.setLayout(new BorderLayout());
		this.jpTop.add(this.jspPane, BorderLayout.CENTER);
		this.add(this.jpTop, BorderLayout.CENTER);
		this.add(this.jpBottom, BorderLayout.SOUTH);
		this.jtTable.getTableHeader().setReorderingAllowed(false);
		
		this.fenAjouterLivre = new FenetreAjouterLivre(this, this.model);

	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		if(this.model != null) {
			
			this.bundle = this.model.getBundle();
			this.gestionnaire.livreParUtilisateur(this.bundle);
			if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
				
				for(int i = 0, j = this.dtmModel.getRowCount(); i < j ; i++) {
					this.dtmModel.removeRow(0);
				}
				
				for(Livre l: (List<Livre>) this.bundle.get(Bundle.LISTE)) {
					Object[] o = new Object[4];
					o[0] = l.getTitre();
					o[1] = l.getAuteur();
					o[2] = l.getEditeur();
					o[3] = l.getIsbn();
					this.dtmModel.addRow(o);
				}
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.model != null) {
			if(ev.getSource() == this.jbAddBook) {
				this.fenAjouterLivre.setBundle(this.model.getBundle());
				this.fenAjouterLivre.setVisible(true);
			}
		}
		
	}
}
