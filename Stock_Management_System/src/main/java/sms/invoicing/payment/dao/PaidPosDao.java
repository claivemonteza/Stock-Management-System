/**
 * 
 */
package sms.invoicing.payment.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.payment.model.PaidPos;

/**
 * <code>PaidPosDao</code> is the class that will make the search at the database
 * at the table PaidPos.
 * 
 * @see PaidPos
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class PaidPosDao extends GenericDAO<PaidPos> {

	public PaidPosDao() {
		super(PaidPos.class);
	}

}
