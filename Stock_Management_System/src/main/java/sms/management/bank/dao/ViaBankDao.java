/**
 * 
 */
package sms.management.bank.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.management.bank.model.ViaBank;

/**
 * @author User
 *
 */
@Repository
public class ViaBankDao extends GenericDAO<ViaBank> {

	public ViaBankDao() {
		super(ViaBank.class);
	}

	@SuppressWarnings("unchecked")
	public List<String> allBanks(){
		String hql = "Select viabank.bank from ViaBank as viabank";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> allTypes(){
		String hql = "Select viabank.type from ViaBank as viabank";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}
