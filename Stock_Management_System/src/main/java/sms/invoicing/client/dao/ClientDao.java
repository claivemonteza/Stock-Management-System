package sms.invoicing.client.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.client.model.Client;

/**
 * <code>ClientDao</code> is the class that will make the search at the database
 * at the table client.
 * 
 * @see Client
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */

@Repository
public class ClientDao extends GenericDAO<Client> {

	public ClientDao() {
		super(Client.class);
	}
	
	
	/**
	 * Will search at the database if exist client with same name.
	 * 
	 * @param name
	 * @return
	 */
	public Client findByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Client.class);
		criteria.add(Restrictions.eq("name", name));
		return (Client) criteria.uniqueResult();
	}
	
	
	/**
	 * Will search at the database if exist client with same nuit.
	 * 
	 * @param nuit
	 * @return
	 */
	public Client findByNuit(String nuit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Client.class);
		criteria.add(Restrictions.eq("nuit", nuit));
		return (Client) criteria.uniqueResult();
	}
	
	
	/**
	 * Will search for all client are active.
	 * 
	 * @param active
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Client> allClients(boolean active) {
		String hql = "from Client as client where client.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}
	
	/**
	 * will search for all clients with have on name str.
	 * 
	 *@param str
	 *@return
	 * */
	@SuppressWarnings("unchecked")
	public List<Client> find(String str) {
		Criteria criteria = getSession().createCriteria(Client.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		List<Client> clients = criteria.list();
		if(clients.isEmpty()) {
			criteria = getSession().createCriteria(Client.class);
			criteria.add(Restrictions.like("nuit", "%" + str + "%"));
			clients = criteria.list();
		}
		return clients;
	}
	
	
	/**
	 * will search for all clients with have on name str and are active.
	 * 
	 *@param str
	 *@param active
	 *@return
	 * */
	@SuppressWarnings("unchecked")
	public List<Client> find(String str,boolean active) {
		Criteria criteria = getSession().createCriteria(Client.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		criteria.add(Restrictions.eq("active", active));
		List<Client> clients = criteria.list();
		if(clients.isEmpty()) {
			criteria = getSession().createCriteria(Client.class);
			criteria.add(Restrictions.like("nuit", "%" + str + "%"));
			criteria.add(Restrictions.eq("active", active));
			clients = criteria.list();
		}
		return clients;
	}
	
	/**
	 * This method will search for all unpaid invoices that the client have.
	 * 
	 * @param client
	 * @return
	
	@SuppressWarnings("unchecked")
	public List<Invoice> unpaidInvoices(Client client) {		
		String hql = "FROM Invoice as invoice join fetch invoice.client as client WHERE invoice.madePayment=false and  client=:client";
		Query query = getSession().createQuery(hql);
		query.setParameter("client", client);
		return query.list();
	}
	 */
}
