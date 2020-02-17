package sms.invoicing.payment.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.payment.model.Paid;

@Repository
public class PaidDao extends GenericDAO<Paid> {

	public PaidDao() {
		super(Paid.class);
	}
}
