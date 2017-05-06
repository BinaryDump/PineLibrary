/*@author :Noto Ignazio
 * modifie : Bordin Sarah
 * Version : 1
 */
package be.iesca.domaine;
 

/*
 *  Une instance de cette classe contiendra des donn�es : une bi�re, liste de bieres, message, ...
 *  Elle facilite le transfert de ces donn�es de la couche de pr�sentation ou du programme de tests
 *  vers la couche business et vice-versa
 */
import java.util.HashMap;
import java.util.Map;


public class Bundle {	
	public static final String MESSAGE = "message";
	public static final String LIVRE = "livre";
	public static final String USER = "user";
	public static final String OPERATION_REUSSIE = "operationReussie";
	public static final String LISTE = "liste";
	public static final String NOM = "nom";
	public static final String PRET = "pret";
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	
	public Bundle() {
		this.map.put(Bundle.OPERATION_REUSSIE, false);
		this.map.put(Bundle.MESSAGE, "");
	}
	public void put(String clef, Object valeur) {
		this.map.put(clef, valeur);
	}
	
	public Object get(String clef) {
		return this.map.get(clef);
	}
	
	public void vider() {
		this.map.clear();		
	}
}
