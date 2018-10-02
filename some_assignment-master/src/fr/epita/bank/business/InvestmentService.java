/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.bank.business;

import fr.epita.bank.datamodel.InvestmentAccount;
import fr.epita.bank.datamodel.StockOrder;
import fr.epita.bank.exceptions.BusinessException;
import fr.epita.bank.exceptions.LowBalanceException;
import fr.epita.bank.exceptions.StockException;

public class InvestmentService {

	public static double validateStockOrder(StockOrder order) throws BusinessException, StockException, LowBalanceException {
		final InvestmentAccount account = order.getAccount();
		final Double balance = account.getBalance();
		if (balance == null || balance == 0) {
			throw new LowBalanceException("Balance too low!!");
		}
		if (order.getStock() == null) {
			throw new StockException("Balance too low!!");
		}
		// perform operation : final balance = initialBalance - (stockUnitPrice * stockOrderQuantity) - ticker
		final double finalBalance = balance - order.getStock().getUnitPrice() * order.getQuantity() - order.getTicker();

		account.setBalance(finalBalance);
		return account.getBalance();

	}

}
