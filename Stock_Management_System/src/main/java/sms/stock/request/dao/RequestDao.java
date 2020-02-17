package sms.stock.request.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sms.GenericDAO;
import sms.stock.request.model.Request;


//TODO Create documentation

public class RequestDao extends GenericDAO<Request> {

	public RequestDao() {
		super(Request.class);
	}
	
	public Request findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Request.class);
		criteria.add(Restrictions.eq("code", code));
		return (Request) criteria.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Request> getAllRequestByDate(Date date) {
		String hql = "from Request as r where r.date=:date";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", date);
		return query.list();
	}

}
