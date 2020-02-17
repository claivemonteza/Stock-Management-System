package sms.invoicing.sale.controller;

import java.text.DecimalFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.invoice.report.InvoiceReportController;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.quotation.model.Quotation;
import sms.invoicing.quotation.report.QuotationReportController;
import sms.invoicing.sale.model.Sale;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.DateUtil;
import sms.util.Mathematics;
import sms.util.Messages;
import sms.util.Verifications;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see InvoicingManager
 * @see ItemManager
 * @see Client
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class SaleController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8842829796077860138L;
	@Wire
	private Combobox cbx_client;
	@Wire
	private Combobox cbx_product;
	@Wire
	private Combobox cbx_batch;
	@Wire
	private Textbox txtNuit;
	@Wire
	private Textbox txtAddress;
	@Wire
	private Textbox txtCity;
	@Wire
	private Textbox txtProvince;
	@Wire
	private Textbox txtCountry;
	@Wire
	private Textbox txtMobile;
	@Wire
	private Textbox txtPhone;
	@Wire
	private Textbox txtFax;
	@Wire
	private Textbox txtEmail;
	@Wire
	private Listbox listbox;
	@Wire
	private Listbox lbx_total;
	@Wire
	private Spinner spi;
	@Wire
	private Label lbTotalUnit;
	@Wire
	private Label lbVat;
	@Wire
	private Label lbPrice;
	@Wire
	private Label lbName;
	@Wire
	private Button btnAdd;
	
	@WireVariable
	private InvoicingManager invoicingManager;
	@WireVariable
	private ItemManager itemManager;
	
	private Client selectedClient;
	private double total;
	private double subTotal;
	private double vat;
	private int position;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
		itemManager = ManagerFactory.getItemManager();
		cleanUp();
		list();
	}

	@Listen("onSelect = #cbx_client")
	public void selectClient() {
		clientSelected();
		cbx_product.setFocus(true);
	}

	@Listen("onOK = #cbx_client")
	public void okClient() {
		clientSelected();
		cbx_product.setFocus(true);
	}

	@Listen("onSelect = #cbx_product")
	public void selectProduct() {
		productSelected();
		cbx_batch.setFocus(true);
	}

	@Listen("onOK = #cbx_product")
	public void okProduct() {
		productSelected();
		cbx_batch.setFocus(true);
	}

	@Listen("onChange = #cbx_product")
	public void changeProduct() {
		productSelected();
		cbx_batch.setFocus(true);
	}

	@Listen("onSelect = #cbx_batch")
	public void selectBatch() {
		batchSelected();
		spi.setFocus(true);
	}

	@Listen("onOK = #cbx_batch")
	public void oKBatch() {
		batchSelected();
		spi.setFocus(true);
	}

	@Listen("onChange = #spi")
	public void checkChangeAmount() {
		updateAmount();
	}

	@Listen("onChanging = #spi")
	public void checkAmountChanging() {
		updateAmount();
	}

	@Listen("onOK = #spi")
	public void checkAmountOK() {
		updateAmount();
		btnAdd.setFocus(true);
	}

	@Listen("onClick = #btnAdd")
	public void addItem() {
		try {
			SaleItem item = new SaleItem();
			fillItem(item);
			if (Mathematics.availableBatchQuantity(item.getAmount(), item.getBatch())) {
				if (invoicingManager.addItem(position, item)) {
					listOfItems();
					calcularTotal();
					cleanItem();
				} else {
					cleanItem();
					Messages.warning_center(Labels.getLabel("item.already.been.added"), listbox);
				}
			} else {
				Messages.warning_right(Labels.getLabel("exceeded.amount"), listbox);
			}
			cbx_product.setFocus(true);
		} catch (NullPointerException ex) {
			Messages.warning_center("select.product", cbx_product);
		}
	}

	@Listen("onSelect = #listbox")
	public void selectPosition() {
		position = listbox.getSelectedIndex();
	}

	@Listen("onDoubleClick = #listbox")
	public void refill() {
		try {
			SaleItem item = listbox.getSelectedItem().getValue();
			Product product = item.getBatch().getProduct();
			Batch batch = item.getBatch();
			UIHelper.setSelectedValueOnCombobox(cbx_product, product);
			fillProduct(product);
			UIHelper.setSelectedValueOnCombobox(cbx_batch, batch);
			btnAdd.setDisabled(false);
			lbPrice.setValue(String.valueOf(batch.getSalePrice()));
			spi.setValue(item.getAmount());
			calcularTotalUnitario(batch);
			invoicingManager.removerItem(item);
			listOfItems();
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.record"), listbox);
		}
	}

	@Listen("onClick = #btnQuotation")
	public void quotation() {
		if (checkComponents()) {
			Quotation cotacao = new Quotation();
			cotacao.setCode(DateUtil.getYear() + "/" + (invoicingManager.allQuotation().size() + 1));
			fillTheSale(cotacao);
			cotacao.setToPay(0);
			cotacao.setDueDate(DateUtil.calendaryConvert(7));
			invoicingManager.save(cotacao);
			invoicingManager.saveItens(cotacao);
			QuotationReportController.generateReport(cotacao);
			cleanUp();
			Messages.info_center(Labels.getLabel("messages.add"), listbox);
		}
	}

	@Listen("onClick = #btnInvoice")
	public void invoice() {
		if (checkComponents()) {
			Invoice invoice = new Invoice();
			invoice.setCode(DateUtil.getYear() + "/" + (invoicingManager.allInvoices().size() + 1));
			fillTheSale(invoice);
			invoicingManager.save(invoice);
			invoicingManager.saveItens(invoice);
			InvoiceReportController.generateReport(invoice);
			Mathematics.calculateQuantity();
			cleanUp();
			Messages.info_center(Labels.getLabel("messages.add"), listbox);
		}
	}

	@Listen("onClick = #btnCashSale")
	public void cashSale() {
		if (checkComponents()) {
			CashSale vd = new CashSale();
			vd.setCode(DateUtil.getYear() + "/" + (invoicingManager.allCashSale().size() + 1));
			fillTheSale(vd);
			SessionHelper.setObject("vd", vd);
			Executions.createComponents("invoicing/payment.zul", null, null);
		}
	}

	@Listen("onClick = #btnNew")
	public void add() {
		cleanUp();
	}

	@Listen("onClick = #btnCancel")
	public void cancel() {
		cleanUp();
		close();
	}
	
	private boolean checkComponents() {
		if (!UIHelper.selection(cbx_client)) {
			if (!Verifications.emptyOrNull(invoicingManager.selectedItems())) {
				return true;
			} else {
				Messages.warning_right(Labels.getLabel("messages.empty"), listbox);
			}
		}
		return false;
	}

	private void fillTheSale(Sale sale) {
		DecimalFormat formato = new DecimalFormat("0.00");
		sale.setHour(DateUtil.getTime());
		sale.setYear(DateUtil.getYear());
		sale.setSubTotal(subTotal);
		sale.setTotalVat(Double.parseDouble(formato.format(vat)));
		sale.setTotal(total);
		sale.setClient(selectedClient);
		sale.setToPay(sale.getTotal());
		sale.setDueDate(DateUtil.calendaryConvert(30));
	}

	private void batchSelected() {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			fillBatch(batch);
			btnAdd.setDisabled(false);
		} catch (NullPointerException ex) {
			Messages.warning_right(Labels.getLabel("select.product"), cbx_product);
			Messages.warning_right(Labels.getLabel("select.batch"), cbx_batch);
		}
	}

	private void productSelected() {
		try {
			Product produtoSelecionado = cbx_product.getSelectedItem().getValue();
			fillProduct(produtoSelecionado);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), cbx_product);
		}
	}

	private void clientSelected() {
		try {
			selectedClient = cbx_client.getSelectedItem().getValue();
			fillClient(selectedClient);
		} catch (NullPointerException ex) {
			Messages.warning_right(Labels.getLabel("select.client"), cbx_client);
		}
	}

	private void updateAmount() {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			calcularTotalUnitario(batch);
		} catch (NullPointerException e) {
			Messages.warning_right(Labels.getLabel("select.batch"), cbx_batch);
		}
	}

	private void list() {
		listOfProducts();
		listOfClients();
	}

	private void listOfClients() {
		UIHelper.buildCombobox(cbx_client, invoicingManager.allClients(true));
	}

	private void listOfItems() {
		UIHelper.buildSimpleList(listbox, invoicingManager.selectedItems());
		cleanUpTotal();
	}

	private void listOfBatches(Product product) {
		UIHelper.BuildBatchCombobox(cbx_batch, itemManager.allBatches(product));
	}

	private void listOfProducts() {
		UIHelper.buildCombobox(cbx_product, itemManager.allProducts(true));
	}

	private void calcularTotal() {

		subTotal = Mathematics.calculateTheSubtotal(invoicingManager.selectedItems());
		vat = Mathematics.calculateTotalOfVat(invoicingManager.selectedItems());
		total = Mathematics.calculateTotal(invoicingManager.selectedItems());

		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcell = (Listcell) listitem.getChildren().get(6);
		listcell.setLabel(String.valueOf(formato.format(subTotal)));
		Listitem liIva = (Listitem) lbx_total.getChildren().get(2);
		Listcell listcellIva = (Listcell) liIva.getChildren().get(6);
		listcellIva.setLabel(String.valueOf(formato.format(vat)));
		Listitem liTotal = (Listitem) lbx_total.getChildren().get(3);
		Listcell listcellTotal = (Listcell) liTotal.getChildren().get(6);
		listcellTotal.setLabel(String.valueOf(formato.format(total)));
	}

	private void calcularTotalUnitario(Batch batch) {
		double totalUnit = Mathematics.calculateTotalUnity(spi.getValue(), batch.getSalePrice());
		lbTotalUnit.setValue(String.valueOf(totalUnit));
	}

	private void fillItem(SaleItem item) {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			item.setBatch(batch);
			item.setAmount(spi.getValue());
			item.setVatUnitTotal(Mathematics.calculateTotalUnityVat(item.getAmount(), batch));
			item.setUnitTotal(Mathematics.calculateTotalUnity(item.getAmount(), batch.getSalePrice()));

		} catch (NullPointerException e) {
			Messages.warning_right(Labels.getLabel("select.batch"), cbx_batch);
		}
	}

	private void fillBatch(Batch batch) {
		lbPrice.setValue(String.valueOf(batch.getSalePrice()));
		spi.setValue(1);
		calcularTotalUnitario(batch);
	}

	private void fillProduct(Product product) {
		lbName.setValue(product.getName());
		listOfBatches(product);
		lbVat.setValue(String.valueOf(product.getVat()));
	}

	private void fillClient(Client client) {
		cbx_client.setText(client.getName());
		txtNuit.setValue(client.getNuit());
		txtAddress.setValue(client.getAddress());
		txtCity.setValue(client.getCity());
		txtCountry.setValue(client.getCountry());
		txtPhone.setValue(client.getTelephone());
		txtMobile.setValue(client.getMobile());
		txtFax.setValue(client.getFax());
		txtEmail.setValue(client.getEmail());
	}

	private void cleanUp() {
		invoicingManager.cleanUp();
		cleanFields();
		cleanItem();
		listOfItems();
	}

	private void cleanUpTotal() {
		subTotal = 0;
		vat = 0;
		total = 0;
		calcularTotal();
	}

	private void cleanFields() {
		cbx_client.setValue(null);
		txtNuit.setValue(null);
		txtAddress.setValue(null);
		txtCity.setValue(null);
		txtProvince.setValue(null);
		txtCountry.setValue(null);
		txtPhone.setValue(null);
		txtMobile.setValue(null);
		txtFax.setValue(null);
		txtEmail.setValue(null);
		listOfClients();
	}

	private void cleanItem() {
		lbName.setValue(null);
		lbVat.setValue(null);
		cbx_batch.getItems().clear();
		cbx_batch.setValue(null);
		cbx_product.setValue(null);
		listOfProducts();
		spi.setValue(1);
		lbTotalUnit.setValue(null);
		lbPrice.setValue(null);
		btnAdd.setDisabled(true);
	}
}
