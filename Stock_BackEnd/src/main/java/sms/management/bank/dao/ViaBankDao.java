/**
 * 
 */
package sms.management.bank.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.management.bank.model.ViaBank;

/**
 * <code>UserDao</code> is the class that will make the search at the database
 * at the table via bank.
 * 
 * @see ViaBank
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ViaBankDao extends GenericDAO<ViaBank> {

	public ViaBankDao() {
		super(ViaBank.class);
	}

	/**
	 * List all bank that have.
	 * 
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<String> allBanks(){
		String hql = "Select viabank.bank from ViaBank as viabank";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * List all the type that have.
	 * 
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<String> allTypes(){
		String hql = "Select viabank.type from ViaBank as viabank";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}
