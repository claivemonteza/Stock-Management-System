/**
 * 
 */
package mz.co.stock.access.profiles.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.stock.GenericDAO;
import mz.co.stock.access.profiles.model.Transaction;



/**
 * <code>TransactionDao</code> is the class that will make the search 
 * at the database at the table Transaction.
 * 
 * @see Transaction
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
@Repository
public class TransactionDao extends GenericDAO<Transaction> {

	public TransactionDao() {
		super(Transaction.class);
	}
	
	
	/**
	 * will search for a transaction that have the
	 * same code.
	 * 
	 * @param code is a object of string.
	 * @return a object of transaction.
	 */
	public Transaction findByCode(String code) {
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("code"), code));
			Query<Transaction> query = session.createQuery(criteria);
			transaction = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return transaction;
	}
	
	
	
	/**
	 * will search for a transaction that have the
	 * same name.
	 * 
	 * @param name is a object of string.
	 * @return a object of transaction.
	 */
	public Transaction findByName(String name) {
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name));
			Query<Transaction> query = session.createQuery(criteria);
			transaction = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return transaction;
	}
	
	
	
	/**
	 * will search for all transactions that contains the str
	 * on the code or name.
	 * 
	 * @param str is object of string
	 * @return a list of transactions
	 */
	public List<Transaction> find(String str) {
		List<Transaction> transactions = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("code"), "%" + str + "%"), builder.like(root.get("name"), "%" + str + "%")));
			Query<Transaction> query = session.createQuery(criteria);
			transactions = query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return transactions;
	}
	
	
	
	/**
	 * This method <code>find</code> will search for all users that contains the str
	 * on the code or name and have same status.
	 * 
	 * @param str is object of string
	 * @param active is the attribute of the user that show the status.
	 * @return a list of users
	 */
	public List<Transaction> find(String str, boolean active) {
		List<Transaction> transactions = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
			Root<Transaction> root = criteria.from(Transaction.class);
			criteria.select(root);
			Predicate rootRestriction = builder.or(builder.like(root.get("code"), "%" + str + "%"), builder.like(root.get("name"), "%" + str + "%"));
			criteria.where(builder.and(rootRestriction, builder.equal(root.get("active"), active)));
			Query<Transaction> query = session.createQuery(criteria);
			transactions = query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return transactions;
	}
	
	
	/**
	 * Will search for all permissions are active.
	 * 
	 * @param active
	 * @return
	 */
	public List<Transaction> allPermissions(boolean active){
		List<Transaction> permissoes = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Transaction as t where t.active=:active";
			Query<Transaction> query = session.createQuery(hql, Transaction.class);
			query.setParameter("active", active);
			permissoes = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return permissoes;
	}

}
