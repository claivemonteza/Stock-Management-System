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
 * In this class we create all the query to
 * create, update, delete and search the
 * 
 * @see Receipt
 * @see GenericDAO
 * 
 * @author Claive Monteza
 *
 */
@Repository
public class ReceiptDao extends GenericDAO<Receipt> {

	public ReceiptDao() {
		super(Receipt.class);
	}

	public Receipt findByReference(String reference) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Receipt.class);
		criteria.add(Restrictions.eq("referencia", reference));
		return (Receipt) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Receipt> allReceipts(Client client) {
		String hql = "FROM Receipt as receipt join fetch receipt.sale as s where s.client=:client and s.type not in ('Quotation')";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
}
