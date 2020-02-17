package sms.invoicing.item.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.invoicing.sale.model.Sale;
import sms.Item;

/**
 * This class will be inherit all methods of class @see Item and will
 * add @see Sale.
 * 
 * @version 1.0
 * @since 1.6
 * 
 * @author Claive Monteza
 */
@Entity
@PrimaryKeyJoinColumn(name = "item_id")
@Table(name = "saleitem")
@Access(AccessType.FIELD)
public class SaleItem extends Item {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", nullable = false)
	@ForeignKey(name = "fk_saleitem_sale")
	private Sale sale;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
}
