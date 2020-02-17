package sms.item.product.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;

/**
 * <code>ProductDao</code> is the class that will make the search at the
 * database at the table product.
 * 
 * @see Product
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ProductDao extends GenericDAO<Product> {

	public ProductDao() {
		super(Product.class);
	}

	
	@SuppressWarnings("unchecked")
	public Set<String> allCategories(){
		String hql = "Select p.category from Product as p order by p.category ASC";
		Query query = getSession().createQuery(hql);
		Set<String> categories = Set.copyOf(query.list());
		return categories;
	}
	
	/**
	 * Will search at the database if exist product with same name.
	 * 
	 * @param name
	 * @return
	 */
	public Product findByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("name", name));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * This method will search for all active products that have the same name.
	 * 
	 * @param name
	 * @param active
	 * @return
	 */
	public Product findByName(String name, boolean active) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("active", active));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * Will search at the database if exist product with same code.
	 * 
	 * @param code
	 * @return
	 */
	public Product findByCode(String code) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("code", code));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * This method will search for all active products that have the same code.
	 * 
	 * @param code
	 * @param active
	 * @return
	 */
	public Product findByCode(String code, boolean active) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("active", active));
		return (Product) criteria.uniqueResult();
	}

	/**
	 * Will search for all products are active.
	 * 
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Product> allProducts(boolean active) {
		String hql = "from Product as product where product.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}

	/**
	 * This method will search for all batches that have the same product.
	 * 
	 * @param product
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Batch> allBatches(Product product) {
		String hql = "from Batch as batch where batch.product=:product order by batch.date DESC";
		Query query = getSession().createQuery(hql);
		query.setParameter("product", product);
		return query.list();
	}
	
	/**
	 * This method will search for all batches that have the same product.
	 * 
	 * @param product
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Batch> allBatches(Product product, boolean active) {
		String hql = "from Batch as batch where batch.product=:product and batch.active=:active order by batch.date DESC";
		Query query = getSession().createQuery(hql);
		query.setParameter("product", product);
		query.setParameter("active", active);
		return query.list();
	}

	/**
	 * will search for all products with have on name str.
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Product> find(String str) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		List<Product> products = criteria.list();
		if (products.isEmpty()) {
			criteria = getSession().createCriteria(Product.class);
			criteria.add(Restrictions.like("code", "%" + str + "%"));
			products = criteria.list();
		}
		return products;
	}

	/**
	 * will search for all products with have on name str and are active.
	 * 
	 * @param str
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Product> find(String str, boolean active) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.like("name", "%" + str + "%"));
		criteria.add(Restrictions.eq("active", active));
		List<Product> products = criteria.list();
		if (products.isEmpty()) {
			criteria = getSession().createCriteria(Product.class);
			criteria.add(Restrictions.like("code", "%" + str + "%"));
			criteria.add(Restrictions.eq("active", active));
			products = criteria.list();
		}
		return products;
	}
}
