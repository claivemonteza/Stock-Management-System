package sms.invoicing.sale.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.client.model.Client;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.sale.model.Sale;


//TODO Create documentation
@Repository
public class SaleDao extends GenericDAO<Sale> {

	public SaleDao() {
		super(Sale.class);
	}

	public Sale findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Sale.class);
		criteria.add(Restrictions.eq("code", code));
		return (Sale) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Sale> allSales(Client client) {
		String hql = "from Sale as s join fetch s.client as c where c=:client and s.type not in ('quotation.record')";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}

	public Sale lastSale(String type) {
		String hql = "FROM Sale as s WHERE s.id=(SELECT max(id) FROM s WHERE s.type=:type)";
		Query query = getSession().createQuery(hql);
		query.setParameter("type", type);
		return (Sale) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<SaleItem> allItems(Sale sale) {
		String hql = "from Item as item join fetch item.sale as s where s=:sale";
		Query query = getSession().createQuery(hql);
		query.setParameter("sale", sale);
		return query.list();
	}

}
