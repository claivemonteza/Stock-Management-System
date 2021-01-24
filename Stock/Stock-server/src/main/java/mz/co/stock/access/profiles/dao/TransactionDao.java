/**
 * 
 */
package mz.co.stock.access.profiles.dao;

import java.util.List;

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
