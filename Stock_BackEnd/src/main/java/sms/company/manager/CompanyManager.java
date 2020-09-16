/**
 * 
 */
package sms.company.manager;

import java.util.List;

import sms.company.model.Company;
import sms.company.model.Stock;

/**
 * This interface have all methods that will been use
 * to save, update, delete and search for:
 *
 * @see Company
 * @see Stock
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public interface CompanyManager {

	public void save(Company company);

	public void update(Company company);

	public void delete(Company company);

	public List<Company> all();
	
	public void save(Stock stock);

	public void update(Stock stock);

	public void delete(Stock stock);

	public List<Stock> allStock();

}
