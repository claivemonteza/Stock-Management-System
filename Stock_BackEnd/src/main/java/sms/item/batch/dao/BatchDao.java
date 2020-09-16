package sms.item.batch.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;

/**
 * <code>BatchDao</code> is the class that will make the search at the database
 * at the table batch.
 * 
 * @see Batch
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class BatchDao extends GenericDAO<Batch> {

	public BatchDao() {
		super(Batch.class);
	}

	
	/**
	 * Search for all batch that are active.
	 * 
	 * @param active
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Batch> getAllActives(boolean active) {
		String hql = "from Batch as batch where batch.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}

	/**
	 * Search for a batch have the code
	 * 
	 * @param code 
	 * @return
	 * */
	public Batch findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Batch.class);
		criteria.add(Restrictions.eq("code", code));
		return (Batch) criteria.uniqueResult();
	}

	
	/**
	 * Search for a batch that have the code
	 * and if is active or not.
	 * 
	 * @param code
	 * @param active
	 * @return
	 * */
	public Batch findByCode(String code, boolean active) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Batch.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("active", active));
		return (Batch) criteria.uniqueResult();
	}

	/**
	 * Search for all batches with have on reference str.
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
	 * Search for all batches with have on reference str and are active.
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

	/**
	 * Search all batches that have expiration date 
	 * 
	 * @param validade
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Batch> findBatchesExpiring(Date validade) {
		String hql = "from Batch as b where b.expirationDate<=:validade and b.active = true order by b.expirationDate";
		Query query = getSession().createQuery(hql);
		query.setParameter("validade", validade);
		return query.list();
	}

	
	/**
	 * SUM all the amount of batch that are active or not that a product have.
	 * 
	 * @param product
	 * @param active
	 * @return
	 * */
	public Integer calculateAmountOfBatchWhereActive(Product product, boolean active) {
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
	
	
	/**
	 * SUM all the amount of batch that a product have.
	 * 
	 * @param product
	 * @return
	 * */
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
	
	
	/**
	 * SUM all the cost prices of the batch that the product have.
	 * 
	 * @param product
	 * return
	 * */
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
	
	
	/**
	 * SUM all cost prices of the batch where active or not that the product have.
	 * 
	 * @param active
	 * @param product 
	 * @return
	 * */
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
	
	/**
	 * SUM all sale prices of the batch that product have.
	 * 
	 * @param product
	 * @return
	 * */
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
	
	/**
	 * SUM all sale prices of the batch that are active or not that product have.
	 * 
	 * @param active
	 * @param product
	 * @return
	 * */
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
