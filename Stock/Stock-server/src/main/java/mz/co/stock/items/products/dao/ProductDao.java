package mz.co.stock.items.products.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.stock.GenericDAO;
import mz.co.stock.items.products.model.Product;

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
 * @since 1.8
 */
@Repository
public class ProductDao extends GenericDAO<Product> {

	public ProductDao() {
		super(Product.class);
	}

	/**
	 * List all categories that have.
	 * 
	 * @return
	 */
	public Set<String> allCategories() {
		Set<String> categories = new HashSet<String>();
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Product as p order by p.category ASC";
			Query<Product> query = session.createQuery(hql, Product.class);
			for (Product product : query.list()) {
				categories.add(product.getCategory());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return categories;
	}

	/**
	 * Will search at the database if exist product with same name.
	 * 
	 * @param name
	 * @return
	 */
	public Product findByName(String name) {
		Product product = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name));
			Query<Product> query = session.createQuery(criteria);
			product = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * This method will search for all active products that have the same name.
	 * 
	 * @param name
	 * @param active
	 * @return object
	 */
	public Product findByName(String name, boolean active) {
		Product product = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name), builder.equal(root.get("active"), active));
			Query<Product> query = session.createQuery(criteria);
			product = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * Will search at the database if exist product with same code.
	 * 
	 * @param code
	 * @return
	 */
	public Product findByCode(String code) {
		Product product = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("code"), code));
			Query<Product> query = session.createQuery(criteria);
			product = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * This method will search for all active products that have the same code.
	 * 
	 * @param code
	 * @param active
	 * @return
	 */
	public Product findByCode(String code, boolean active) {
		Product product = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("code"), code), builder.equal(root.get("active"), active));
			Query<Product> query = session.createQuery(criteria);
			product = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * Will search for all products are active.
	 * 
	 * @param active
	 * @return list objects
	 */
	public List<Product> allProducts(boolean active) {
		List<Product> products = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Product as product where product.active=:active";
			Query<Product> query = session.createQuery(hql, Product.class);
			query.setParameter("active", active);
			products = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * will search for all products with have on name str.
	 * 
	 * @param str
	 * @return list of objects
	 */
	public List<Product> find(String str) {
		List<Product> products = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("code"), "%" + str + "%"),
					builder.like(root.get("name"), "%" + str + "%")));
			Query<Product> query = session.createQuery(criteria);
			products = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * will search for all products with have on name str and are active.
	 * 
	 * @param str
	 * @param active
	 * @return list of objects
	 */
	public List<Product> find(String str, boolean active) {
		List<Product> products = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			Root<Product> root = criteria.from(Product.class);
			criteria.select(root);
			Predicate rootRestriction = builder.or(builder.like(root.get("code"), "%" + str + "%"),
					builder.like(root.get("name"), "%" + str + "%"));
			criteria.where(builder.and(rootRestriction, builder.equal(root.get("active"), active)));
			Query<Product> query = session.createQuery(criteria);
			products = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return products;
	}
}
