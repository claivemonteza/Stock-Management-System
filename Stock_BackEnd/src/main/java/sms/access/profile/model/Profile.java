package sms.access.profile.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sms.access.transaction.model.Transaction;
import sms.util.Extra;

/**
 * <code>Profile</code> is the class that will give transaction
 * to the user.
 * 
 * @see Extra
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */

@Entity
@Table(name = "profile")
public class Profile extends Extra implements Comparable<Profile> {

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "profile_transaction", joinColumns = {
			@JoinColumn(name = "profile_id", nullable = true, insertable = true, updatable = true) }, inverseJoinColumns = {
					@JoinColumn(name = "transaction_id", nullable = true, insertable = true, updatable = true) })
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	
	/**
	 * 
	 * This method will compare this class with another 
	 *  with the same name.
	 * 
	 * @param profile
	 */
	@Override
	public int compareTo(Profile profile) {
		return this.name.compareTo(profile.getName());
	}


	/**
	 * 
	 * This method will compare with one object is
	 * equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Profile) {
			return this.name.equals(((Profile) obj).getName());
		}
		return false;
	}

	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
	
}