package sms.access.user.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.access.profile.model.Profile;
import sms.access.user.model.User;


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
 * @since 1.6
 */
@Repository
public class UserDao extends GenericDAO<User>{

	public UserDao() {
		super(User.class);
	}
	
	
	/**
	 * Will search at the database if exist user with same name.
	 * 
	 * @param name
	 * @return
	 */
	public User findByName(String name) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("name", name));
		return (User) criteria.uniqueResult();
	}
	
	
	/**
	 * Will search at the database if exist user with same username.
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}
	
	
	/**
	 * This method will search for all user that have the 
	 * same <i>str</i> at the name or username.
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> find(String str) {
		Criteria criteria =  getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("name",  "%"+str+"%"));
		List<User> utilizadores = (List<User>) criteria.list();
		if(utilizadores.isEmpty()) {
			criteria =  getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("username",  "%"+str+"%"));
			utilizadores = (List<User>) criteria.list();
		}
		return utilizadores;
	}
	
	
	
	/**
	 * This method will search for all active user that have the 
	 * same <i>str</i> at the name or username.
	 * 
	 * @param str
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> find(String str, boolean active) {
		Criteria criteria =  getSession().createCriteria(User.class);
		criteria.add(Restrictions.like("name",  "%"+str+"%"));
		criteria.add(Restrictions.eq("active", active));
		List<User> utilizadores = (List<User>) criteria.list();
		if(utilizadores.isEmpty()) {
			criteria =  getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("username",  "%"+str+"%"));
			criteria.add(Restrictions.eq("active", active));
			utilizadores = (List<User>) criteria.list();
		}
		return utilizadores;
	}
	
	
	/**
	 * Will search for all user are active.
	 * 
	 * @param active
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<User> allUsers(boolean active) {
		String hql = "from User as user where user.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}
	
	
	/**
	 * This method will search for all user that have the 
	 * same profile.
	 * 
	 * @param profile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> allUsers(Profile profile) {
		String hql = "from User as user join fetch user.profile as profile where profile=:profile";
		Query query = getSession().createQuery(hql);
		query.setParameter("profile", profile);
		return query.list();
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
	 * @return
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
