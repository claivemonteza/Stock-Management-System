package sms.invoicing.receipt.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.client.model.Client;
import sms.invoicing.receipt.model.Receipt;

/**
 * <code>ReceiptDao</code> is the class that will make the search at the database
 * at the table receipt.
 * 
 * @see Receipt
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ReceiptDao extends GenericDAO<Receipt> {

	public ReceiptDao() {
		super(Receipt.class);
	}

	/**
	 * Search for the receipt that have the same reference/code.
	 * 
	 * @param reference
	 * @return
	 * */
	public Receipt findByReference(String reference) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Receipt.class);
		criteria.add(Restrictions.eq("referencia", reference));
		return (Receipt) criteria.uniqueResult();
	}

	/**
	 * Search for all the receipt that the client have.
	 * 
	 * @param client
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Receipt> allReceipts(Client client) {
		String hql = "FROM Receipt as receipt join fetch receipt.sale as s where s.client=:client and s.type not in ('Quotation')";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
}
