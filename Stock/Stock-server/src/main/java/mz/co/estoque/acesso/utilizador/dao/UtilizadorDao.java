package mz.co.estoque.acesso.utilizador.dao;

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

import mz.co.estoque.GenericDao;
import mz.co.estoque.acesso.perfil.model.Perfil;
import mz.co.estoque.acesso.utilizador.model.Utilizador;

/**
 * <code>UserDao</code> is the class that will make all operations at the
 * database at the table user.
 * 
 * @see Utilizador
 * @see GenericDao
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.7
 */
@Repository
public class UtilizadorDao extends GenericDao<Utilizador> {

	public UtilizadorDao() {
		super(Utilizador.class);
	}

	
	/**
	 * will search for a user that have the same
	 * name.
	 * 
	 * @param nome is a object of string and the attribute of the user.
	 * @return a object of user.
	 */
	public Utilizador pesquisarNome(String nome) {
		Utilizador utilizador = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Utilizador> criteria = builder.createQuery(Utilizador.class);
			Root<Utilizador> root = criteria.from(Utilizador.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("nome"), nome));
			Query<Utilizador> query = session.createQuery(criteria);
			utilizador = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return utilizador;
	}

	
	/**
	 * will search for a user that have the
	 * same login.
	 * 
	 * @param usuario is a object of string and the attribute of the user.
	 * @return a object of user.
	 */
	public Utilizador pesquisarUsuario(String usuario) {
		Utilizador utilizador = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Utilizador> criteria = builder.createQuery(Utilizador.class);
			Root<Utilizador> root = criteria.from(Utilizador.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("usuario"), usuario));
			Query<Utilizador> query = session.createQuery(criteria);
			utilizador = query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return utilizador;
	}

	/**
	 * This method <code>find</code> will search for all users that contains the str
	 * on the name or login.
	 * 
	 * @param str str is object of string and any name of user
	 * @return a list of users
	 */
	public List<Utilizador> pesquisar(String str) {
		Set<Utilizador> utilizadores = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Utilizador> criteria = builder.createQuery(Utilizador.class);
			Root<Utilizador> root = criteria.from(Utilizador.class);
			criteria.select(root);
			criteria.where(builder.or(builder.like(root.get("nome"), "%" + str + "%"), builder.like(root.get("usuario"), "%" + str + "%")));
			Query<Utilizador> query = session.createQuery(criteria);
			utilizadores = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(utilizadores);
	}

	/**
	 * This method <code>find</code> will search for all users that contains the str
	 * on the name or login and have same status.
	 * 
	 * @param str    is object of string and attribute of user
	 * @param activo is the attribute of the user that show the status.
	 * @return a list of users
	 */
	public List<Utilizador> pesquisar(String str, boolean activo) {
		Set<Utilizador> utilizadores = null;
		try (Session session = getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Utilizador> criteria = builder.createQuery(Utilizador.class);
			Root<Utilizador> root = criteria.from(Utilizador.class);
			criteria.select(root);
			Predicate rootRestriction = builder.or(builder.like(root.get("nome"), "%" + str + "%"), builder.like(root.get("usuario"), "%" + str + "%"));
			criteria.where(builder.and(rootRestriction, builder.equal(root.get("activo"), activo)));
			Query<Utilizador> query = session.createQuery(criteria);
			utilizadores = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(utilizadores);
	}

	/**
	 * This method <code>allUsers</code> will search for all users that have the
	 * same status.
	 * 
	 * @param activo is the attribute of the user that show the status.
	 * @return a list of users
	 */
	public List<Utilizador> todosUtilizadores(boolean activo) {
		Set<Utilizador> utilizador = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Utilizador as u where u.activo=:activo";
			Query<Utilizador> query = session.createQuery(hql, Utilizador.class);
			query.setParameter("activo", activo);
			utilizador = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(utilizador);
	}

	/**
	 * This method <code>allUsers</code> will search for all users that have the
	 * same profile.
	 * 
	 * @param perfil is a object that have the roles of the user.
	 * @return a list of users
	 */
	public List<Utilizador> todosUtilizadores(Perfil perfil) {
		Set<Utilizador> utilizadores = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Utilizador as u join fetch u.perfil as p where p=:perfil";
			Query<Utilizador> query = session.createQuery(hql, Utilizador.class);
			query.setParameter("perfil", perfil);
			utilizadores = Set.copyOf(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return List.copyOf(utilizadores);
	}

	/**
	 * Search for a user have the same password
	 * 
	 * @return
	 * @param senha
	 */
	public Utilizador pesquisarSenha(String senha) {
		for (Utilizador utilizador : getAll()) {
			if (passwordMatches(senha, utilizador.getSenha())) {
				return utilizador;
			}
		}
		return null;
	}

	/**
	 * check if there password and username exists and if the user have the that
	 * username and password.
	 * 
	 * @return
	 * @param usuario
	 * @param senha
	 */
	public Utilizador autenticacao(String usuario, String senha) {
		Utilizador utilizador = pesquisarUsuario(usuario);
		if (utilizador != null) {
			boolean check = passwordMatches(senha, utilizador.getSenha());
			if (check) {
				return utilizador;
			}
		}
		return null;
	}

	/**
	 * Encrypt user password
	 * 
	 * @return
	 * @param utilizador
	 */
	public void encryptUserPassword(Utilizador utilizador) {
		String encryptedPassword = encrypt(utilizador.getSenha());
		utilizador.setSenha(encryptedPassword);
	}

	/**
	 * Encrypt the password
	 * 
	 * @return
	 * @param senha
	 */
	public String encrypt(String senha) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		String encryptedPassword = encryptor.encryptPassword(senha);
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
