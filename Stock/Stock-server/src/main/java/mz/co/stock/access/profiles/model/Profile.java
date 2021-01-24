package mz.co.stock.access.profiles.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mz.co.stock.Availability;



/**
 * <code>Profile</code> is the class that will give transaction
 * to the user.
 * 
 * @see Extra
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.8
 */

@Entity
@Table(name = "profile", uniqueConstraints=@UniqueConstraint(name="uk_profile_name",columnNames= {"name"}))
public class Profile extends Availability implements Comparable<Profile> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="profileid_generator")
	@SequenceGenerator(name="profileid_generator", initialValue=1, allocationSize=1, sequenceName="profile_id_seq")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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