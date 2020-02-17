/**
 * 
 */
package sms.invoicing;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sms.invoicing.cash.dao.CashSaleDao;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.client.dao.ClientDao;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.dao.InvoiceDao;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.item.dao.SaleItemDao;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.payment.dao.ItemPaidBillDao;
import sms.invoicing.payment.dao.ItemPaidDao;
import sms.invoicing.payment.dao.ItemPaidPosDao;
import sms.invoicing.payment.dao.PaidDao;
import sms.invoicing.payment.dao.PaidInvoiceDao;
import sms.invoicing.payment.dao.PaidPosDao;
import sms.invoicing.payment.model.ItemPaid;
import sms.invoicing.payment.model.Paid;
import sms.invoicing.payment.model.PaidInvoice;
import sms.invoicing.payment.model.PaidPos;
import sms.invoicing.quotation.dao.QuotationDao;
import sms.invoicing.quotation.model.Quotation;
import sms.invoicing.receipt.dao.ReceiptDao;
import sms.invoicing.receipt.model.Receipt;
import sms.invoicing.sale.dao.SaleDao;
import sms.invoicing.sale.model.Sale;
import sms.item.batch.model.Batch;

/**
 * This class implements all the methods of the
 * interface @see InvoicingManager.
 * 
 * @see ClientDao
 * @see CashSaleDao
 * @see InvoiceDao
 * @see ItemPaidBillDao
 * @see ItemPaidDao
 * @see ItemPaidPosDao
 * @see PaidDao
 * @see PaidInvoiceDao
 * @see PaidPosDao
 * @see QuotationDao
 * @see ReceiptDao
 * @see SaleDao
 * @see SaleItemDao
 * 
 * 
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Service("InvoicingManager")
public class InvoicingManagerImpl implements InvoicingManager {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private CashSaleDao cashSaleDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private ItemPaidBillDao itemPaidBillDao;
	@Autowired
	private ItemPaidDao itemPaidDao;
	@Autowired
	private ItemPaidPosDao itemPaidPosDao;
	@Autowired
	private PaidDao paidDao;
	@Autowired
	private PaidInvoiceDao paidInvoiceDao;
	@Autowired
	private PaidPosDao paidPosDao;
	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private ReceiptDao receiptDao;
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private SaleItemDao saleItemDao;

	
	private List<SaleItem> items = new ArrayList<SaleItem>();
	
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public void setCashSaleDao(CashSaleDao cashSaleDao) {
		this.cashSaleDao = cashSaleDao;
	}
	
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
	
	public void setItemPaidDao(ItemPaidDao itemPaidDao) {
		this.itemPaidDao = itemPaidDao;
	}
	
	public void setItemPaidBillDao(ItemPaidBillDao itemPaidBillDao) {
		this.itemPaidBillDao = itemPaidBillDao;
	}
	
	public void setItemPaidPosDao(ItemPaidPosDao itemPaidPosDao) {
		this.itemPaidPosDao = itemPaidPosDao;
	}
	
	public void setPaidDao(PaidDao paidDao) {
		this.paidDao = paidDao;
	}
	
	public void setPaidPosDao(PaidPosDao paidPosDao) {
		this.paidPosDao = paidPosDao;
	}
	
	public void setPaidInvoiceDao(PaidInvoiceDao paidInvoiceDao) {
		this.paidInvoiceDao = paidInvoiceDao;
	}
	
	public void setQuotationDao(QuotationDao quotationDao){
		this.quotationDao = quotationDao;
	}
	
	public void setReceiptDao(ReceiptDao receiptDao) {
		this.receiptDao = receiptDao;
	}
	
	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}
	
	public void setSaleItemDao(SaleItemDao saleItemDao){
		this.saleItemDao = saleItemDao;
	}
	
	
	@Transactional
	public void save(Client cliente) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		clientDao.create(cliente);
	}

	@Transactional
	public void delete(Client cliente)  throws IllegalArgumentException, DataIntegrityViolationException,
	SQLIntegrityConstraintViolationException, BatchUpdateException{
		clientDao.delete(cliente);
	}

	@Transactional
	public void update(Client cliente) throws DataIntegrityViolationException, ConstraintViolationException,
	SQLIntegrityConstraintViolationException{
		clientDao.update(cliente);
	}

	@Transactional(readOnly = true)
	public Client findByName(String designacao)throws NullPointerException {
		return clientDao.findByName(designacao);
	}

	@Transactional(readOnly = true)
	public Client findByNuit(String nuit)throws NullPointerException {
		return clientDao.findByNuit(nuit);
	}

	@Transactional(readOnly = true)
	public List<Client> allClients() throws NullPointerException{
		return clientDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Client> allClients(boolean active)throws NullPointerException {
		return clientDao.allClients(active);
	}
/*
	@Transactional(readOnly = true)
	public List<Invoice> unpaidInvoices(Client cliente)throws NullPointerException {
		return clientDao.unpaidInvoices(cliente);
	}
	*/

	@Override
	public List<Client> selectedClient(Client client) throws NullPointerException{
		List<Client> clients = new ArrayList<Client>();
		clients.add(client);
		return clients;
	}

	@Transactional(readOnly = true)
	public List<Client> find(String str) throws NullPointerException{
		return clientDao.find(str);
	}

	@Transactional(readOnly = true)
	public List<Client> find(String str, boolean active) throws NullPointerException{
		return clientDao.find(str, active);
	}

	@Transactional(readOnly = true)
	public List<Sale> debts(Client cliente) {
		return null;
	}

	@Transactional(readOnly = true)
	public List<Sale> extract(Client cliente) {
		return null;
	}
	
	@Transactional
	public void save(Paid paid) {
		paidDao.create(paid);
	}

	@Transactional
	public void delete(Paid paid) {
		paidDao.delete(paid);
	}

	@Transactional(readOnly = true)
	public List<Paid> allPaids() {
		return paidDao.getAll();
	}

	@Transactional
	public void save(PaidPos paidPos) {
		paidPosDao.create(paidPos);		
	}

	@Transactional
	public void delete(PaidPos paidPos) {
		paidPosDao.delete(paidPos);		
	}

	@Transactional(readOnly = true)
	public List<PaidPos> allPaidsPos() {
		return paidPosDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<PaidInvoice> allPaidsInvoices() {
		return paidInvoiceDao.getAll();
	}

	@Transactional
	public void saveItem(ItemPaid item) {
		itemPaidDao.create(item);
	}

	@Transactional(readOnly = true)
	public List<CashSale> allCashSale(Paid paid) {
		return itemPaidPosDao.allCashSale(paid);
	}
	
	@Transactional(readOnly = true)
	public List<Invoice> allInvoices(Paid paid) {
		return itemPaidBillDao.allInvoices(paid);
	}
	
	@Transactional
	public void save(Receipt receipt) {
		receiptDao.create(receipt);
	}

	@Transactional
	public void update(Receipt receipt) {
		receiptDao.update(receipt);
	}

	@Transactional
	public void delete(Receipt receipt) {
		receiptDao.delete(receipt);
	}

	@Transactional(readOnly = true)
	public Receipt findByReceiptCode(String code) {
		return receiptDao.findByReference(code);
	}

	@Transactional(readOnly = true)
	public List<Receipt> allReceipts(Client client) {
		return receiptDao.allReceipts(client);
	}

	@Transactional(readOnly = true)
	public List<Receipt> allReceipts() {
		return receiptDao.getAll();
	}
	
	@Transactional
	public void save(SaleItem i) {
		saleItemDao.create(i);
	}

	@Transactional
	public void delete(SaleItem i) {
		saleItemDao.delete(i);
	}

	@Transactional
	public void update(SaleItem i) {
		saleItemDao.update(i);
	}

	@Transactional(readOnly = true)
	public List<SaleItem> allItems() {
		return saleItemDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<SaleItem> allSaleItems(Sale venda) {
		return saleItemDao.allItems(venda);
	}

	@Transactional
	public int totalAmount(Batch lote) {
		return saleItemDao.totalAmount(lote);
	}

	@Override
	public boolean addItem(int posicao, SaleItem item) {
		if (!items.contains(item)) {
			if (posicao == 0) {
				items.add(posicao, item);
			} else {
				items.add(item);
			}
			return true;
		}
		return false;
	}

	@Override
	public void removerItem(SaleItem item) {
		items.remove(item);
	}

	@Override
	public void cleanUp() {
		items.clear();
	}

	@Transactional
	public void saveItens(Sale sale) {
		for (SaleItem item : items) {
			item.setSale(sale);
			save(item);
			//pm.actualizarQuantidadeLote(item);
		}
	}

	@Override
	public List<SaleItem> selectedItems() {
		return items;
	}
	
	@Transactional
	public void save(Sale sale) {
		saleDao.create(sale);
	}

	@Transactional
	public void update(Sale sale) {
		saleDao.update(sale);
	}

	@Transactional
	public void delete(Sale sale) {
		saleDao.delete(sale);		
	}
	
	@Transactional(readOnly = true)
	public Sale findByCode(String code) {
		return saleDao.findByCode(code);
	}
	
	@Transactional(readOnly = true)
	public List<SaleItem> allItems(Sale sale) {
		return saleDao.allItems(sale);
	}
	
	@Transactional(readOnly = true)
	public List<Sale> allSales(Client client) {
		return saleDao.allSales(client);
	}

	@Transactional(readOnly = true)
	public List<Sale> allSales() {
		return saleDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Quotation> allQuotation() {
		return quotationDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Invoice> allInvoices() {
		return invoiceDao.getAll();
	}

	@Transactional(readOnly = true)
	public List<Invoice> allInvoices(Client client) {
		return invoiceDao.allInvoices(client);
	}

	@Transactional(readOnly = true)
	public List<Invoice> unpaidInvoices() {
		return invoiceDao.unpaidInvoices();
	}

	@Transactional(readOnly = true)
	public List<Invoice> unpaidInvoices(Client client) {
		return invoiceDao.unpaidInvoices(client);
	}

	@Transactional(readOnly = true)
	public List<Invoice> allInvoicesUnpaid(Client client) {
		return invoiceDao.allInvoicesUnpaid(client);
	}
	
	@Transactional(readOnly = true)
	public List<CashSale> allCashSale() {
		return cashSaleDao.getAll();
	}

}
