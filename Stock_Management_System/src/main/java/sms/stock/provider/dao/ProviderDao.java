package sms.stock.provider.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.stock.provider.model.Provider;
import sms.stock.request.model.Request;


/**
 * <code>FornecedorDao</code> is the class that will make the search at the database
 * at the table fornecedor.
 * 
 * @see Provider
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ProviderDao extends GenericDAO<Provider> {

	public ProviderDao() {
		super(Provider.class);
	}

	/**
	 * Will search at the database if exist provider with same name.
	 * 
	 * @param name
	 * @return
	 */
	public Provider findByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Provider.class);
		criteria.add(Restrictions.eq("name", name));
		return (Provider) criteria.uniqueResult();
	}
	
	
	/**
	 * Will search at the database if exist provider with same name and active.
	 * 
	 * @param name
	 * @param active
	 * @return
	 */
	public Provider findByName(String name, boolean active) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Provider.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("active", active));
		return (Provider) criteria.uniqueResult();
	}
	
	
	/**
	 * Will search at the database if exist provider with same nuit.
	 * 
	 * @param nuit
	 * @return
	 */
	public Provider findByNuit(String nuit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Provider.class);
		criteria.add(Restrictions.eq("nuit", nuit));
		return (Provider) criteria.uniqueResult();
	}


	/**
	 * Will search for all provider are active.
	 * 
	 * @param active
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Provider> allProviders(boolean active) {
		String hql = "from Provider as p where p.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}
	
	
	/**
	 * This method will search for all request that have the 
	 * same provider.
	 * 
	 * @param provider
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Request> allRequisitions(Provider provider){
		String hql = "from Request as r where r.provider=:provider";
		Query query = getSession().createQuery(hql);
		query.setParameter("provider", provider);
		return query.list();
	}
	
	
	/**
	 * will search for all providers with have on name str.
	 * 
	 *@param str
	 *@return
	 * */
	@SuppressWarnings("unchecked")
	public List<Provider> find(String str) {
		Criteria criteria = getSession().createCriteria(Provider.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		List<Provider> providers = criteria.list();
		if(providers.isEmpty()) {
			criteria = getSession().createCriteria(Provider.class);
			criteria.add(Restrictions.like("nuit", "%" + str + "%"));
			providers = criteria.list();
		}
		return providers;
	}
	
	
	/**
	 * will search for all providers with have on name str and are active.
	 * 
	 *@param str
	 *@param active
	 *@return
	 * */
	@SuppressWarnings("unchecked")
	public List<Provider> find(String str,boolean active) {
		Criteria criteria = getSession().createCriteria(Provider.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		criteria.add(Restrictions.eq("active", active));
		List<Provider> providers = criteria.list();
		if(providers.isEmpty()) {
			criteria = getSession().createCriteria(Provider.class);
			criteria.add(Restrictions.like("nuit", "%" + str + "%"));
			criteria.add(Restrictions.eq("active", active));
			providers = criteria.list();
		}
		return providers;
	}
}
