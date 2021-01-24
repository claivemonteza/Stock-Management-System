package mz.co.stock.access.profiles.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import mz.co.stock.Availability;

/**
 * <code>Transaction</code> is the class that will allow the profile
 * have many task.
 * 
 * @see Extra
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.8
 */

@Entity
@Table(name = "transaction", uniqueConstraints=@UniqueConstraint(name="uk_transaction_code_name",columnNames= {"code","name"}))
public class Transaction extends Availability implements
		Comparable<Transaction> {

	public static final String PRODUCT_RECORD_WITH_MODIFICATIONS_ENABLED = "204";

	public static final String EXPIRING_PRODUCTS_REPORT = "401";


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="transactionid_generator")
	@SequenceGenerator(name="transactionid_generator", initialValue=1, allocationSize=1, sequenceName="transaction_id_seq")
	private Long id;
	
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	private String url;

	
	/**
	 * This method will compare with one object is
	 * equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transaction) {
			return this.code.equals(((Transaction) obj).getCode());
		}
		return super.equals(obj);
	}

	
	/**
	 * This method will compare this class with another 
	 *  with the same name.
	 * 
	 * @param transaction
	 */
	public int compareTo(Transaction transaction) {
		return name.compareTo(transaction.getName());
	}
	
	@Override
	public int hashCode() {
		return getCode().hashCode();
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
