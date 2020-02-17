package sms.invoicing.receipt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sms.invoicing.payment.model.Paid;
import sms.util.DateUtil;
import sms.Identidade;

/**
 * <code>Receipt</code> will identify with someone(person or company)
 * declares that they have received out of what is specified.
 * 
 * @see Identidade
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@Table(name = "receipt")
public class Receipt extends Identidade implements Comparable<Receipt> {

	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = DateUtil.parse();
	
	@Column(nullable = false)
	private String code;
	
	@OneToOne
	@JoinColumn(name = "paid_id")
	private Paid paid;

	
	@Column(nullable = true)
	private String note;
	
	
	@Override
	public int compareTo(Receipt receipt) {
		return this.code.compareTo(receipt.getCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Receipt) {
			return this.code.equals(((Receipt) obj).getCode());
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Paid getPaid() {
		return paid;
	}

	public void setPaid(Paid paid) {
		this.paid = paid;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
