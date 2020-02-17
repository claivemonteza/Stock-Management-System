package sms.invoicing.cash.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.cash.model.CashSale;


//TODO create documentation

@Repository
public class CashSaleDao extends GenericDAO<CashSale> {

	public CashSaleDao() {
		super(CashSale.class);
	}

}
