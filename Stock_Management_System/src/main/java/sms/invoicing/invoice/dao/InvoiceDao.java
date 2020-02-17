package sms.invoicing.invoice.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.model.Invoice;

//TODO Create documentation

@Repository
public class InvoiceDao extends GenericDAO<Invoice> {

	public InvoiceDao() {
		super(Invoice.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> invoiceForNotificarion() {
		String hql = "from Invoice as invoice join fetch invoice.client as client WHERE invoice.paid = false and invoice.dueDate < CURRENT_DATE";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	
	/**
	 * This method will search for all unpaid invoices.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Invoice> unpaidInvoices() {
		String hql = "from Invoice as invoice join fetch invoice.client as client WHERE invoice.paid=0";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	
	
	/**
	 * This method will search for all invoices that the client have.
	 * 
	 * @param client
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Invoice> allInvoices(Client client) {
		String hql = "from Invoice as invoice WHERE invoice.client=:client";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
	
	
	/**
	 * This method will search for all unpaid invoices that the client have.
	 * 
	 * @param client
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Invoice> unpaidInvoices(Client client) {
		String hql = "from Invoice as invoice where invoice.paid=0 and invoice.client=:client";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
	
	
	/**
	 * This method will search for all unpaid invoices that the client have.
	 * 
	 * @param client
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Invoice> allInvoicesUnpaid(Client client) {
		String hql = "from Invoice as invoice where invoice.madePayment=false and invoice.client=:client";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Invoice> listaAntiguidadeSaldo(Date inicio, Date dataFim, int nrRegisto) {
		String hql = "from Invoice as invoice join fetch invoice.client as client where invoice.paga=0 and (invoice.date between :inicio and :dataFim)";
		Query query = getSession().createQuery(hql);
		query.setParameter("inicio", inicio);
		query.setParameter("dataFim", dataFim);
		query.setMaxResults(nrRegisto);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> mesesFacturaAno(int year) {
		String hql = "select distinct month(invoice.date) from Invoice as invoice where invoice.year=:year and invoice.paga=0";
		Query query = getSession().createQuery(hql);
		query.setParameter("year", year);
		return query.list();
	}

	public double valorAReceber(Date dataInicio, Date dataFim) {
		String hql = "select sum(invoice.subTotal+invoice.totalIva-invoice.totalIva) from Invoice as invoice where invoice.paga=false and(invoice.date between :dataInicio and :dataFim)";
		Query query = getSession().createQuery(hql);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		
		if (((Double) query.uniqueResult()).doubleValue() == 0) {
			return 0.00;
		} else {
			return ((Double) query.uniqueResult()).doubleValue();
		}

	}

	public double valorAReceber(int month, int year) {
		String hql = "select sum(invoice.total-invoice.valorPago) from invoice as invoice where invoice.year=:year and month(invoice.date)=:month and invoice.paga=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("month", month);
		query.setParameter("year", year);
		return ((Double) query.uniqueResult()).doubleValue();
	}

}
