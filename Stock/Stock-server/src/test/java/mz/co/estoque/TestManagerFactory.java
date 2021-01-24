package mz.co.estoque;

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
public class TestManagerFactory {

	private static AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
	
	public static AccessManager getAccessManager() {
		return (AccessManager) context.getBean("AccessManager",
				 AccessManager.class);
	}
	
	public static ItemManager getItemManager() {
		return (ItemManager) context.getBean("ItemManager");
	}

}
