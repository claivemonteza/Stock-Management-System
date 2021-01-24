package mz.co.stock.items;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import mz.co.stock.items.batches.model.Batch;
import mz.co.stock.items.products.model.Product;



/**
 * This interface have all methods that will been use
 * to save, update, delete and search for:
 *
 * @see Batch
 * @see Product
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.8
 */
public interface ItemManager {

	public void save(Batch batch)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public void update(Batch batch)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public void delete(Batch batch)throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;

	public Batch findByBatchCode(String code)throws NullPointerException;

	public Batch findByBatchCode(String code, boolean active)throws NullPointerException;

	public List<Batch> findBatch(String str)throws NullPointerException;

	public List<Batch> findBatch(String str, boolean active)throws NullPointerException;

	public List<Batch> selectedBatch(Batch batch)throws NullPointerException;

	public List<Batch> outOfDateBatches(Date validade)throws NullPointerException;

	public List<Batch> allBatches(boolean active)throws NullPointerException;

	public List<Batch> allBatches()throws NullPointerException;
	
	public Integer calculateAmountBatch(Product product);
	
	public Integer calculateAmountOfBatchActive(Product product, boolean active);
	
	public double calculateCost(Product product);
	
	public double calculateCostOfBatchActive(Product product, boolean active);
	
	public double calculateSale(Product product);
	
	public double calculateSaleOfBatchActive(Product product, boolean active);
	
	public void save(Product product)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public void delete(Product product) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;

	public void update(Product product)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;
	
	public List<String> allCategories() throws NullPointerException;

	public List<Product> allProducts()throws NullPointerException;

	public List<Product> allProducts(boolean active)throws NullPointerException;

	public Product findByName(String name)throws NullPointerException;

	public Product findByName(String name, boolean active)throws NullPointerException;

	public Product findByCode(String code)throws NullPointerException;
	
	public Product findByCode(String code, boolean active)throws NullPointerException;
	
	public List<Batch> allBatches(Product product)throws NullPointerException;
	
	public List<Batch> allBatches(Product product, boolean active)throws NullPointerException;
	
	public int calculateAmount(Product product)throws NullPointerException;
	
	public List<Product> selectedProduct(Product product)throws NullPointerException;
	
	public List<Product> find(String str)throws NullPointerException;

	public List<Product> find(String str, boolean active)throws NullPointerException;
	
}
