/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;

import sms.GenericDAO;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.payment.model.ItemPaidPos;
import sms.invoicing.payment.model.Paid;

/**
 * @author Claive Monteza
 *
 */
public class ItemPaidPosDao extends GenericDAO<ItemPaidPos> {

	public ItemPaidPosDao() {
		super(ItemPaidPos.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CashSale> allCashSale(Paid paid) {
		String hql = " Select ipi.cashsale from ItemPaid as ipi where ipi.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return query.list();
	}
	

}
