package sms.invoicing.payment.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Claive Monteza
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "paid_id")
@Table(name = "paidpos")
@Access(AccessType.FIELD)
public class PaidPos extends Paid{
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private PayMethod payMethod;
	
	
	@Column(name ="code_transaction", nullable = true)
	private String codeTransaction;

	/**
	 * @return the payMethod
	 */
	public PayMethod getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}



	/**
	 * @return the codeTransaction
	 */
	public String getCodeTransaction() {
		return codeTransaction;
	}

	/**
	 * @param codeTransaction the codeTransaction to set
	 */
	public void setCodeTransaction(String codeTransaction) {
		this.codeTransaction = codeTransaction;
	}
	
}
