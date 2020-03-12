package sms.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.payment.model.ItemPaidBill;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.util.Inventory;
import sms.Item;

/**
 * <code>Mathematics</code> will do all mathematical calculations.
 *
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public class Mathematics {

	/**
	 * This method verifies if the batch has the amount available to subtract.
	 * 
	 * @param amount
	 * @param batch
	 * @return
	 */
	public static boolean availableBatchQuantity(int amount, Batch batch) {
		if ((batch.getAmount() - amount) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method verifies if the product reached the max quantity.
	 * 
	 * @param amount
	 * @param product
	 * @return
	 */
	public static boolean availableQuantityOfProduct(int amount, Product product) {
		if (product.getAmount() + amount <= product.getAmountMax()) {
			return true;
		}
		return false;
	}

	/**
	 * will calculate the price to sale product.
	 * 
	 * @param cost
	 * @param percentage
	 * @return
	 */
	public static double calculcatePriceForSale(double cost, double percentage) {
		/* cost*((percentage/100)+1) */
		return cost + (cost * (percentage / 10));
	}

	/**
	 * will calculate the percentage for sale
	 * 
	 * @param price
	 * @param cost  return
	 */
	public static double calculcatePercentage(double price, double cost) {
		DecimalFormat formato = new DecimalFormat("0.0");
		/* ((price/cost)-1)*100 (price / cost) * 10 */
		return Double.parseDouble(formato.format(((price / cost) - 1) * 10));
	}

	/**
	 * Will calculate the total unit vat
	 * 
	 * @param quantity
	 * @param batch    return
	 */
	public static double calculateTotalUnityVat(int quantity, Batch batch) {
		return ((batch.getCostPrice() * batch.getProduct().getVat()) * quantity);
	}

	/**
	 * will calculate the total unity of the item
	 * 
	 * @param quantity
	 * @param price
	 * @return
	 */
	public static double calculateTotalUnity(int quantity, double price) {
		return price * quantity;
	}

	/**
	 * Will Get a list of items and sum all subTotal of the items.
	 * 
	 * @param items return
	 */
	public static double calculateTheSubtotal(List<?> items) {
		double subtotal = 0.0;
		for (Object item : items) {
			subtotal += ((Item) item).getUnitTotal();
		}
		return subtotal;
	}

	/**
	 * will get a list of items and sum all total vat of the items.
	 * 
	 * @param items return
	 */
	public static double calculateTotalOfVat(List<?> items) {
		double iva = 0.0;
		for (Object item : items) {
			iva += ((Item) item).getVatUnitTotal();
		}
		return iva;
	}

	/**
	 * will get a list of items and calculate the total.
	 * 
	 * @param items return
	 */
	public static double calculateTotal(List<?> items) {
		return calculateTheSubtotal(items) + calculateTotalOfVat(items);
	}

	/**
	 * will get a list of invoices and sum all total.
	 * 
	 * @param items return
	 */
	public static double calculateTotalToPay(List<ItemPaidBill> items) {
		double total = 0.00;
		for (ItemPaidBill item : items) {
			if (item.getAmount() == 0) {
				// total = total + Mathematics.unpaid(invoice.getTotal(), invoice.getPaid());
				total += item.getInvoice().getToPay();
			} else {
				total += item.getAmount();
			}
		}
		return total;
	}

	/**
	 * will get a list of invoices and sum the total
	 * 
	 * @param invoices
	 * @return
	 */
	public static double calculateTotalOfAllInvoiceOfCustomer(Set<Invoice> invoices) {
		double total = 0.00;
		for (Invoice invoice : invoices) {
			total += invoice.getTotal();
		}
		return total;
	}

	/**
	 * will subtract what is paid out and check what is missing to pay.
	 * 
	 * @param toPaid
	 * @param paidOut
	 * @return
	 */
	public static double unpaid(double toPaid, double paidOut) {
		double unpaid = toPaid - paidOut;
		if (unpaid < 0) {
			unpaid = 0;
		}
		return unpaid;
	}

	/**
	 * will subtract the paid and toPaid
	 * 
	 * @param paid
	 * @param toPaid
	 * @return
	 * */
	public static double change(double paid, double toPaid) {
		return paid - toPaid;
	}

	/*
	 * public static double calculcarValorPago(List<Recibo> recibos) { double
	 * valorpago = 0; for (Recibo recibo : recibos) { valorpago = valorpago +
	 * recibo.getValorPago(); } return valorpago; }
	 * 
	 * public static List<Recibo> getRecibos(){
	 * 
	 * 
	 * return null; }
	 */

	
	/**
	 * will calculate the amount of batch and product.
	 * */
	public static void calculateQuantity() {
		Inventory.updateAmountOfBatch();
		Inventory.updateAmountOfProduct();
	}
}
