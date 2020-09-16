package sms.stock.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.item.batch.model.Batch;
import sms.stock.item.model.ItemRequest;
import sms.stock.request.model.Request;

/**
 * <code>ItemRequestDao</code> is the class that will make the search at the database
 * at the table item request.
 * 
 * @see ItemRequest
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class ItemRequestDao extends GenericDAO<ItemRequest> {

	public ItemRequestDao() {
		super(ItemRequest.class);
	}

	/**
	 * Search all item that have batch.
	 * 
	 * @param request
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<ItemRequest> requestDetails(Request request) {
		String hql = "from ItemRequest as ir join fetch ir.request as request where request=:request";
		Query query = getSession().createQuery(hql);
		query.setParameter("request", request);
		return query.list();
	}

	
	/**
	 * SUM all amount of item that have batch
	 * 
	 * @param batch
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public int totalAmount(Batch batch) {

		/*
		 * String hql =
		 * "Select SUM(ir.quantidade) from ItemRequisicao as ir where ir.lote=:lote";
		 * Query query = getSession().createQuery(hql); query.setParameter("lote",
		 * batch); return (int) query.uniqueResult();
		 */

		int quantity = 0;
		try {
			String hql = "from ItemRequest as itemrequest where itemrequest.batch=:batch";
			Query query = getSession().createQuery(hql);
			query.setParameter("batch", batch);
			List<ItemRequest> items = query.list();
			for (ItemRequest item : items) {
				quantity = quantity + item.getAmount();
			}
			return quantity;
		} catch (NullPointerException e) {
			return quantity;
		}

	}

}
