package mz.co.stock.items.batches.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.stock.GenericDAO;
import mz.co.stock.items.batches.model.Batch;
import mz.co.stock.items.products.model.Product;

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
 * @since 1.8
 */
@Repository
public class BatchDao extends GenericDAO<Batch> {

	public BatchDao() {
		super(Batch.class);
	}

	/**
	 * will search for a batch that have the same code.
	 * 
	 * @param code is a object of string
	 * @return a object.
	 */
	public Batch findByCode(String code) {
		Batch batch = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Batch> criteria = builder.createQuery(Batch.class);
			Root<Batch> root = criteria.from(Batch.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("code"), code));
			Query<Batch> query = session.createQuery(criteria);
			batch = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batch;
	}

	/**
	 * will search for batch that have the same code and status.
	 * 
	 * @param code   is object of string
	 * @param active is the attribute that show the status.
	 * @return a list of objects
	 */
	public Batch findByCode(String code, boolean active) {
		Batch batch = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Batch> criteria = builder.createQuery(Batch.class);
			Root<Batch> root = criteria.from(Batch.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("code"), code), builder.equal(root.get("active"), active));
			Query<Batch> query = session.createQuery(criteria);
			batch = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batch;
	}

	/**
	 * will search for all batches that contains the str on the code.
	 * 
	 * @param str is object of string
	 * @return a list of objects
	 */
	public List<Batch> find(String str) {
		List<Batch> batches = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Batch> criteria = builder.createQuery(Batch.class);
			Root<Batch> root = criteria.from(Batch.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("code"), "%" + str + "%")));
			Query<Batch> query = session.createQuery(criteria);
			batches = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batches;
	}

	/**
	 * will search for all batches that contains the str on the code and have same
	 * status.
	 * 
	 * @param str    is object of string
	 * @param active is the attribute that show the status.
	 * @return a list of objects
	 */
	public List<Batch> find(String str, boolean active) {
		List<Batch> batches = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Batch> criteria = builder.createQuery(Batch.class);
			Root<Batch> root = criteria.from(Batch.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("code"), "%" + str + "%"),
					builder.equal(root.get("active"), active)));
			Query<Batch> query = session.createQuery(criteria);
			batches = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batches;
	}

	/**
	 * Search all batches that have expiration date
	 * 
	 * @param validade is object of date
	 * @return list of objects
	 */
	public List<Batch> findBatchesExpiring(Date validade) {
		List<Batch> batches = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Batch as b where b.expirationDate<=:validade and b.active = true order by b.expirationDate";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("validade", validade);
			batches = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batches;
	}

	/**
	 * will search for all batches that have the same status.
	 * 
	 * @param active is the attribute that show the status.
	 * @return a list
	 */
	public List<Batch> getAllActives(boolean active) {
		List<Batch> batches = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Batch as batch where batch.active=:active";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("active", active);
			batches = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return batches;
	}

	/**
	 * SUM all the amount of batch that are active or not that a product have.
	 * 
	 * @param product
	 * @param active
	 * @return
	 */
	public Integer calculateAmountOfBatchWhereActive(Product product, boolean active) {
		int value = 0;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.amount) from Batch as b where b.product=:product and b.active=:active";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("product", product);
			query.setParameter("active", active);
			value = Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * SUM all the amount of batch that a product have.
	 * 
	 * @param product
	 * @return object
	 */
	public Integer calculateAmount(Product product) {
		int value = 0;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.amount) from Batch as b where b.product=:product";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("product", product);
			value = Integer.parseInt(String.valueOf(query.uniqueResult()));
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * SUM all the cost prices of the batch that the product have.
	 * 
	 * @param product return
	 */
	public double calculateCost(Product product) {
		double value = 0.00;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.costPrice) from Batch as b where b.product=:product";
			Query<Batch> query =session.createQuery(hql, Batch.class);
			query.setParameter("product", product);
			value = Double.parseDouble(String.valueOf(query.uniqueResult()));
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * SUM all cost prices of the batch where active or not that the product have.
	 * 
	 * @param active
	 * @param product
	 * @return
	 */
	public double calculateCostOfBatchActive(Product product, boolean active) {
		double value = 0.00;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.costPrice) from Batch as b where b.product=:product and b.active=:active";
			Query<Batch> query = session.createQuery(hql,Batch.class);
			query.setParameter("product", product);
			query.setParameter("active", active);
			value = Double.parseDouble(String.valueOf(query.uniqueResult()));
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * SUM all sale prices of the batch that product have.
	 * 
	 * @param product
	 * @return
	 */
	public double calculateSale(Product product) {
		double value = 0.00;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.salePrice) from Batch as b where b.product=:product";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("product", product);
			value = Double.parseDouble(String.valueOf(query.uniqueResult()));
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * SUM all sale prices of the batch that are active or not that product have.
	 * 
	 * @param active
	 * @param product
	 * @return
	 */
	public double calculateSaleOfBatchActive(Product product, boolean active) {
		double value = 0.00;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "Select SUM(b.salePrice) from Batch as b where b.product=:product and b.active=:active";
			Query<Batch> query = session.createQuery(hql, Batch.class);
			query.setParameter("product", product);
			query.setParameter("active", active);
			value = Double.parseDouble(String.valueOf(query.uniqueResult()));
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return value;
	}

}
