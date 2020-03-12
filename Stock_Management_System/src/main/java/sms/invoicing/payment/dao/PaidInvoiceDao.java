/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.payment.model.Paid;
import sms.invoicing.payment.model.PaidInvoice;
import sms.management.bank.model.ViaBank;

/**
 * <code>PaidInvoiceDao</code> is the class that will make the search at the database
 * at the table PaidInvoice.
 * 
 * @see PaidInvoice
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class PaidInvoiceDao extends GenericDAO<PaidInvoice> {

	public PaidInvoiceDao() {
		super(PaidInvoice.class);
	}

	/**
	 * Search for all paid invoice that have paid.
	 * 
	 * @param paid
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<ViaBank> allViaBank(Paid paid) {
		String hql = " Select pi.viabank from PaidInvoice as pi join fetch pi.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return  query.list();
	}
}
