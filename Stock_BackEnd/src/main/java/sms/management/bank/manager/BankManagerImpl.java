/**
 * 
 */
package sms.management.bank.manager;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sms.management.bank.dao.ViaBankDao;
import sms.management.bank.model.ViaBank;

/**
 * This class call the BankDao and implments all the methods of the
 * interface.
 * 
 * @see ViaBankDao
 * @see BankManager
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Service("BankManager")
public class BankManagerImpl implements BankManager {

	@Autowired
	private ViaBankDao viaBankDao;
	
	public void setViaBankDao(ViaBankDao viaBankDao) {
		this.viaBankDao = viaBankDao;
	}

	@Transactional
	public void save(ViaBank viaBank)throws DataIntegrityViolationException, ConstraintViolationException, SQLIntegrityConstraintViolationException{
		viaBankDao.create(viaBank);		
	}

	@Transactional(readOnly = true)
	public Set<String> allBanks() {
		return Set.copyOf(viaBankDao.allBanks());
	}

	
	@Transactional(readOnly = true)
	public List<ViaBank> allViaBanks() throws NullPointerException {
		return viaBankDao.getAll();
	}

	@Transactional(readOnly = true)
	public  Set<String> allTypes() throws NullPointerException {
		return Set.copyOf(viaBankDao.allTypes());
	}

}
