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

import sms.invoicing.invoice.model.Invoice;

/**
 * @author Claive Monteza
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "itempaid_id")
@Table(name = "itempaidbill")
@Access(AccessType.FIELD)
public class ItemPaidBill extends ItemPaid {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "invoice_id", nullable = false)
	@ForeignKey(name = "fk_itempaid_invoice")
	private Invoice invoice;

	/**
	 * @return the invoice
	 */
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * @param invoice the invoice to set
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
}
