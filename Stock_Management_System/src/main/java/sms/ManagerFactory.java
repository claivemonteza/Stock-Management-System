package sms;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zkoss.zkplus.spring.SpringUtil;

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

	public static Session getHibernateSession() {
		return ((SessionFactory) SpringUtil.getBean("sessionFactory",
				SessionFactory.class)).getCurrentSession();
	}
	
	public static AccessManager getAccessManager() {
		return (AccessManager) SpringUtil.getBean("AccessManager",
				 AccessManager.class);
	}
	
	public static CompanyManager getCompanyManager() {
		return (CompanyManager) SpringUtil.getBean("CompanyManager",
				CompanyManager.class);
	}
	
	public static ItemManager getItemManager() {
		return (ItemManager) SpringUtil.getBean("ItemManager",
				ItemManager.class);
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
}
