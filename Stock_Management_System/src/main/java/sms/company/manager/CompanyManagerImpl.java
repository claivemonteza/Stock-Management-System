/**
 * 
 */
package sms.company.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sms.company.dao.CompanyDao;
import sms.company.dao.StockDao;
import sms.company.model.Company;
import sms.company.model.Stock;

/** 
 * This class  implements all the methods of the
 * interface @see CompanyManager.
 * 
 * @see CompanyDao
 * @see StockDao
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public class CompanyManagerImpl implements CompanyManager {

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private StockDao stockDao;
	
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}
	
	@Transactional
	public void save(Company company) {
		companyDao.create(company);
	}

	@Transactional
	public void update(Company company) {
		companyDao.update(company);
	}

	@Transactional
	public void delete(Company company) {
		companyDao.delete(company);
	}

	@Transactional(readOnly = true)
	public List<Company> all() {
		return companyDao.getAll();
	}

	@Transactional
	public void save(Stock stock) {
		stockDao.create(stock);
	}

	@Transactional
	public void update(Stock stock) {
		stockDao.update(stock);
	}

	@Transactional
	public void delete(Stock stock) {
		stockDao.delete(stock);
	}

	@Transactional(readOnly = true)
	public List<Stock> allStock() {
		return stockDao.getAll();
	}

}
