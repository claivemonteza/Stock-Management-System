package sms.invoicing.payment.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.payment.model.Paid;


/**
 * <code>PaidDao</code> is the class that will make the search at the database
 * at the table Paid.
 * 
 * @see Paid
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class PaidDao extends GenericDAO<Paid> {

	public PaidDao() {
		super(Paid.class);
	}
}
