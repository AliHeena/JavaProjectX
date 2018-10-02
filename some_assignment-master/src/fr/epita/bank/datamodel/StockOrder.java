/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.bank.datamodel;

public class StockOrder {

	private Integer quantity;
	private Double ticker;
	private InvestmentAccount account;
	private Stock stock;

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the ticker
	 */
	public Double getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(Double ticker) {
		this.ticker = ticker;
	}

	/**
	 * @return the account
	 */
	public InvestmentAccount getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(InvestmentAccount account) {
		this.account = account;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
