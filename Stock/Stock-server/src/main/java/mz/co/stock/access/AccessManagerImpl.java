/**
 * 
 */
package mz.co.stock.access;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mz.co.stock.access.profiles.dao.ProfileDao;
import mz.co.stock.access.profiles.dao.TransactionDao;
import mz.co.stock.access.profiles.model.Profile;
import mz.co.stock.access.profiles.model.Transaction;
import mz.co.stock.access.users.dao.UserDao;
import mz.co.stock.access.users.model.User;

/** 
 * This class  implements all the methods of the
 * interface @see AccessManager.
 * 
 * @see UserDao
 * @see ProfileDao
 * @see TransactionDao
 * 
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */

@Service("AccessManager")
public class AccessManagerImpl implements AccessManager {

	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private UserDao userDao;

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	@Transactional
	public void save(Transaction transaction) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException {
		transactionDao.create(transaction);
	}
	
	@Transactional
	public void update(Transaction transaction) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		transactionDao.update(transaction);
	}
	
	@Transactional   
	public void delete(Transaction transaction)throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException {
		transactionDao.delete(transaction);
	}
	
	@Transactional(readOnly = true)
	public Transaction findTransactionByCode(String code) throws NullPointerException {
		return transactionDao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public Transaction findTransactionByName(String name) throws NullPointerException {
		return transactionDao.findByName(name);
	}

	@Transactional(readOnly = true)
	public List<Transaction> find(String str) throws NullPointerException {
		return transactionDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Transaction> find(String str, boolean active) throws NullPointerException {
		return transactionDao.find(str, active);
	}
	
	@Transactional(readOnly = true)
	public List<Transaction> allTransactions(boolean active) {
		return transactionDao.allPermissions(active);
	}

	@Transactional(readOnly = true)
	public List<Transaction> allTransactions() {
		return transactionDao.getAll();
	}

	@Transactional
	public void save(Profile profile) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException {
		profileDao.create(profile);
	}

	@Transactional
	public void delete(Profile profile) throws IllegalArgumentException, DataIntegrityViolationException,
			SQLIntegrityConstraintViolationException, BatchUpdateException {
		profileDao.delete(profile);
	}

	@Transactional
	public void update(Profile profile) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException {
		profileDao.update(profile);
	}

	@Transactional(readOnly = true)
	public Profile searchProfile(String name) throws NullPointerException {
		return profileDao.findByName(name);
	}

	@Transactional(readOnly = true)
	public Profile searchProfile(String name, boolean active) throws NullPointerException {
		return profileDao.findByName(name, active);
	}

	@Transactional(readOnly = true)
	public List<Profile> findProfiles(String str) throws NullPointerException {
		return profileDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Profile> findProfiles(String str, boolean active) throws NullPointerException {
		return profileDao.find(str, active);
	}

	@Override
	public List<Profile> selectedProfile(Profile profile) throws NullPointerException {
		List<Profile> profiles = new ArrayList<Profile>();
		profiles.add(profile);
		return profiles;
	}

	@Transactional(readOnly = true)
	public List<Profile> allProfiles(boolean active) throws NullPointerException {
		return profileDao.allProfiles(active);
	}

	@Transactional(readOnly = true)
	public List<Profile> allProfiles() throws NullPointerException {
		return profileDao.getAll();
	}

	@Transactional
	public void save(User user) throws ConstraintViolationException, DataIntegrityViolationException,
			SQLIntegrityConstraintViolationException {
		userDao.encryptUserPassword(user);
		userDao.create(user);
	}

	@Transactional
	public void delete(User user) throws IllegalArgumentException, DataIntegrityViolationException,
			SQLIntegrityConstraintViolationException, BatchUpdateException {
		userDao.delete(user);
	}

	@Transactional
	public void update(User user) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException {
		userDao.update(user);
	}

	@Transactional(readOnly = true)
	public User findByName(String nome) throws NullPointerException {
		return userDao.findByName(nome);
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) throws NullPointerException {
		return userDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public User authentication(String username, String password) throws NullPointerException {
		return userDao.authentication(username, password);
	}

	@Transactional(readOnly = true)
	public User searchPassword(String password) throws NullPointerException {
		return userDao.searchPassword(password);
	}

	@Transactional(readOnly = true)
	public List<User> findUsers(String str) throws NullPointerException {
		return userDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<User> findUsers(String str, boolean active) throws NullPointerException {
		return userDao.find(str, active);
	}

	@Override
	public List<User> selectedUser(User user) throws NullPointerException {
		List<User> users = new ArrayList<User>();
		users.add(user);
		return users;
	}

	@Transactional(readOnly = true)
	public List<User> allUsers(Profile perfil) throws NullPointerException {
		return userDao.allUsers(perfil);
	}

	@Transactional(readOnly = true)
	public List<User> allUsers(boolean active) throws NullPointerException {
		return userDao.allUsers(active);
	}

	@Transactional(readOnly = true)
	public List<User> allUsers() {
		return userDao.getAll();
	}

	@Transactional
	public void resetPassword(User user) throws NullPointerException {
		user.setPassword("wmyjbe5mN2Yyw17/YBzXlsTieMpKTnzbvXXd/xCbPd3azt8F9lZkD+qt9ZJDWGTr");
		userDao.update(user);
	}

	@Transactional
	public boolean changePassword(User user, String password) {
		if (searchPassword(password) == null) {
			user.setPassword(password);
			userDao.encryptUserPassword(user);
			userDao.update(user);
			return true;
		}
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException, DataAccessException {
		return null;
	}
}
