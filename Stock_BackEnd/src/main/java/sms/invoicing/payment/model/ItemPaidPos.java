/**
 * 
 */
package sms.invoicing.payment.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.invoicing.cash.model.CashSale;

/**
 * @author Claive Monteza
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "itempaid_id")
@Table(name = "itempaidpos")
@Access(AccessType.FIELD)
public class ItemPaidPos extends ItemPaid{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cashSale_id", nullable = false)
	@ForeignKey(name = "fk_itempaid_cashSale")
	private CashSale cashSale;

	/**
	 * @return the cashSale
	 */
	public CashSale getCashSale() {
		return cashSale;
	}

	/**
	 * @param cashSale the cashSale to set
	 */
	public void setCashSale(CashSale cashSale) {
		this.cashSale = cashSale;
	}
	
}
