package mz.co.stock.items.products.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mz.co.stock.Availability;




/**
 * <code>Product</code> is the item that will be sale.
 * 
 * @see Extra
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(name = "uk_product_code_name", columnNames = {
		"code", "name" }))
public class Product  extends Availability implements Comparable<Product>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "productid_generator")
	@SequenceGenerator(name = "productid_generator", initialValue = 1, allocationSize = 1, sequenceName = "product_id_seq")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Column(name="code", nullable = false)
	private String code;
	
	@Column(name= "name", nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean freeVat;
	
	
	@Column(nullable = false)
	private int amount;
	
	@Column(name = "amount_max", nullable = true)
	private int amountMax;

	@Column(name = "amount_min", nullable = true)
	private int amountMin;
	
	@Column(name = "vat",nullable = false)
	private double vat;
	
	@Column(name = "category",nullable = false)
	private String category;
	
	@Column(nullable = false)
	private String brand;
	

	
	/**
	 * This method will compare this class with another 
	 *  with the same name.
	 * 
	 * @param product
	 */
	@Override
	public int compareTo(Product product) {
		return this.name.compareTo(product.getName());
	}

	
	/**
	 * This method will compare with one object is
	 * equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			return this.name.equals(((Product) obj).getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

	/**
	 * @return the data
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


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	/**
	 * @return the freeVat
	 */
	public boolean isFreeVat() {
		return freeVat;
	}


	/**
	 * @param freeVat the freeVat to set
	 */
	public void setFreeVat(boolean freeVat) {
		this.freeVat = freeVat;
	}


	/**
	 * @return the vat
	 */
	public double getVat() {
		return vat;
	}


	/**
	 * @param vat the vat to set
	 */
	public void setVat(double vat) {
		this.vat = vat;
	}


	/**
	 * @return the amountMax
	 */
	public int getAmountMax() {
		return amountMax;
	}


	/**
	 * @param amountMax the amountMax to set
	 */
	public void setAmountMax(int amountMax) {
		this.amountMax = amountMax;
	}


	/**
	 * @return the amountMin
	 */
	public int getAmountMin() {
		return amountMin;
	}


	/**
	 * @param amountMin the amountMin to set
	 */
	public void setAmountMin(int amountMin) {
		this.amountMin = amountMin;
	}


	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}


	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
