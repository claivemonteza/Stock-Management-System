/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;

import sms.GenericDAO;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.payment.model.ItemPaidBill;
import sms.invoicing.payment.model.Paid;

/**
 * @author Claive Monteza
 *
 */
public class ItemPaidBillDao extends GenericDAO<ItemPaidBill> {

	public ItemPaidBillDao() {
		super(ItemPaidBill.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> allInvoices(Paid paid) {
		String hql = " Select ipb.invoice from ItemPaidBill as ipb where ipb.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return  query.list();
	}
	
	
}
