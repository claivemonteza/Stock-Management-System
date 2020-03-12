/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.payment.model.ItemPaidBill;
import sms.invoicing.payment.model.Paid;

/**
 * <code>ItemPaidBillDao</code> is the class that will make the search at the database
 * at the table ItemPaidBill.
 * 
 * @see ItemPaidBill
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ItemPaidBillDao extends GenericDAO<ItemPaidBill> {

	public ItemPaidBillDao() {
		super(ItemPaidBill.class);
	}
	
	/**
	 * Serach for all items that the invoice had been paid.
	 * 
	 * @param paid
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Invoice> allInvoices(Paid paid) {
		String hql = " Select ipb.invoice from ItemPaidBill as ipb where ipb.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return  query.list();
	}
	
	
}
