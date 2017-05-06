
/* 
 * Auteur: Quentin
 */

package be.iesca.ihm;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.iesca.domaine.Bundle;

public class Model {

	private Bundle bundle;
	private ArrayList<ChangeListener> listViews;
	
	public Model() {
		this.bundle = new Bundle();
		this.bundle.put(Bundle.OPERATION_REUSSIE, true);
		this.listViews = new ArrayList<ChangeListener>(1);
	}
	
	public Model(String message) {
		this();
		this.bundle.put(Bundle.MESSAGE, message);
	}
	
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
		treatEvent(new ChangeEvent(this));
	}
	
	/** Enregistre un listener */
	public synchronized void addChangeListener(ChangeListener cl) {
		if (!listViews.contains(cl)) {
			listViews.add(cl);
		}
	}

	/** supprime un listener */
	public synchronized void removeChangeListener(ChangeListener cl) {
		if (listViews.contains(cl)) {
			listViews.remove(cl);
		}
	}

	/** notifie le(s) listener(s) */
	protected synchronized void treatEvent(ChangeEvent ev) {
		for (ChangeListener listener : listViews) {
			listener.stateChanged(ev);
		}
	}

	public Bundle getBundle() {
		return this.bundle;
	}
}
