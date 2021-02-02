package mz.co.stock;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mz.co.stock.access.AccessManager;
import mz.co.stock.items.ItemManager;


/**
 * <code>ManagerFactory</code> will get all classes of Manager where that have the functionalities
 * of the DAO in the database interacting with spring
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.8
 * */
public class ManagerFactory {

	private static AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	
	public static AccessManager getAccessManager() {
		return (AccessManager) context.getBean("AccessManager");
	}
	
	public static ItemManager getItemManager() {
		return (ItemManager) context.getBean("ItemManager");
	}
	/*
	public static CompanyManager getCompanyManager() {
		return (CompanyManager) SpringUtil.getBean("CompanyManager",
				CompanyManager.class);
	}
	
	
	
	public static StockManager getStockManager() {
		return (StockManager) SpringUtil.getBean("StockManager",
				StockManager.class);
	}
	
	
	public static InvoicingManager getInvoicingManager() {
		return (InvoicingManager) SpringUtil.getBean("InvoicingManager",
				InvoicingManager.class);
	}
	
	
	public static BankManager getBankManager() {
		return (BankManager) SpringUtil.getBean("BankManager",
				BankManager.class);
	}
	*/
}
