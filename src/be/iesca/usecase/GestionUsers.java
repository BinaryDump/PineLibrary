/*Author Bordin Sarah Version 1*/
package be.iesca.usecase;

import be.iesca.domaine.Bundle;

public interface GestionUsers {
	void connecterUser(Bundle bundle);
	void ajouterUser(Bundle bundle);
	void deconnecterUser(Bundle bundle);
	void listerUser (Bundle bundle);
}
