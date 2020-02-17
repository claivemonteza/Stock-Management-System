/**
 * 
 */
package sms.invoicing.payment.dao;

import sms.GenericDAO;
import sms.invoicing.payment.model.ItemPaid;

/**
 * @author Claive Monteza
 *
 */
public class ItemPaidDao extends GenericDAO<ItemPaid> {

	public ItemPaidDao() {
		super(ItemPaid.class);
	}

}
