/**
 * 
 */
package sms.stock;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import sms.item.batch.model.Batch;
import sms.stock.item.model.ItemRequest;
import sms.stock.provider.model.Provider;
import sms.stock.request.model.Request;

/**
 * This interface have all methods that will been use to save, update, delete
 * and search for:
 *
 * @see Batch
 * @see ItemRequest
 * @see Provider
 * @see Request
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public interface StockManager {

	public void save(ItemRequest item);

	public void delete(ItemRequest item);

	public void update(ItemRequest item);

	public List<ItemRequest> allItems();

	public List<ItemRequest> allItems(Request request);

	public int totalAmount(Batch batch);

	public boolean addItem(int position, ItemRequest item);

	public void removerItem(ItemRequest item);

	public void cleanUp();

	public void saveItens(Request request);

	public List<ItemRequest> selectedItems();

	public void save(Provider provider) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException;

	public void update(Provider provider) throws DataIntegrityViolationException, ConstraintViolationException,
			SQLIntegrityConstraintViolationException;

	public void delete(Provider provider) throws IllegalArgumentException, DataIntegrityViolationException,
			SQLIntegrityConstraintViolationException, BatchUpdateException;

	public Provider findByName(String name) throws NullPointerException;

	public Provider findByName(String name, boolean active) throws NullPointerException;

	public Provider findByNuit(String nuit) throws NullPointerException;

	public List<Provider> find(String str) throws NullPointerException;

	public List<Provider> find(String str, boolean active) throws NullPointerException;

	public List<Provider> selectedProvider(Provider provider) throws NullPointerException;

	public List<Provider> allProviders() throws NullPointerException;

	public List<Provider> allProviders(boolean active) throws NullPointerException;

	public List<Request> allRequisitions(Provider provider) throws NullPointerException;
	
	public void save(Request request);

	public void delete(Request request);

	public void update(Request request);

	public List<Request> allRequests();

	public List<Request> allRequests(Date date);
	
	public Request findByCode(String code);
	
	public List<Request> selectedRequest(Request request);

}
