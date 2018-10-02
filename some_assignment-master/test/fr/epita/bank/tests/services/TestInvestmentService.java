/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.bank.tests.services;

import fr.epita.bank.business.InvestmentService;
import fr.epita.bank.datamodel.Customer;
import fr.epita.bank.datamodel.InvestmentAccount;
import fr.epita.bank.datamodel.Stock;
import fr.epita.bank.datamodel.StockOrder;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class should be used as follows:
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 * ${tags}
 */
public class TestInvestmentService {


	public static void main(String[] args) throws Exception {

		nominalValidateStockOrder();
		nullStockValidateStockOrder();
	}


	private static void nominalValidateStockOrder() throws Exception {
		// Given (context)
		final Customer john = new Customer();
		john.setName("john");

		final InvestmentAccount investment = new InvestmentAccount();
		investment.setBalance(150d);
		investment.setCustomer("john");

		final Stock gold = new Stock();
		gold.setName("gold");
		gold.setUnitPrice(15.0);

		final StockOrder stockOrder = new StockOrder();
		stockOrder.setAccount(investment);
		stockOrder.setQuantity(3);
		stockOrder.setStock(gold);
		stockOrder.setTicker(15d);

		// When (operation to validate)
		InvestmentService.validateStockOrder(stockOrder);

		// Then (validation)

		// We expect the final balance to be 150 - 3 * 15 - 15 => 150 - 60 = 90
		if (investment.getBalance() != 90) {
			throw new Exception("the final balance is not correct, expected 90,  got " + investment.getBalance());
		}
	}

	private static void nullStockValidateStockOrder() throws Exception {
		// Given (context)
		final Customer john = new Customer();
		john.setName("john");

		final InvestmentAccount investment = new InvestmentAccount();
		investment.setBalance(150d);
		investment.setCustomer("john");

		final StockOrder stockOrder = new StockOrder();
		stockOrder.setAccount(investment);
		stockOrder.setQuantity(3);
		stockOrder.setTicker(15d);

		// When (operation to validate)
		InvestmentService.validateStockOrder(stockOrder);

		// Then (validation)

		// No stock so the operation should not have taken place
		if (investment.getBalance() != 150) {
			throw new Exception("the final balance is not correct, expected 90,  got " + investment.getBalance());
		}
	}

}
