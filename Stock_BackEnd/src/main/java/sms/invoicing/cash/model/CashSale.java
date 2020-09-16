package sms.invoicing.cash.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import sms.invoicing.sale.model.Sale;


/**
 * <code>CashSale</code> inheritance all the methods of class @see Sale.
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */

@Entity
@PrimaryKeyJoinColumn(name = "sale_id")
@Table(name = "CashSale")
@Access(AccessType.FIELD)
public class CashSale extends Sale implements Comparable<CashSale>{ 

	public CashSale(){
		super.setType("cashsale.record");
	}
	
	@Column(nullable = false, unique = true)
	private String code;
	
	@Override
	public int compareTo(CashSale cashSale) {
		return this.code.compareTo(cashSale.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashSale) {
			return this.code.equals(((CashSale) obj).getCode());
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
