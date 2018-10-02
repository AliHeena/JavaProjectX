/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.bank.launcher;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.epita.bank.application.bankapplication;
import fr.epita.bank.business.InvestmentService;
import fr.epita.bank.datamodel.Customer;
import fr.epita.bank.datamodel.InvestmentAccount;
import fr.epita.bank.datamodel.SavingsAccount;
import fr.epita.bank.datamodel.Stock;
import fr.epita.bank.datamodel.StockOrder;
import fr.epita.bank.exceptions.BusinessException;
import fr.epita.bank.exceptions.LowBalanceException;
import fr.epita.bank.exceptions.StockException;
import org.apache.log4j.Logger;

public class Launcher {
	static Logger log = Logger.getLogger(Launcher.class.getName());

		@SuppressWarnings("resource")
		public static void main(String[] args) throws BusinessException, SQLException, InterruptedException {
			Connection c = null;
			Statement s = null;
			try {
				ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
				bankapplication configmapper = mapper.readValue(new File("configs/config.yaml"), bankapplication.class);
				Class.forName(configmapper.getDriver());
				c = DriverManager.getConnection(configmapper.getHostname(), configmapper.getUsername(), configmapper.getPassword());
			} catch (Exception e) {
				log.error("JSON Read Exception");
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

			final Customer info = new Customer();
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your Name");
			String name = sc.nextLine();
			log.info("User entered name: "+name);
			info.setName(name);
			System.out.println("Enter your UserId");
			String userId = sc.nextLine().toLowerCase();
			log.info("User entered userId: "+userId);
			if (name.length() == 0 || userId.length() == 0) {
				log.error("User entered details invalid");
				throw new BusinessException("There was an error in processing your transaction- Invalid credentials");
			}
			
			s = c.createStatement();
			String username_Check = "SELECT * FROM BANK where userId='" + userId + "'";
			
			ResultSet rs = s.executeQuery(username_Check);
			if (rs.next()) {
				log.info("User login exists"+ userId);
				System.out.println("Enter your password");
				String password = sc.nextLine();
				String password_Check = "SELECT * FROM BANK where userId='" + userId + "'" + "AND password='" + password + "'";
				rs = s.executeQuery(password_Check);
				if (rs.next()) {
					System.out.println("Hello " + info.getName().substring(0, 1).toUpperCase().concat(info.getName().substring(1, info.getName().length())) + "." + " " + "Your login was successful!!");
					operations(userId, sc, c, s, rs);
				}
				else {
					System.out.println("Password doesn't match!! ");
					log.info("Password doesn't match!!"+ userId);
					System.out.println("Enter your password");
					String password_again = sc.nextLine();
					String password_CheckAgain = "SELECT * FROM BANK where userId='" + userId + "'" + "AND password='" + password_again + "'";
					rs = s.executeQuery(password_CheckAgain);
					if (rs.next()) {
						System.out.println("Hello " + info.getName().substring(0, 1).toUpperCase().concat(info.getName().substring(1, info.getName().length())) + "." + " " + "Your login was successful!!");
						operations(userId, sc, c, s, rs);
					}
					else {
						System.out.println("You have exceeded the number of incorrect attempts!!");
					}
				}
			} 
			else {
				System.out.println("Hello " + info.getName() + "." + " " + "Your userId does not exist");
				System.out.println("Can we create an account (Yes/No)?");
				log.info("Created new Account"+ userId);
				String selection = sc.nextLine().toLowerCase();
				if (selection.equals("yes")) {
					System.out.println("Enter your password");
					String password = sc.nextLine();
					System.out.println("Enter your EmailId");
					String emailId = sc.nextLine().toLowerCase();
					info.setName(name);
					String create_Account = "INSERT into BANK values('" + name + "','" + userId + "','" + password + "','" + emailId + "')";
					s.execute(create_Account);
					System.out.println("Account created successfully!!");
					operations(userId, sc, c, s, rs);
				} else
				{
					System.out.println("Thank you!! Have a good day!!");
				}
				sc.close();
			}
		}

		private static void operations(String userId, Scanner sc, Connection c, Statement s, ResultSet rs)
				throws SQLException, InterruptedException {
			String ops;
			System.out.println("What operation do you want to perform? Update|Check Balance|Deposit|Delete Account|Exit");
			ops = sc.next().toLowerCase();
			System.out.println(ops+"Testing");
			if(ops.length()==0) {
				System.out.println("Testing");
				operations(userId, sc, c, s, rs);
			}
			if (ops.equals("update")) {
				System.out.println("What do you want to update? Password|EmailId");
				String selection = sc.next().toLowerCase();
				if (selection.equals("password")) {
					System.out.print("Enter new Password: ");
					String new_Password = sc.next();
					String update_Password = "UPDATE BANK SET password='" + new_Password + "' WHERE userId='" + userId
							+ "' ";
					s.execute(update_Password);
					Thread.sleep(2000);
					System.out.println("Password updated successfully!!");
					log.info("User password operation completed"+ userId);
					operations(userId, sc, c, s, rs);
				} else {
					System.out.print("Enter new EmailId: ");
					String new_EmailId = sc.next().toLowerCase();
					String update_EmailId = "UPDATE BANK SET emailid='" + new_EmailId + "' WHERE userId='" + userId + "' ";
					s.execute(update_EmailId);
					Thread.sleep(2000);
					System.out.println("EmailId updated successfully!!");
					log.info(" operation completed"+ userId);
					operations(userId, sc, c, s, rs);
				}
			} else if (ops.equals("delete")) {
				System.out.println("Your account is being deleted");
				String delete_UserId = "DELETE from BANK where userId='" + userId + "'";
				s.execute(delete_UserId);
				Thread.sleep(5000);
				log.info("User has been deleted"+ userId);
				System.out.print("Your account was deleted successfully!!");

			} else if (ops.equals("check balance")) {
				int zero_balance=0;
				String check_Balance = "SELECT sbalance from BANK where userId='" + userId + "'";
				rs = s.executeQuery(check_Balance);
				if (rs.next()) {
					if(rs.getString("sbalance")==null) {
						System.out.println("Your Savings Account Balance is: "+zero_balance);
						operations(userId, sc, c, s, rs);
					}
					
				else{
					System.out.println("Your Savings Account Balance is: " + rs.getInt("sbalance"));
					operations(userId, sc, c, s, rs);
					log.info("Check Balance operation completed"+ userId);
				}
				}
			} 
			else if (ops.equals("deposit")) {
				final SavingsAccount savings = new SavingsAccount();
				System.out.println("Enter amount to deposit: ");
				int amount = sc.nextInt();
				String check_Balance_1 = "SELECT sbalance from BANK where userId='" + userId + "'";
				rs = s.executeQuery(check_Balance_1);
				if (rs.next()) {
					int new_balance = rs.getInt("sbalance") + amount;
					String update_Balance = "UPDATE BANK SET sBalance='" + new_balance + "' WHERE userId='" + userId + "' ";
					s.execute(update_Balance);
					Thread.sleep(5000);
					System.out.println("Balance Updated Successfully!!");
					final InvestmentAccount investment = new InvestmentAccount();
					investment.setBalance((double) new_balance);
					investment.setCustomer(userId);
					savings.setBalance((double) new_balance);
					final Stock gold = new Stock();
					gold.setName("gold");
					gold.setUnitPrice(15.0);
					final StockOrder stockOrder = new StockOrder();
					stockOrder.setAccount(investment);
					stockOrder.setQuantity(3);
					stockOrder.setStock(gold);
					stockOrder.setTicker(15d);
					try {
						int new_ibalance = (int)InvestmentService.validateStockOrder(stockOrder);
						System.out.println("Your accounts final balance is: " + new_ibalance);
						String update_iBalance = "UPDATE BANK SET iBalance='" + new_ibalance + "' WHERE userId='" + userId
								+ "' ";
						s.execute(update_iBalance);
						
					} catch (final BusinessException | LowBalanceException | StockException e) {
						System.out.println("There was an error in processing your transaction");
					}
					operations(userId, sc, c, s, rs);
					log.info("Deposit operation completed"+ userId);
				}
			}
			if (ops.equals("exit")) {
				System.out.println("Thank you!! Have a god day!!");
				log.info("User operations completed"+ userId);
			}
			else {
				System.out.println("Select from on of the following operations Update|Check Balance|Deposit|Delete Account|Exit ");
				operations(userId, sc, c, s, rs);
			}
			}
		}


	

	