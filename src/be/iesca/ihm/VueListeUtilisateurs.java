
//modifié: Noto Ignazio
package be.iesca.ihm;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import be.iesca.controleur.GestionnaireUseCases;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Utilisateur;

public class VueListeUtilisateurs extends JPanel implements ChangeListener {
	
	private Model model;
	private Bundle bundle;
	private GestionnaireUseCases gestionnaire;
	
	private JScrollPane jspPane;
	private DefaultTableModel dtmModel;
	private JTable jtTable;
	
	public VueListeUtilisateurs(Model model) {
		this.model = model;
		this.bundle = this.model.getBundle();
		this.model.addChangeListener(this);
		
		this.gestionnaire = GestionnaireUseCases.getInstance();
		
		this.setLayout(new BorderLayout());
		
		String[] columnNames = {"Nom d'utilisateur"};
		this.dtmModel = new DefaultTableModel(columnNames, 0);
		
		this.jtTable = new JTable(this.dtmModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		this.jtTable.getTableHeader().setReorderingAllowed(false);
		
		this.jspPane = new JScrollPane(this.jtTable);

		this.add(this.jspPane, BorderLayout.CENTER);

	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		if(this.model != null) {
			this.bundle = this.model.getBundle();
			this.gestionnaire.listerUser(this.bundle);
			if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {

				for(int i = 0, j = this.dtmModel.getRowCount(); i < j ; i++) {
					this.dtmModel.removeRow(0);
				}
				
				for(Utilisateur u: (List<Utilisateur>) this.bundle.get(Bundle.LISTE)) {
					Object[] o = new Object[1];
					o[0] = u.getNom_user() + " " + u.getPrenom_user();
					this.dtmModel.addRow(o);
				}
			}
		}
			
	}

}
