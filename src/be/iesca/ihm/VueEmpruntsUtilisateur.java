package be.iesca.ihm;

import java.awt.BorderLayout;
import java.util.List;

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

public class VueEmpruntsUtilisateur extends JPanel implements ChangeListener, TableModelListener {

	private Model model;
	private Bundle bundle;
	private GestionnaireUseCases gestionnaire;
	
	private JScrollPane jspPane;
	private DefaultTableModel dtmModel;
	private JTable jtTable;
	
	public VueEmpruntsUtilisateur(Model model){
		
		this.model = model;
		this.bundle = this.model.getBundle();
		this.model.addChangeListener(this);
		
		this.gestionnaire = GestionnaireUseCases.getInstance();
		
		this.setLayout(new BorderLayout());
		
		String[] columnNames = {"Titre", "Auteur", "Éditeur", "ISBN", "Date", "Utilisateur"};
		this.dtmModel = new DefaultTableModel(columnNames, 0);
		this.dtmModel.addTableModelListener(this);
		
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
			this.gestionnaire.listerEmprunts(this.bundle);
			if((boolean) this.bundle.get(Bundle.OPERATION_REUSSIE)) {

				for(int i = 0, j = this.dtmModel.getRowCount(); i < j ; i++) {
					this.dtmModel.removeRow(0);
				}
				
				for(Livre l: (List<Livre>) this.bundle.get(Bundle.LISTE)) {
					Object[] o = new Object[6];
					o[0] = l.getTitre();
					o[1] = l.getAuteur();
					o[2] = l.getEditeur();
					o[3] = l.getIsbn();
					o[4] = l.getDate_empreint();
					o[5] = l.getLoginUser();
					this.dtmModel.addRow(o);
				}
			}
		}
				
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
