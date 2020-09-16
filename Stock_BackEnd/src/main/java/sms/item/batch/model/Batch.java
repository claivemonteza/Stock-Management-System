package sms.item.batch.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import sms.item.product.model.Product;
import sms.util.Extra;

/**
 * <code>Batch</code> will show that one product will
 * have different price and expiration date.
 * 
 * @see Extra
 * @see Product
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@Table(name = "batch")
public class Batch extends Extra implements Comparable<Batch> {

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "expiration_date", nullable = false)
	private Date expirationDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_manufacturing", nullable = false)
	private Date dateManufacturing;
	
	@Column(name = "sale_price", nullable = false)
	private double salePrice;

	@Column(name = "cost_price", nullable = false)
	private double costPrice;

	@Column(nullable = true)
	private int amount = 0;
	
	@Column(nullable = false)
	private String code;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	@ForeignKey(name = "fk_batch_product")
	private Product product;

	
	@Override
	public int compareTo(Batch batch) {
		return this.code.compareTo(batch.getCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Batch) {
			return this.code.equals(((Batch) obj).getCode());
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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getDateManufacturing() {
		return dateManufacturing;
	}

	public void setDateManufacturing(Date dateManufacturing) {
		this.dateManufacturing = dateManufacturing;
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

	@Override
	public String toString() {
		return "Data=" + date + ", validade=" + expirationDate + ", fabrico=" + dateManufacturing + ", precoVenda=" + salePrice
				+ ", precoCompra=" + costPrice + ", quantidade=" + amount + ", referencia=" + code
				+ ", produto=" + product.getName() + "\n";
	}

	
}
