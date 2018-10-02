/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.bank.datamodel;

public class SavingsAccount extends Account {
	private Double interrestRate;

	/**
	 * @return the interrestRate
	 */
	public Double getInterrestRate() {
		return interrestRate;
	}

	/**
	 * @param interrestRate
	 *            the interrestRate to set
	 */
	public void setInterrestRate(Double interrestRate) {
		this.interrestRate = interrestRate;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SavingsAccount [interrestRate=" + interrestRate + ", balance=" + getBalance() + "€]";
	}

}

