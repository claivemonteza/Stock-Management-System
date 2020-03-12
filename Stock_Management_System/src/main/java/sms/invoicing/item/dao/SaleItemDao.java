package sms.invoicing.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.sale.model.Sale;
import sms.item.batch.model.Batch;

/**
 * <code>SaleItemDao</code> is the class that will make the search at the database
 * at the table SaleItem.
 * 
 * @see SaleItem
 * @see GenericDAO
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Repository
public class SaleItemDao extends GenericDAO<SaleItem> {

	public SaleItemDao() {
		super(SaleItem.class);
	}

	/**
	 * Search for all items that in the sale.
	 * 
	 * @param sale
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public List<SaleItem> allItems(Sale sale) {
		String hql = "from SaleItem as i join fetch i.sale as s where s=:sale";
		Query query = getSession().createQuery(hql);
		query.setParameter("sale", sale);
		return query.list();
	}

	/**
	 * Search for all items that have batch and SUM all amount of the same item.
	 * 
	 * @param batch
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public int totalAmount(Batch batch) {
		int quantity = 0;
		try {
			String consulta = " from SaleItem as i where i.batch=:batch";
			Query query = getSession().createQuery(consulta);
			query.setParameter("batch", batch);

			List<SaleItem> items = query.list();
			for (SaleItem item : items) {
				quantity = quantity + item.getAmount();
			}

			return quantity;
		} catch (NullPointerException e) {
			return quantity;
		}
		
		/*
		 * String hql =
		 * "Select SUM(iv.quantidade) from Itemvenda as iv where ir.lote=:lote";
		 * Query query = getSession().createQuery(hql); query.setParameter("lote",
		 * batch); return (int) query.uniqueResult();
		 * */
	}
}