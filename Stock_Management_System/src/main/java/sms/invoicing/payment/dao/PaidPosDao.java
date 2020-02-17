/**
 * 
 */
package sms.invoicing.payment.dao;

import sms.GenericDAO;
import sms.invoicing.payment.model.PaidPos;

/**
 * @author Claive Monteza
 *
 */
public class PaidPosDao extends GenericDAO<PaidPos> {

	public PaidPosDao() {
		super(PaidPos.class);
	}

}
