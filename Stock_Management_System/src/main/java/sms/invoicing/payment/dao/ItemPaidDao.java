/**
 * 
 */
package sms.invoicing.payment.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.payment.model.ItemPaid;

/**
 * <code>ItemPaidDao</code> is the class that will make the search at the database
 * at the table ItemPaid.
 * 
 * @see ItemPaid
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ItemPaidDao extends GenericDAO<ItemPaid> {

	public ItemPaidDao() {
		super(ItemPaid.class);
	}

}
