package sms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.item.batch.model.Batch;

/**
 * This superclass <code>Item</code> will be extends at ItemRequest and SaleItem.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "item")
@Access(AccessType.FIELD)
public class Item extends Identidade implements Comparable<Item> {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "batch_id", nullable = false)
	@ForeignKey(name = "fk_item_batch")
	private Batch batch;

	@Column(name = "amount", nullable = false)
	private int amount;

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
}
