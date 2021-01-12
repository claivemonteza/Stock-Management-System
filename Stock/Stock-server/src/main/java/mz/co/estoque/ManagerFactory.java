package mz.co.estoque;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sms.access.AccessManager;
import sms.company.manager.CompanyManager;
import sms.invoicing.InvoicingManager;
import sms.item.ItemManager;
import sms.management.bank.manager.BankManager;
import sms.stock.StockManager;

/**
 * <code>ManagerFactory</code> will get all classes of Manager where that have the functionalities
 * of the DAO in the database interacting with spring
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 * */
public class ManagerFactory {

	private static AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static AccessManager getAccessManager() {
		return (AccessManager) context.getBean("AccessManager",
				 AccessManager.class);
	}
	
	public static CompanyManager getCompanyManager() {
		return (CompanyManager) context.getBean("CompanyManager");
	}
	
	public static ItemManager getItemManager() {
		return (ItemManager) context.getBean("ItemManager");
	}
	
	public static StockManager getStockManager() {
		return (StockManager) context.getBean("StockManager");
	}
	
	
	public static InvoicingManager getInvoicingManager() {
		return (InvoicingManager) context.getBean("InvoicingManager");
	}
	
	
	public static BankManager getBankManager() {
		return (BankManager) context.getBean("BankManager");
	}
}
