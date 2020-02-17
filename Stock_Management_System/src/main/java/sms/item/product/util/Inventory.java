package sms.item.product.util;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.stock.StockManager;


/**
 * This class will make the inventory of the stock 
 * 
 * @see ItemManager
 * @see StockManager
 * @see InvoicingManager
 * 
 * @author Claive Monteza
 *
 */
public class Inventory {

	private static ItemManager itemManager = ManagerFactory.getItemManager();
	private static StockManager stockManager = ManagerFactory.getStockManager();
	private static InvoicingManager invoicingManager = ManagerFactory.getInvoicingManager();

	public static void updateAmountOfProduct() {
		try {
			for (Product product : itemManager.allProducts()) {
				int amount = itemManager.calculateAmountBatch(product);/*productManager.calcularQuantidade(produto);*/
				product.setAmount(amount);
				itemManager.update(product);
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null,Labels.getLabel("batch.code.already.exist"),
					"Alert!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void addBatchAmount() {
		try {
			for (Batch batch : itemManager.allBatches(true)) {
				int amount = stockManager.totalAmount(batch);
				batch.setAmount(amount);
				itemManager.update(batch);
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null,Labels.getLabel("batch.code.already.exist"),
					"Alert!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void updateAmountOfBatch() {
		try {
			for (Batch batch : itemManager.allBatches(true)) {
				int amount = stockManager.totalAmount(batch) - invoicingManager.totalAmount(batch);
				batch.setAmount(amount);
				itemManager.update(batch);
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null,Labels.getLabel("batch.code.already.exist"),
					"Alert!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
