package sms.stock.request.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.company.model.Stock;
import sms.stock.provider.model.Provider;

/**
 * <code>Request</code> will show amount that will be paid and from with provider.
 * 
 * @see Provider
 * @see Stock
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "stock_id")
@Table(name = "request")
@Access(AccessType.FIELD)
public class Request extends Stock implements Comparable<Request> {

	@Column(nullable = false, unique = true)
	private String code;
	
	@Column(nullable = false)
	private double subtotal;
	
	@Column(nullable = false)
	private double vat;
	
	@Column(nullable = false)
	private double total;
	
	@Column(name = "invoice_provider", nullable = false)
	private String invoiceProvider;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "provider_id", nullable = false)
	@ForeignKey(name = "fk_request_provider")
	private Provider provider;
	
	@Override
	public int compareTo(Request request) {
		return this.code.compareTo(request.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Request) {
			return this.code.equals(((Request) obj).getCode());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	/**
	 * @return the invoiceProvider
	 */
	public String getInvoiceProvider() {
		return invoiceProvider;
	}

	/**
	 * @param invoiceProvider the invoiceProvider to set
	 */
	public void setInvoiceProvider(String invoiceProvider) {
		this.invoiceProvider = invoiceProvider;
	}
	
}
