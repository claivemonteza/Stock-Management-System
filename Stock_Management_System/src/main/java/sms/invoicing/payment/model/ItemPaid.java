/**
 * 
 */
package sms.invoicing.payment.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.Identidade;

/**
 * @author Claive Monteza
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "itempaid")
@Access(AccessType.FIELD)
public class ItemPaid extends Identidade {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paid_id", nullable = true)
	@ForeignKey(name = "fk_itempaid_paid")
	private Paid paid;
	
	@Column(name = "amount", nullable = true)
	private double amount;

	/**
	 * @return the paid
	 */
	public Paid getPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(Paid paid) {
		this.paid = paid;
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
