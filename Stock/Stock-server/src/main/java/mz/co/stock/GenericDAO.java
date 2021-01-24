package mz.co.stock;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * <code>GenericDAO</code> will be extends at all class
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public abstract class GenericDAO<T>{

	private SessionFactory sessionFactory;
	private Class<T> clazz;

	public GenericDAO(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void create(T t) {
		try (Session session = getSessionFactory().openSession()) {
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void update(T t) {
		try (Session session = getSessionFactory().openSession()) {
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void delete(T t) {
		try (Session session = getSessionFactory().openSession()) {
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/*
	public T findById(Long id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}*/


	public List<T> getAll() {
		List<T> list = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(clazz);
			Root<T> root = criteria.from(clazz);
			criteria.select(root);
			Query<T> query = session.createQuery(criteria);
			 list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}
}
