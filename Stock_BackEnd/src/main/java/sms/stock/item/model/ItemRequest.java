package sms.stock.item.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import sms.stock.request.model.Request;
import sms.Item;

/**
 * <code>ItemRequest</code> inheritance all methods of @see Item and will
 * add @see Request.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "item_id")
@Table(name = "itemrequest")
@Access(AccessType.FIELD)
public class ItemRequest extends Item {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id", nullable = false)
	@ForeignKey(name = "fk_itemrequest_request")
	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request requisitar) {
		this.request = requisitar;
	}

}
