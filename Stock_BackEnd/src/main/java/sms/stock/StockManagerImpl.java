/**
 * 
 */
package sms.stock;

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

import sms.item.batch.model.Batch;
import sms.stock.item.dao.ItemRequestDao;
import sms.stock.item.model.ItemRequest;
import sms.stock.provider.dao.ProviderDao;
import sms.stock.provider.model.Provider;
import sms.stock.request.dao.RequestDao;
import sms.stock.request.model.Request;

/**
 * This class implements all the methods of the interface @see StockManager.
 * 
 * @see ItemRequestDao
 * @see ProviderDao
 * @see RequestDao
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Service("StockManager")
public class StockManagerImpl implements StockManager {

	
	@Autowired
	private ItemRequestDao itemRequestDao;
	@Autowired
	private ProviderDao providerDao;
	@Autowired
	private RequestDao requestDao;
	
	private List<ItemRequest> items = new ArrayList<ItemRequest>();
	
	public void setItemRequestDao(ItemRequestDao itemRequestDao) {
		this.itemRequestDao = itemRequestDao;
	}
	
	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}
	
	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}
	
	@Transactional
	public void save(ItemRequest itemRequest) {
		itemRequestDao.create(itemRequest);
	}

	@Transactional
	public void delete(ItemRequest itemRequest) {
		itemRequestDao.delete(itemRequest);
	}

	@Transactional
	public void update(ItemRequest itemRequest) {
		itemRequestDao.update(itemRequest);
	}

	@Transactional(readOnly = true)
	public List<ItemRequest> allItems() {
		return itemRequestDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<ItemRequest> allItems(Request entrada) {
		return itemRequestDao.requestDetails(entrada);
	}
	
	@Transactional
	public int totalAmount(Batch batch) {
		return itemRequestDao.totalAmount(batch);
	}
	
	@Override
	public boolean addItem(int position, ItemRequest item) {
		if (!items.contains(item)) {
			if (position == 0) {
				items.add(position, item);
			} else {
				items.add(item);
			}
			return true;
		}
		return false;
	}

	@Override
	public void removerItem(ItemRequest item) {
		items.remove(item);
	}

	@Override
	public void cleanUp() {
		items.clear();
	}

	@Transactional
	public void saveItens(Request request) {
		for (ItemRequest item : items) {
			item.setRequest(request);
			save(item);
			//pm.actualizarQuantidadeLote(item);
		}
	}

	@Override
	public List<ItemRequest> selectedItems() {
		return items;
	}
	
	@Transactional
	public void save(Provider provider) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		providerDao.create(provider);
	}

	@Transactional
	public void update(Provider provider) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		providerDao.update(provider);
	}

	@Transactional
	public void delete(Provider provider) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException{
		providerDao.delete(provider);
	}

	@Override
	public List<Provider> selectedProvider(Provider provider) throws NullPointerException{
		List<Provider> providers = new ArrayList<Provider>();
		providers.add(provider);
		return providers;
	}

	@Transactional(readOnly = true)
	public Provider findByName(String name) throws NullPointerException{
		return providerDao.findByName(name);
	}

	@Transactional(readOnly = true)
	public Provider findByName(String name, boolean active) throws NullPointerException{
		return providerDao.findByName(name, active);
	}

	@Transactional(readOnly = true)
	public Provider findByNuit(String nuit)throws NullPointerException {
		return providerDao.findByNuit(nuit);
	}

	@Transactional(readOnly = true)
	public List<Provider> allProviders()throws NullPointerException {
		return providerDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Provider> allProviders(boolean active) throws NullPointerException{
		return providerDao.allProviders(active);
	}

	@Transactional(readOnly = true)
	public List<Request> allRequisitions(Provider provider) throws NullPointerException{
		return providerDao.allRequisitions(provider);
	}

	@Transactional(readOnly = true)
	public List<Provider> find(String str) throws NullPointerException{
		return providerDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Provider> find(String str, boolean active) throws NullPointerException{
		return providerDao.find(str, active);
	}
	
	@Transactional
	public void save(Request request) {
		requestDao.create(request);
	}

	@Transactional
	public void delete(Request request) {
		requestDao.delete(request);
	}

	@Transactional
	public void update(Request request) {
		requestDao.update(request);
	}

	@Transactional(readOnly = true)
	public List<Request> allRequests() {
		return requestDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Request> allRequests(Date date) {
		return requestDao.getAllRequestByDate(date);
	}

	@Transactional(readOnly = true)
	public Request findByCode(String code) {
		return requestDao.findByCode(code);
	}

	@Override
	public List<Request> selectedRequest(Request request) {
		List<Request> requisiction = new ArrayList<Request>();
		requisiction.add(request);
		return requisiction;
	}

}
