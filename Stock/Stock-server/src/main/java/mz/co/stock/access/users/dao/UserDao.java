package mz.co.stock.access.users.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Repository;

import mz.co.stock.GenericDAO;
import mz.co.stock.access.profiles.model.Profile;
import mz.co.stock.access.users.model.User;




/**
 * <code>UserDao</code> is the class that will make the search at the database
 * at the table user.
 * 
 * @see User
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
@Repository
public class UserDao extends GenericDAO<User>{

	public UserDao() {
		super(User.class);
	}
	
	
	/**
	 * will search for a user that have the same
	 * name.
	 * 
	 * @param name is a object of string and the attribute of the user.
	 * @return a object of user.
	 */
	public User findByName(String name) {
		User user = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name));
			Query<User> query = session.createQuery(criteria);
			user = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	/**
	 * will search for a user that have the
	 * same username.
	 * 
	 * @param username is a object of string and the attribute of the user.
	 * @return a object of user.
	 */
	public User findByUsername(String username) {
		User user = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("username"), username));
			Query<User> query = session.createQuery(criteria);
			user = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	/**
	 * This method <code>find</code> will search for all users that contains the str
	 * on the name or login.
	 * 
	 * @param str str is object of string and any name of user
	 * @return a list of users
	 */
	public List<User> find(String str) {
		Set<User> users = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("name"), "%" + str + "%"), builder.like(root.get("username"), "%" + str + "%")));
			Query<User> query = session.createQuery(criteria);
			users = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(users);
	}
	
	
	
	/**
	 * This method <code>find</code> will search for all users that contains the str
	 * on the name or username and have same status.
	 * 
	 * @param str    is object of string and attribute of user
	 * @param active is the attribute of the user that show the status.
	 * @return a list of users
	 */
	public List<User> find(String str, boolean active) {
		Set<User> users = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.select(root);
			Predicate rootRestriction = builder.or(builder.like(root.get("name"), "%" + str + "%"), builder.like(root.get("username"), "%" + str + "%"));
			criteria.where(builder.and(rootRestriction, builder.equal(root.get("active"), active)));
			Query<User> query = session.createQuery(criteria);
			users = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(users);
	}
	
	
	/**
	 * This method <code>allUsers</code> will search for all users that have the
	 * same status.
	 * 
	 * @param active is the attribute of the user that show the status.
	 * @return a list of users
	 */
	public List<User> allUsers(boolean active) {
		Set<User> users = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from User as u where u.active=:active";
			Query<User> query = session.createQuery(hql, User.class);
			query.setParameter("active", active);
			users = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(users);
	}
	
	
	/**
	 * This method <code>allUsers</code> will search for all users that have the
	 * same profile.
	 * 
	 * @param profile is a object that have the roles of the user.
	 * @return a list of users
	 */
	public List<User> allUsers(Profile profile) {
		Set<User> users = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from User as u join fetch u.profile as p where p=:profile";
			Query<User> query = session.createQuery(hql, User.class);
			query.setParameter("profile", profile);
			users = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(users);
	}
	
	/**
	 * Search for a user have the same password 
	 * 
	 * @return
	 * @param password
	 */
	public User searchPassword(String password) {
		for (User user : getAll()) {
			if(passwordMatches(password, user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * check if there password and username exists and if the user have the that
	 * username and password.
	 * 
	 * @return
	 * @param username
	 * @param password
	 */
	public User authentication(String username, String password) {
		User util = findByUsername(username);
		if (util != null) {
			boolean check = passwordMatches(password, util.getPassword());
			if (check) {
				return util;
			}
		}
		return null;
	}

	
	/**
	 * Encrypt user password
	 * 
	 * @param user
	 */
	public void encryptUserPassword(User user) {
		String encryptedPassword = encrypt(user.getPassword());
		user.setPassword(encryptedPassword);
	}

	/**
	 * Encrypt the password
	 * 
	 * @param password
	 * @return
	 */
	public String encrypt(String password) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		String encryptedPassword = encryptor.encryptPassword(password);

		return encryptedPassword;
	}

	/**
	 * Check the encryption of the password
	 * 
	 * @return
	 * @param plainPassword
	 * @param encryptedPassword
	 */
	public boolean passwordMatches(String plainPassword, String encryptedPassword) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		return encryptor.checkPassword(plainPassword, encryptedPassword);
	}
}
