package sms.invoicing.invoice.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import sms.invoicing.client.model.Client;
import sms.invoicing.quotation.model.Quotation;
import sms.invoicing.sale.model.Sale;

/**
 * <code>Invoice</code> inheritance all the methods of class @see Sale.
 * 
 * @see Client
 * @see Quotation
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "sale_id")
@Table(name = "invoice")
@Access(AccessType.FIELD)
public class Invoice extends Sale implements Comparable<Invoice>{
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean madePayment;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean canceled;
	
	@Column(nullable = false, unique = true)
	private String code;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "quotation_id")
	@ForeignKey(name = "fk_invoice_quotation")
	private Quotation quotation;

	@Transient
	private Client client;
	
	public Invoice(){
		this.madePayment = false;
		this.canceled = false;
		super.setType("invoice.record");
	}

	@Override
	public int compareTo(Invoice invoice) {
		return this.code.compareTo(invoice.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Invoice) {
			return this.code.equals(((Invoice) obj).getCode());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

	public boolean isMadePayment() {
		return madePayment;
	}

	public void setMadePayment(boolean madePayment) {
		this.madePayment = madePayment;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

}
