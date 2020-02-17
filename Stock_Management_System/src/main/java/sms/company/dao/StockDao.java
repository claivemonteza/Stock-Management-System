/**
 * 
 */
package sms.company.dao;

import sms.GenericDAO;
import sms.company.model.Stock;

/**
 * @author Claive Monteza
 *
 */
public class StockDao extends GenericDAO<Stock> {

	public StockDao() {
		super(Stock.class);
	}

}
