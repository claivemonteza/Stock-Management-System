package sms.invoicing.sale.model;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import sms.company.model.Stock;
import sms.invoicing.client.model.Client;

/**
 * <code>Sale</code> will show amount that will be paid and from with client.
 * 
 * @see Client
 * @see Stock
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "stock_id")
@Table(name = "sale")
@Access(AccessType.FIELD)
public class Sale extends Stock {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "client_id")
	@ForeignKey(name = "fk_sale_client")
	private Client client;

	@Column(name = "paid",nullable = false)
	private double paid;/*valor pago*/

	@Column(nullable = false)
	private double subTotal;

	@Column(name = "total_vat",nullable = false)
	private double totalVat;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private int year;

	@Temporal(TemporalType.DATE)
	@Column(name = "due_Date")
	private Date dueDate;

	@Column(nullable = false)
	private double total;
	
	@Column(name = "to_pay", nullable = true)
	private double toPay;
	
	@Column (nullable = true)
	private String remark; 
	
	@Column(nullable = false)
	private double transshipment;

	@Transient
	private double percentageToPay;
	
	@Transient
	private String code;

	public Client getClient() {
		return client;
	}

	public void setClient(Client cliente) {
		this.client = cliente;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(double totalVat) {
		this.totalVat = totalVat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public String getPercentageToPay() {
		percentageToPay = 100 - ((paid * 100) / total);
		DecimalFormat deFormat = new DecimalFormat("0.00");
		return deFormat.format(percentageToPay);
	}

	public void setToPay(double toPay) {
		this.toPay = toPay;
	}

	public Double getToPay() {
		return toPay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	/**
	 * @return the transshipment
	 */
	public double getTransshipment() {
		return transshipment;
	}

	/**
	 * @param transshipment the transshipment to set
	 */
	public void setTransshipment(double transshipment) {
		this.transshipment = transshipment;
	}

	/**
	 * @param percentageToPay the percentageToPay to set
	 */
	public void setPercentageToPay(double percentageToPay) {
		this.percentageToPay = percentageToPay;
	}

}
