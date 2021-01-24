package mz.co.stock.access.users.model;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mz.co.stock.Availability;
import mz.co.stock.access.profiles.model.Profile;
import mz.co.stock.access.profiles.model.Transaction;

/**
 * <code>User</code> is the class that will access to the system by user name
 * and password.
 * 
 * @see Availability
 * @see Profile
 * @see Language
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "uk_user_password_username", columnNames = {
		"password", "username" }))
public class User extends Availability implements Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userid_generator")
	@SequenceGenerator(name = "userid_generator", initialValue = 1, allocationSize = 1, sequenceName = "user_id_seq")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Language language;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "username", nullable = false)
	private String username;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profile_id", foreignKey = @ForeignKey(name = "fk_user_profile"), nullable = false)
	private Profile profile;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
