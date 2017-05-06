/*@author : Bordin Sarah
 * modifi√©: Noto Ignazio
 * version : 1
 */
package be.iesca.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Livre;
import be.iesca.domaine.Utilisateur;

public class VueListePretsUtilisateur extends JPanel implements ChangeListener ,ActionListener,TableModelListener{

	private Model model;
	private Bundle bundle;
	private GestionnaireUseCases gestionnaire;
	private Livre livre;
	private ArrayList<Livre> listLivre;
	private JScrollPane jspPane;
	private DefaultTableModel dtmModel;
	private JTable jtTable;
	private JPanel jpTop;
	private JPanel jpBottom;
	private JButton jbAddPret;
	private JButton jbDelPret;
	private FenetreAjouterPret fenAjouterPret;
	
	public VueListePretsUtilisateur(Model model){
		
		this.model = model;
		this.bundle = this.model.getBundle();
		this.model.addChangeListener(this);
		
		this.gestionnaire = GestionnaireUseCases.getInstance();
		
		this.setLayout(new BorderLayout());
		
		String[] columnNames = {"Titre", "Auteur", "…diteur", "ISBN", "Date", "Emprunteur"};
		this.dtmModel = new DefaultTableModel(columnNames, 0);
		this.dtmModel.addTableModelListener(this);
		
		this.jtTable = new JTable(this.dtmModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		this.jtTable.getTableHeader().setReorderingAllowed(false);
		this.jspPane = new JScrollPane(this.jtTable);


		this.jpBottom = new JPanel();
		this.jpBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.jbAddPret = new JButton("Ajouter un prÍt");
		this.jbAddPret.addActionListener(this);
		this.jbDelPret = new JButton("Supprimer le livre");
		this.jbDelPret.addActionListener(this);
		
		this.jpBottom.add(this.jbAddPret);
		this.jpBottom.add(this.jbDelPret);
		
		this.jpTop = new JPanel();
		this.jpTop.setLayout(new BorderLayout());
		this.jpTop.add(this.jspPane, BorderLayout.CENTER);
		this.add(this.jpTop, BorderLayout.CENTER);
		this.add(this.jpBottom, BorderLayout.SOUTH);
		
		this.fenAjouterPret = new FenetreAjouterPret(this, this.model);
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		//gestion de la liste des prets
		if(this.model != null) {
			this.bundle = this.model.getBundle();
			this.gestionnaire.listerPrets(this.bundle);
			
			// l'utilisateur est remplacÈ par celui choisi lors du prÍt -> bug
			if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {

				for(int i = 0, j = this.dtmModel.getRowCount(); i < j ; i++) {
					this.dtmModel.removeRow(0);
				}
				
				this.listLivre = (ArrayList<Livre>) this.bundle.get(this.bundle.LISTE);
				for(Livre l: this.listLivre) {
					Object[] o = new Object[6];
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyy-MM-dd");

					
					
					o[0] = l.getTitre();
					o[1] = l.getAuteur();
					o[2] = l.getEditeur();
					o[3] = l.getIsbn();
					o[4] = l.getDate_empreint();
					o[5] = l.getEmprunteur();
										
					this.dtmModel.addRow(o);
				}				
			}
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.model != null) {
			if(e.getSource() == this.jbAddPret) {
				this.fenAjouterPret.setBundle(this.model.getBundle());
				this.fenAjouterPret.setVisible(true);
			}
			if(e.getSource() == this.jbDelPret){
				
				String isbn = (String) this.dtmModel.getValueAt(this.jtTable.getSelectedRow(), 3);
				Livre livre = new Livre();
				livre.setIsbn(isbn);
				this.bundle.put(Bundle.LIVRE, livre);
				this.gestionnaire.supprimerPret(this.bundle);	
				if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {
					JOptionPane.showMessageDialog(this,this.bundle.get(Bundle.MESSAGE), "Suppression", JOptionPane.INFORMATION_MESSAGE);
					this.model.setBundle(this.bundle);
				}
				else {
					JOptionPane.showMessageDialog(this,this.bundle.get(Bundle.MESSAGE), "Suppression", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {

	}
}
