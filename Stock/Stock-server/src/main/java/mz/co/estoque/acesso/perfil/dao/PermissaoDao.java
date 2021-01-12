/**
 * 
 */
package mz.co.estoque.acesso.perfil.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import mz.co.estoque.GenericDao;
import mz.co.estoque.acesso.perfil.model.Permissao;

/**
 * <code>PermissaoDao</code> is the class that will make the search 
 * at the database at the table Transaction.
 * 
 * @see Permissao
 * @see GenericDao
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class PermissaoDao extends GenericDao<Permissao> {

	public PermissaoDao() {
		super(Permissao.class);
	}
	
	/**
	 * Will search for all permissions are active.
	 * 
	 * @param active
	 * @return
	 */
	public List<Permissao> todasPermissoes(boolean activo){
		List<Permissao> permissoes = null;
		try (Session session = getSessionFactory().openSession()) {
			String hql = "from Permissao as p where p.activo=:activo";
			Query<Permissao> query = session.createQuery(hql, Permissao.class);
			query.setParameter("activo", activo);
			permissoes = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return permissoes;
	}

}
