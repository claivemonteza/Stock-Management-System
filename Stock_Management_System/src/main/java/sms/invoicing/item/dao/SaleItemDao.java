package sms.invoicing.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import sms.GenericDAO;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.sale.model.Sale;
import sms.item.batch.model.Batch;

//TODO Create documentation

@Repository
public class SaleItemDao extends GenericDAO<SaleItem> {

	public SaleItemDao() {
		super(SaleItem.class);
	}

	@SuppressWarnings("unchecked")
	public List<SaleItem> allItems(Sale sale) {
		String hql = "from SaleItem as i join fetch i.sale as s where s=:sale";
		Query query = getSession().createQuery(hql);
		query.setParameter("sale", sale);
		return query.list();
	}

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