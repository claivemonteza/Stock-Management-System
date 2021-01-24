package mz.co.stock.access.profiles.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.stock.GenericDAO;
import mz.co.stock.access.profiles.model.Profile;


/**
 * <code>ProfileDao</code> is the class that will make all operations
 * at the database at the table profile.
 * 
 * @see Profile
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
@Repository
public class ProfileDao extends GenericDAO<Profile>{

	public ProfileDao() {
		super(Profile.class);
	}
	
	
	/**
	 * will search for a specific profile.
	 * @param name is object of string and the attribute of the profile.
	 * @return a object of the profile.
	 */
	public Profile findByName(String name) {
		Profile profile = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
			Root<Profile> root = criteria.from(Profile.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name));
			Query<Profile> query = session.createQuery(criteria);
			profile = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return profile;
	}
	
	
	/**
	 * will search for specific profile what where is on the list of profile that are active or not.
	 * 
	 * @param name is object of string and the attribute of the profile.
	 * @param active is object of boolean and the attribute of the profile.
	 * @return a object of profile
	 */
	public Profile findByName(String name, boolean active) {
		Profile profile = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
			Root<Profile> root = criteria.from(Profile.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), name),builder.equal(root.get("active"), active));
			Query<Profile> query = session.createQuery(criteria);
			profile = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return profile;
	}

	
	/**
	 * This method <code>allProfiles</code> will search for all profiles that as in the same status.
	 * 
	 * @param active is object of boolean and the attribute of the profile.
	 * @return a list of profiles
	 */
	public List<Profile> allProfiles(boolean active) {
		List<Profile> profiles = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Profile as p where p.active=:active";
			Query<Profile> query = session.createQuery(hql, Profile.class);
			query.setParameter("active", active);
			profiles = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return profiles;
	}	
	

	/**
	 * will search for all profile that contains str on the <i>name</i>.
	 * @param str is object of string and any name of profile.
	 * @return list of profiles
	 */
	public List<Profile> find(String str) {
		List<Profile> profiles = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
			Root<Profile> root = criteria.from(Profile.class);
			criteria.select(root);
			criteria.where(builder.like(root.get("name"), "%" + str + "%"));
			Query<Profile> query = session.createQuery(criteria);
			profiles = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return profiles;
	}
	
	
	/**
	 * This method <code>find</code> will search for all profile that as in the same
	 * status and that contains str on the name.
	 * 
	 * @param str is object of string and the any name of a profile.
	 * @param active is object of boolean and the attribute of the profile.
	 * @return a list of profiles
	 */
	public List<Profile> find(String str, boolean active) {
		List<Profile> profiles = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Profile> criteria = builder.createQuery(Profile.class);
			Root<Profile> root = criteria.from(Profile.class);
			criteria.select(root);
			criteria.where(
					builder.like(root.get("name"), "%" + str + "%"),
					builder.equal(root.get("active"), active)
					);
			Query<Profile> query = session.createQuery(criteria);
			profiles = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return profiles;
	}
}
