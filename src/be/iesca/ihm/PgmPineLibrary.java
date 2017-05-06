
/* 
 * Auteur: Quentin
 */

package be.iesca.ihm;

import javax.swing.SwingUtilities;

//@author NOTO Ignazio Version 1

public class PgmPineLibrary {

	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FenetrePrincipale fp = new FenetrePrincipale();
				fp.setVisible(true);
			}
		});
	}

}
