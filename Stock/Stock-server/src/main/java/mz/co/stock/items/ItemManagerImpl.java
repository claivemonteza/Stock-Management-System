package mz.co.stock.items;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mz.co.stock.items.batches.dao.BatchDao;
import mz.co.stock.items.batches.model.Batch;
import mz.co.stock.items.products.dao.ProductDao;
import mz.co.stock.items.products.model.Product;

/**
 * This class implements all the methods of the interface @see ItemManager.
 * 
 * @see BatchDao
 * @see ProductDao
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */

@Service("ItemManager")
public class ItemManagerImpl implements ItemManager {

	@Autowired
	private BatchDao batchDao;
	@Autowired
	private ProductDao productDao;

	public void setBatchDao(BatchDao batchDao) {
		this.batchDao = batchDao;
	}
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}


	@Transactional
	public void save(Batch batch)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException {
		batchDao.create(batch);
	}

	@Transactional
	public void update(Batch batch) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		batchDao.update(batch);
	}

	@Transactional
	public void delete(Batch batch) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException{
		batchDao.delete(batch);		
	}

	@Transactional(readOnly = true)
	public List<Batch> allBatches() throws NullPointerException{
		return batchDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Batch> allBatches(boolean active) throws NullPointerException{
		return batchDao.getAllActives(active);
	}
	
	@Transactional(readOnly = true)
	public List<Batch> allBatches(Product product) throws NullPointerException {
		return batchDao.allBatches(product);
	}
	
	@Transactional(readOnly = true)
	public List<Batch> allBatches(Product product, boolean active)throws NullPointerException{
		return batchDao.allBatches(product, active);
	}

	@Transactional(readOnly = true)
	public Batch findByBatchCode(String code) throws NullPointerException{
		return this.batchDao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public List<Batch> outOfDateBatches(Date expirationDate) throws NullPointerException{
		return this.batchDao.findBatchesExpiring(expirationDate);
	}

	@Transactional(readOnly = true)
	public List<Batch> findBatch(String str) throws NullPointerException{
		return batchDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Batch> findBatch(String str, boolean active)throws NullPointerException {
		return batchDao.find(str, active);
	}

	@Override
	public List<Batch> selectedBatch(Batch batch) throws NullPointerException{
		List<Batch> batches = new ArrayList<Batch>();
		batches.add(batch);
		return batches;
	}
	
	@Transactional
	public Integer calculateAmountBatch(Product product) {
		return batchDao.calculateAmount(product);
	}
	
	@Transactional
	public Integer calculateAmountOfBatchActive(Product product, boolean active) {
		return batchDao.calculateAmountOfBatchWhereActive(product, active);
	}

	@Transactional
	public double calculateCost(Product product) {
		return batchDao.calculateCost(product);
	}
	
	@Transactional
	public double calculateCostOfBatchActive(Product product, boolean active) {
		return batchDao.calculateCostOfBatchActive(product, active);
	}

	@Transactional
	public double calculateSale(Product product) {
		return batchDao.calculateSale(product);
	}
	
	@Transactional
	public double calculateSaleOfBatchActive(Product product, boolean active) {
		return batchDao.calculateSaleOfBatchActive(product, active);
	}
	
	@Transactional
	public void save(Product product) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException {
		productDao.create(product);
	}

	@Transactional
	public void update(Product product) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException {
		productDao.update(product);
	}
	
	@Transactional
	public void delete(Product product) throws IllegalArgumentException, DataIntegrityViolationException,
			SQLIntegrityConstraintViolationException, BatchUpdateException {
		productDao.delete(product);
	}
	
	@Transactional(readOnly = true)
	public Product findByCode(String code) throws NullPointerException {
		return productDao.findByCode(code);
	}
	
	@Transactional(readOnly = true)
	public Product findByName(String name) throws NullPointerException {
		return productDao.findByName(name);
	}
	
	@Transactional(readOnly = true)
	public List<String> allCategories() throws NullPointerException {
		return List.copyOf(productDao.allCategories());
	}

	@Transactional(readOnly = true)
	public List<Product> allProducts() throws NullPointerException {
		return productDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Product> allProducts(boolean active) throws NullPointerException {
		return productDao.allProducts(active);
	}

	@Override
	public int calculateAmount(Product product) throws NullPointerException {
		List<Batch> batchs = allBatches(product);
		int amount = 0;
		for (Batch batch : batchs) {
			amount = amount + batch.getAmount();
		}
		return amount;
	}

	@Override
	public List<Product> selectedProduct(Product product) throws NullPointerException {
		List<Product> products = new ArrayList<Product>();
		products.add(product);
		return products;
	}

	@Transactional(readOnly = true)
	public List<Product> find(String str) throws NullPointerException {
		return productDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Product> find(String str, boolean active) throws NullPointerException {
		return productDao.find(str, active);
	}

}
