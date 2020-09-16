package sms.invoicing.payment.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import sms.Identidade;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "paid")
@Access(AccessType.FIELD)
public class Paid extends Identidade {

	
	@Column(name = "amount_paid", nullable = false)
	private double amountPaid;


	/**
	 * @return the amountPaid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}

	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	
	
}
