/**
 * 
 */
package sms.invoicing;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import sms.invoicing.cash.model.CashSale;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.payment.model.ItemPaid;
import sms.invoicing.payment.model.Paid;
import sms.invoicing.payment.model.PaidInvoice;
import sms.invoicing.payment.model.PaidPos;
import sms.invoicing.quotation.model.Quotation;
import sms.invoicing.receipt.model.Receipt;
import sms.invoicing.sale.model.Sale;
import sms.item.batch.model.Batch;

/**
 * This interface have all methods that will been use to save, update, delete
 * and search for:
 * 
 * @see Client
 * @see Paid
 * @see PaidPos
 * @see ItemPaid
 * @see Receipt
 * @see SaleItem
 * @see Sale
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public interface InvoicingManager {
	
	public void save(Client client) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;

	public void delete(Client client) throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException;

	public void update(Client client)throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException;
	
	public Client findByName(String name)throws NullPointerException;
	
	public Client findByNuit(String nuit)throws NullPointerException;
	
	public List<Client> selectedClient(Client client)throws NullPointerException;
	
	public List<Client> find(String str)throws NullPointerException;
	
	public List<Client> find(String str,boolean active)throws NullPointerException;
	
	public List<Client> allClients()throws NullPointerException;
	
	public List<Client> allClients(boolean active)throws NullPointerException;
	
//	public List<Invoice> unpaidInvoices(Client client)throws NullPointerException;

	public List<Sale> debts(Client client);

	public List<Sale> extract(Client client);
	
	public void save(Paid paid);

	public void delete(Paid paid);

	public List<Paid> allPaids();

	public void save(PaidPos paidPos);

	public void delete(PaidPos paidPos);

	public List<PaidPos> allPaidsPos();
	
	public List<PaidInvoice> allPaidsInvoices();

	public void saveItem(ItemPaid item);

	public List<CashSale> allCashSale(Paid paid);
	
	public List<Invoice> allInvoices(Paid paid);
	
	public void save(Receipt receipt);

	public void update(Receipt receipt);

	public void delete(Receipt receipt);
	
	public Receipt findByReceiptCode(String code);
	
	public List<Receipt> allReceipts(Client client);
	
	public List<Receipt> allReceipts();
	
	public void save(SaleItem item);

	public void delete(SaleItem item);

	public void update(SaleItem item);

	public List<SaleItem> allItems();

	public List<SaleItem> allSaleItems(Sale sale);

	public int totalAmount(Batch batch);
	
	public boolean addItem(int position, SaleItem item);

	public void removerItem(SaleItem item);

	public void cleanUp();
	
	public void saveItens(Sale sale);
	
	public List<SaleItem> selectedItems();
	
	public void save(Sale sale);

	public void update(Sale sale);

	public void delete(Sale sale);
	
	public Sale findByCode(String code);
	
	public List<SaleItem> allItems(Sale sale);
	
	public List<Sale> allSales(Client client);
	
	public List<Sale> allSales();
	
	public List<Quotation> allQuotation();
	
	public List<Invoice> allInvoices();
	
	public List<Invoice> allInvoices(Client client);
	
	public List<Invoice> unpaidInvoices();
	
	public List<Invoice> unpaidInvoices(Client client);
	
	public List<Invoice> allInvoicesUnpaid(Client client);
	
	public List<CashSale> allCashSale();

}
