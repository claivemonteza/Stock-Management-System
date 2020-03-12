/**
 * 
 */
package sms.company.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.company.model.Stock;

/**
 * <code>StockDao</code> is the class that will access to the system by user name and
 * password.
 * 
 * @see Stock
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class StockDao extends GenericDAO<Stock> {

	public StockDao() {
		super(Stock.class);
	}

}
