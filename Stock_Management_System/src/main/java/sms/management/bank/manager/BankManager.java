/**
 * 
 */
package sms.management.bank.manager;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import sms.management.bank.model.ViaBank;

/**
 * This interface have all methods that will been use to save, update, delete
 * and search for the bank.
 * 
 * @see Bank
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public interface BankManager {

	public void save(ViaBank bank)throws DataIntegrityViolationException, ConstraintViolationException, SQLIntegrityConstraintViolationException;

	public Set<String> allBanks();
	
	public List<ViaBank> allViaBanks() throws NullPointerException;
	
	public  Set<String> allTypes()throws NullPointerException;
}
