/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.bank.exceptions;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -2630979091226253098L;
	 	public BusinessException(String message) {
	 		super(message);
	 	}
}
