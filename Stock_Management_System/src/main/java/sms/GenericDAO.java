package sms;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <code>GenericDAO</code> will be extends at all class
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public abstract class GenericDAO<T extends Identidade> extends HibernateDaoSupport {

	private Class<T> clazz;

	public GenericDAO(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public void create(T t) {
		getHibernateTemplate().save(t);
	}

	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	public void delete(T t) {
		getHibernateTemplate().delete(t);
	}

	public T findById(Long id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession().createCriteria(clazz).list();
	}
}
