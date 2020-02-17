package sms.invoicing.quotation.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import sms.invoicing.sale.model.Sale;

/**
 * <code>Quotation</code> inheritance all the methods of class @see Sale.
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "sale_id")
@Table(name = "quotation")
@Access(AccessType.FIELD)
public class Quotation extends Sale implements Comparable<Quotation>{

	public Quotation() {
		super.setType("quotation.record");
	}
	
	@Column(nullable = false, unique = true)
	private String code;

	@Column(nullable = false, columnDefinition = "bit")
	private boolean active;

	@Transient
	private double paid = 0;

	@Override
	public int compareTo(Quotation quotation) {
		return this.code.compareTo(quotation.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Quotation) {
			return this.code.equals(((Quotation) obj).getCode());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.code.hashCode();
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
