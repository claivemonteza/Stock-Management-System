package sms.invoicing.cash.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.cash.model.CashSale;

/**
 * <code>CashSaleDao</code> is the class that will make the search at the database
 * at the table CashSale.
 * 
 * @see CashSaleDao
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class CashSaleDao extends GenericDAO<CashSale> {

	public CashSaleDao() {
		super(CashSale.class);
	}

}
