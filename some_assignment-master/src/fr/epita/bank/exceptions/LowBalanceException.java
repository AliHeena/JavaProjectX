package fr.epita.bank.exceptions;

public class LowBalanceException extends Exception {

	private static final long serialVersionUID = -8321353181411598105L;

	public LowBalanceException(String message) {
	super(message);
}


}
