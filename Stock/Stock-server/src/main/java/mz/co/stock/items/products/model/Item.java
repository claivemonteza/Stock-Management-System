package mz.co.stock.items.products.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mz.co.stock.items.batches.model.Batch;

import javax.persistence.ForeignKey;



/**
 * This superclass <code>Item</code> will be extends at ItemRequest and SaleItem.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "item")
@Access(AccessType.FIELD)
public class Item implements Comparable<Item> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="itemid_generator")
	@SequenceGenerator(name="itemid_generator", initialValue=1, allocationSize=1, sequenceName="item_id_seq")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "batch_id", foreignKey = @ForeignKey(name = "BATCH_ID_FK"),  nullable = false)
	private Batch batch;

	@Column(name = "amount", nullable = false)
	private int amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Unity unity;

	@Column(name = "unit_total", nullable = false)
	private double unitTotal;

	@Column(name = "vat_unit_total", nullable = false)
	private double vatUnitTotal;

	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			return this.batch.equals(((Item) obj).getBatch());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Item item) {
		return this.batch.compareTo(item.getBatch());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.batch.hashCode();
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getUnitTotal() {
		return unitTotal;
	}

	public void setUnitTotal(double unitTotal) {
		this.unitTotal = unitTotal;
	}

	public double getVatUnitTotal() {
		return vatUnitTotal;
	}

	public void setVatUnitTotal(double vatUnitTotal) {
		this.vatUnitTotal = vatUnitTotal;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the unity
	 */
	public Unity getUnity() {
		return unity;
	}

	/**
	 * @param unity the unity to set
	 */
	public void setUnity(Unity unity) {
		this.unity = unity;
	}
	
}
