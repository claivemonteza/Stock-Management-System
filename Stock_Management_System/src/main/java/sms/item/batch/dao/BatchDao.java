package sms.item.batch.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sms.GenericDAO;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;


//TODO Create documentation

public class BatchDao extends GenericDAO<Batch> {

	public BatchDao() {
		super(Batch.class);
	}

	@SuppressWarnings("unchecked")
	public List<Batch> getAllActives(boolean active) {
		String hql = "from Batch as batch where batch.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}

	public Batch findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Batch.class);
		criteria.add(Restrictions.eq("code", code));
		return (Batch) criteria.uniqueResult();
	}

	public Batch findByCode(String code, boolean active) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Batch.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("active", active));
		return (Batch) criteria.uniqueResult();
	}

	/**
	 * will search for all batches with have on reference str.
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Batch> find(String str) {
		Criteria criteria = getSession().createCriteria(Batch.class);
		criteria.add(Restrictions.like("code", "%" + str + "%"));
		return criteria.list();
	}

	/**
	 * will search for all batches with have on reference str and are active.
	 * 
	 * @param str
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Batch> find(String str, boolean active) {
		Criteria criteria = getSession().createCriteria(Batch.class);
		criteria.add(Restrictions.like("code", "%" + str + "%"));
		criteria.add(Restrictions.eq("active", active));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Batch> findBatchesExpiring(Date validade) {
		String hql = "from Batch as b where b.expirationDate<=:validade and b.active = true order by b.expirationDate";
		Query query = getSession().createQuery(hql);
		query.setParameter("validade", validade);
		return query.list();
	}

	public Integer calculateAmountOfBatchActive(Product product, boolean active) {
		try {
			String hql = "Select SUM(b.amount) from Batch as b where b.product=:product and b.active=:active";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			query.setParameter("active", active);
			return Integer.parseInt(String.valueOf(((Long) query.uniqueResult()).longValue()));
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0;
		}
	}
	
	public Integer calculateAmount(Product product) {
		try {
			String hql = "Select SUM(b.amount) from Batch as b where b.product=:product";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			return Integer.parseInt(String.valueOf(((Long) query.uniqueResult()).longValue()));
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0;
		}
	}
	
	
	public double calculateCost(Product product) {
		try {
			String hql = "Select SUM(b.costPrice) from Batch as b where b.product=:product";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			return (double) query.uniqueResult();
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0.00;
		}
	}
	
	public double calculateCostOfBatchActive(Product product, boolean active) {
		try {
			String hql = "Select SUM(b.costPrice) from Batch as b where b.product=:product and b.active=:active";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			query.setParameter("active", active);
			return (double) query.uniqueResult();
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0;
		}
	}
	
	public double calculateSale(Product product) {
		try {
			String hql = "Select SUM(b.salePrice) from Batch as b where b.product=:product";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			return (double) query.uniqueResult();
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0.00;
		}
	}
	
	
	public double calculateSaleOfBatchActive(Product product, boolean active) {
		try {
			String hql = "Select SUM(b.salePrice) from Batch as b where b.product=:product and b.active=:active";
			Query query = getSession().createQuery(hql);
			query.setParameter("product", product);
			query.setParameter("active", active);
			return (double) query.uniqueResult();
		} catch (IllegalArgumentException |NullPointerException e) {
			return 0;
		}
	}
	

}
