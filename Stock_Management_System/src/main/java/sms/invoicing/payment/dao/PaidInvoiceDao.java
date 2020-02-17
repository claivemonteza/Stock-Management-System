/**
 * 
 */
package sms.invoicing.payment.dao;

import java.util.List;

import org.hibernate.Query;

import sms.GenericDAO;
import sms.invoicing.payment.model.Paid;
import sms.invoicing.payment.model.PaidInvoice;
import sms.management.bank.model.ViaBank;

/**
 * @author Claive Monteza
 *
 */
public class PaidInvoiceDao extends GenericDAO<PaidInvoice> {

	public PaidInvoiceDao() {
		super(PaidInvoice.class);
	}

	@SuppressWarnings("unchecked")
	public List<ViaBank> allViaBank(Paid paid) {
		String hql = " Select pi.viabank from PaidInvoice as pi join fetch pi.paid=:paid";
		Query query = getSession().createQuery(hql);
		query.setParameter("paid", paid);
		return  query.list();
	}
}
