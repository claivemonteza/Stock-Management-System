package sms.stock.request.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.stock.request.model.Request;

/**
 * <code>RequestDao</code> is the class that will make the search at the database
 * at the table request.
 * 
 * @see Request
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class RequestDao extends GenericDAO<Request> {

	public RequestDao() {
		super(Request.class);
	}
	
	/**
	 * Search for a request that have the code.
	 * 
	 * @param code
	 * @return
	 * */
	public Request findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Request.class);
		criteria.add(Restrictions.eq("code", code));
		return (Request) criteria.uniqueResult();
	}
	
	/**
	 * Search for all requests that have at the date.
	 * 
	 * @param date
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<Request> getAllRequestByDate(Date date) {
		String hql = "from Request as r where r.date=:date";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		return query.list();
	}

}
