 /**
 * 
 */
package mz.co.stock.access;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import mz.co.stock.access.profiles.model.Profile;
import mz.co.stock.access.profiles.model.Transaction;
import mz.co.stock.access.users.model.User;



/**
 * This interface have all methods that will been use
 * to save, update, delete and search for:
 *
 * @see Transaction
 * @see Profile
 * @see User
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
public interface AccessManager extends UserDetailsService{
	
	public void save(Profile profile) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public void delete(Profile profile) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;

	public void update(Profile profile) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public Profile searchProfile(String name) throws NullPointerException;
	
	public Profile searchProfile(String name,boolean active) throws NullPointerException;
	
	public List<Profile> findProfiles(String str) throws NullPointerException;

	public List<Profile> findProfiles(String str, boolean active) throws NullPointerException;
	
	public List<Profile> selectedProfile(Profile profile) throws NullPointerException;
	
	public List<Profile> allProfiles(boolean active) throws NullPointerException;
	
	public List<Profile> allProfiles() throws NullPointerException;
	
	public void save(Transaction transaction) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;
	
	public void update(Transaction transaction) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;
	
	public void delete(Transaction transaction)throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;
	
	public Transaction findTransactionByCode(String code)throws NullPointerException;
	
	public Transaction findTransactionByName(String name)throws NullPointerException;
	
	public List<Transaction> find(String str)throws NullPointerException;
	
	public List<Transaction> find(String str, boolean active)throws NullPointerException;
	
	public List<Transaction> allTransactions(boolean active)throws NullPointerException;
	
	public List<Transaction> allTransactions()throws NullPointerException;
	
	public void save(User user)throws ConstraintViolationException, DataIntegrityViolationException, SQLIntegrityConstraintViolationException;

	public void delete(User user) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;

	public void update(User user) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;
	
	public User findByName(String nome)throws NullPointerException;
	
	public User findByUsername(String username)throws NullPointerException;

	public User authentication(String username, String password)throws EncryptionOperationNotPossibleException ;
	
	public User searchPassword(String password) throws NullPointerException;

	public List<User> findUsers(String str) throws NullPointerException;

	public List<User> findUsers(String str, boolean active) throws NullPointerException;

	public List<User> selectedUser(User user)throws NullPointerException;

	public List<User> allUsers(Profile perfil)throws NullPointerException;
	
	public List<User> allUsers(boolean active)throws NullPointerException;
	
	public List<User> allUsers()throws NullPointerException;
	
	public void resetPassword(User user)throws NullPointerException;

	public boolean changePassword(User user, String password)throws NullPointerException;

}
