/**
 * 
 */
package sms.management.bank.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import sms.Identidade;

/**
 * <code>ViaBank</code> will define the amount of payment and the type of payment.
 * 
 * @see Identidade
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@Table(name = "viabank")
public class ViaBank extends Identidade{

	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "number", unique = true, nullable = false)
	private String number;
	
	@Column(name = "bank", nullable = false)
	private String bank;
	
	@Column(name = "term", nullable = false)
	private Date term;
	
	@Column(name = "amount", nullable = false)
	private double amount;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return the term
	 */
	public Date getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(Date term) {
		this.term = term;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}


	
}
