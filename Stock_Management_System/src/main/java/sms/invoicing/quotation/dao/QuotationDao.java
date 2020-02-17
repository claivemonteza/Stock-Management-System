package sms.invoicing.quotation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.quotation.model.Quotation;

/**
 * In this class we create all the query to
 * create, update, delete and search the
 * 
 * @see Quotation
 * @see GenericDAO
 * 
 * @author Claive Monteza
 *
 */
@Repository
public class QuotationDao extends GenericDAO<Quotation> {

	public QuotationDao() {
		super(Quotation.class);
	}

	@SuppressWarnings("unchecked")
	public List<SaleItem> verificarConfomidadeDeItems(Quotation quotation) {
		String hql = "from Item as item join fetch item.quotation as q left join item.product as p where q=:quotation and item.amount>product.amount";
		Query query = getSession().createQuery(hql);
		query.setParameter("quotation", quotation);
		return query.list();
	}
	
	public Quotation findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Quotation.class);
		criteria.add(Restrictions.eq("code", code));
		return (Quotation) criteria.uniqueResult();
	}
}
