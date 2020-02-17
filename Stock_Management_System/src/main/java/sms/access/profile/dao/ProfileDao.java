package sms.access.profile.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.access.profile.model.Profile;

/**
 * <code>ProfileDao</code> is the class that will make the search 
 * at the database at the table profile.
 * 
 * @see Profile
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ProfileDao extends GenericDAO<Profile>{

	public ProfileDao() {
		super(Profile.class);
	}
	
	
	/**
	 * Will search at the database if exist the profile with same name.
	 * 
	 * @param name
	 * @return
	 */
	public Profile findByName(String name) {
		Criteria criteria =  getSession().createCriteria(Profile.class);
		criteria.add(Restrictions.eq("name", name));
		return (Profile) criteria.uniqueResult();
	}
	
	
	/**
	 * This method will search for all profile that are active
	 * and will check if those have the same <i>name</i>.
	 * 
	 * @param name
	 * @param active
	 * @return
	 */
	public Profile findByName(String name, boolean active) {
		Criteria criteria =  getSession().createCriteria(Profile.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("active", active));
		return (Profile) criteria.uniqueResult();
	}

	
	/**
	 * Will search for all profile are active.
	 * 
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> allProfiles(boolean active) {
		String hql = "from Profile as profile where profile.active=:active";
		Query query = getSession().createQuery(hql);
		query.setParameter("active", active);
		return query.list();
	}	
	

	/**
	 * This method will search for all profile that have the 
	 * same <i>str</i>.
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> find(String str) {
		Criteria criteria =  getSession().createCriteria(Profile.class);
		criteria.add(Restrictions.like("name",  "%"+str+"%"));
		List<Profile> profiles = (List<Profile>) criteria.list();
		return profiles;
	}
	
	
	/**
	 * This method will search for all profile that are active
	 * and will check if those have the same <i>str</i>.
	 * 
	 * @param str
	 * @param active
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> find(String str, boolean active) {
		Criteria criteria =  getSession().createCriteria(Profile.class);
		criteria.add(Restrictions.like("name",  "%"+str+"%"));
		criteria.add(Restrictions.eq("active", active));
		List<Profile> profiles = (List<Profile>) criteria.list();
		return profiles;
	}
}
