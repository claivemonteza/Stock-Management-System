/**
 * 
 */
package sms.company.dao;

import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.company.model.Company;


/**
 * <code>CompanyDao</code> is the class that will access to the system by user name and
 * password.
 * 
 * @see Company
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class CompanyDao extends GenericDAO<Company> {

	public CompanyDao() {
		super(Company.class);
	}

}
