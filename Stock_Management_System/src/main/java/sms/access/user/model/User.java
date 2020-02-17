package sms.access.user.model;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import sms.access.profile.model.Profile;
import sms.access.transaction.model.Transaction;
import sms.language.Language;
import sms.util.Extra;

/**
 * <code>User</code> is the class that will access to the system by user name and
 * password.
 * 
 * @see Extra
 * @see Profile
 * @see Language
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */

@Entity
@Table(name = "user")
public class User extends Extra implements Comparable<User> {

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Language language;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String username;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profile_id")
	@ForeignKey(name = "fk_user_profile")
	private Profile profile;


	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<UserRoler> userRoles = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_transaction", joinColumns = {
			@JoinColumn(name = "user_id", nullable = true, insertable = true, updatable = true) }, inverseJoinColumns = {
					@JoinColumn(name = "transaction_id", nullable = true, insertable = true, updatable = true) })
	private Set<Transaction> transactions = new HashSet<>();

	/**
	 * This method will compare this class with another with the same name.
	 * 
	 * @param user
	 */
	@Override
	public int compareTo(User user) {
		return this.username.compareTo(user.getUsername());
	}

	/**
	 * This method will compare with one object is equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return this.username.equals(((User) obj).getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * 
	 * @param profile
	 * 
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public Set<UserRoler> getUserRoles() {
		return userRoles;
	}

	/**
	 * 
	 * @param userRoles
	 */
	public void setUserRoles(Set<UserRoler> userRoles) {
		this.userRoles = userRoles;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the transactions
	 */
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public boolean isTransactionAccessible(String transactionCode) {
		List<Transaction> matchingTransactions = select(profile.getTransactions(),
				having(on(Transaction.class).getCode(), equalTo(transactionCode)));
		return !matchingTransactions.isEmpty();
	}
}
