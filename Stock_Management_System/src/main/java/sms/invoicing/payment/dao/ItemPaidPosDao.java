/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.payment.model.ItemPaidPos;
import sms.invoicing.payment.model.Paid;

/**
 * <code>ItemPaidPosDao</code> is the class that will make the search at the database
 * at the table ItemPaidPos.
 * 
 * @see ItemPaidPos
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ItemPaidPosDao extends GenericDAO<ItemPaidPos> {

	public ItemPaidPosDao() {
		super(ItemPaidPos.class);
	}
	
	/**
	 * Search for all item has had cash sale been paid
	 * 
	 * @param paid
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<CashSale> allCashSale(Paid paid) {
		String hql = " Select ipi.cashsale from ItemPaid as ipi where ipi.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return query.list();
	}
	

}
