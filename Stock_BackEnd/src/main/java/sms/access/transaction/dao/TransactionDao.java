/**
 * 
 */
package sms.access.transaction.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.access.transaction.model.Transaction;

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
 * @since 1.6
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
	@SuppressWarnings("unchecked")
	public List<Transaction> allPermissions(boolean active){
		String hql = "from Transaction as transaction where transaction.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}

}
