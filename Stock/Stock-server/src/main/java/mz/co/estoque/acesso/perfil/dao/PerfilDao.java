package mz.co.estoque.acesso.perfil.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.estoque.GenericDao;
import mz.co.estoque.acesso.perfil.model.Perfil;

/**
 * the class that will make all operations
 * at the database at the table profile.
 * 
 * @see Profile
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.7
 */
@Repository
public class PerfilDao extends GenericDao<Perfil>{

	public PerfilDao() {
		super(Perfil.class);
	}
	
	
	/**
	 * will search for a specific profile.
	 * @param nome is object of string and the attribute of the profile.
	 * @return a object of the profile.
	 */
	public Perfil pesquisarNome(String nome) {
		Perfil perfil = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
			Root<Perfil> root = criteria.from(Perfil.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("nome"), nome));
			Query<Perfil> query = session.createQuery(criteria);
			perfil = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return perfil;
	}
	
	
	/**
	 * will search for specific profile what where is on the list of profile that are active or not.
	 * 
	 * @param nome is object of string and the attribute of the profile.
	 * @param activo is object of boolean and the attribute of the profile.
	 * @return a object of profile
	 */
	public Perfil pesquisarNome(String nome, boolean activo) {
		Perfil perfil = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
			Root<Perfil> root = criteria.from(Perfil.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("name"), nome),builder.equal(root.get("active"), activo));
			Query<Perfil> query = session.createQuery(criteria);
			perfil = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return perfil;
	}


	/**
	 * will search for all profile that contains str on the <i>name</i>.
	 * @param str is object of string and any name of profile.
	 * @return list of profiles
	 */
	public List<Perfil> pesquisar(String str) {
		List<Perfil> perfis = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
			Root<Perfil> root = criteria.from(Perfil.class);
			criteria.select(root);
			criteria.where(builder.like(root.get("nome"), "%" + str + "%"));
			Query<Perfil> query = session.createQuery(criteria);
			perfis = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return perfis;
	}
	
	
	/**
	 * This method <code>find</code> will search for all profile that as in the same
	 * status and that contains str on the name.
	 * 
	 * @param str is object of string and the any name of a profile.
	 * @param activo is object of boolean and the attribute of the profile.
	 * @return a list of profiles
	 */
	public List<Perfil> pesquisar(String str, boolean activo) {
		List<Perfil> perfis = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
			Root<Perfil> root = criteria.from(Perfil.class);
			criteria.select(root);
			criteria.where(
					builder.like(root.get("nome"), "%" + str + "%"),
					builder.equal(root.get("activo"), activo)
					);
			Query<Perfil> query = session.createQuery(criteria);
			perfis = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return perfis;
	}
	
	
	/**
	 * This method <code>todosPerfis</code> will search for all perfis that as in the same status.
	 * 
	 * @param activo is object of boolean and the attribute of the perfil.
	 * @return a list of perfis
	 */
	public List<Perfil> todosPerfis(boolean activo) {
		List<Perfil> perfis = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Perfil as p where p.activo=:activo";
			Query<Perfil> query = session.createQuery(hql, Perfil.class);
			query.setParameter("activo", activo);
			perfis = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return perfis;
	}
}
