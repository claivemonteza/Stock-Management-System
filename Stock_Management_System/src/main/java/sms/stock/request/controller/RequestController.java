package sms.stock.request.controller;

import java.text.DecimalFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.WrongValueException;
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
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.util.Inventory;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.stock.StockManager;
import sms.stock.item.model.ItemRequest;
import sms.stock.provider.model.Provider;
import sms.stock.request.model.Request;
import sms.stock.request.report.RequestReportController;
import sms.util.AutoClosableController;
import sms.util.DateUtil;
import sms.util.Mathematics;
import sms.util.Messages;
import sms.util.Verifications;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see ItemRequestManager
 * @see StockManager
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */

public class RequestController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5923356666368467082L;
	@Wire
	private Combobox cbx_provider;
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
	private Textbox txtReference;
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
	private Label lbUnity;
	@Wire
	private Button btnAdd;
	
	@WireVariable
	private StockManager stockManager;
	@WireVariable
	private ItemManager itemManager;
	
	private double total;
	private double subTotal;
	private double vat;
	private int position;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		stockManager = ManagerFactory.getStockManager();
		itemManager = ManagerFactory.getItemManager();
		list();
	}

	@Listen("onSelect = #cbx_provider")
	public void selectProvider() {
		providerSelected();
		txtReference.setFocus(true);
	}

	@Listen("onOK = #cbx_provider")
	public void okProvider() {
		providerSelected();
		txtReference.setFocus(true);
	}

	@Listen("onChanged = #cbx_provider")
	public void changedProvider() {
		providerSelected();
	}

	@Listen("onOK = #txtReference")
	public void enter() {
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

	@Listen("onChanged = #cbx_product")
	public void changedProduct() {
		productSelected();
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

	@Listen("onChanged = #cbx_batch")
	public void changedBatch() {
		batchSelected();
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
			ItemRequest item = new ItemRequest();
			fillItem(item);
			if (stockManager.addItem(position, item)) {
				listOfItems();
				calculateTotal();
				cleanItens();
			} else {
				cleanItens();
				throw new WrongValueException(Labels.getLabel("item.already.been.added"));
			}
			cbx_product.setFocus(true);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), cbx_product);
		}
	}

	@Listen("onSelect = #listbox")
	public void selectPosition() {
		position = listbox.getSelectedIndex();
	}

	@Listen("onDoubleClick = #listbox")
	public void refill() {
		try {
			ItemRequest item = listbox.getSelectedItem().getValue();
			Product product = item.getBatch().getProduct();
			Batch batch = item.getBatch();
			UIHelper.setSelectedValueOnCombobox(cbx_product, product);
			fillProduct(product);
			UIHelper.setSelectedValueOnCombobox(cbx_batch, batch);
			btnAdd.setDisabled(false);
			lbPrice.setValue(String.valueOf(batch.getSalePrice()));
			spi.setValue(item.getAmount());
			lbUnity.setValue(product.getUnity().getVariant());
			calculateTotalUnit(batch);
			stockManager.removerItem(item);
			listOfItems();
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.record"), listbox);
		}
	}

	@Listen("onClick = #btnSave")
	public void save() {
		if (checkComponents()) {
			Request request = new Request();
			fillRequest(request);
			stockManager.save(request);
			stockManager.saveItens(request);
			RequestReportController.generateReport(request);
			calculateAmount();
			cleanUp();
			Messages.info_center(Labels.getLabel("messages.add"), listbox);
		}
	}

	@Listen("onClick = #btnCancel")
	public void cancel() {
		cleanUp();
		stockManager.cleanUp();
		close();
	}


	private void calculateTotalUnit(Batch batch) {
		double totalUnity = Mathematics.calculateTotalUnity(spi.getValue(), batch.getCostPrice());
		lbTotalUnit.setValue(String.valueOf(totalUnity));
	}

	private void calculateAmount() {
		Inventory.addBatchAmount();
		Inventory.updateAmountOfProduct();
	}

	private void calculateTotal() {
		subTotal = Mathematics.calculateTheSubtotal(stockManager.selectedItems());
		vat = Mathematics.calculateTotalOfVat(stockManager.selectedItems());
		total = Mathematics.calculateTotal(stockManager.selectedItems());
		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcell = (Listcell) listitem.getChildren().get(8);
		listcell.setLabel(String.valueOf(formato.format(subTotal)));
		Listitem liIva = (Listitem) lbx_total.getChildren().get(2);
		Listcell listcellIva = (Listcell) liIva.getChildren().get(8);
		listcellIva.setLabel(String.valueOf(formato.format(vat)));
		Listitem liTotal = (Listitem) lbx_total.getChildren().get(3);
		Listcell listcellTotal = (Listcell) liTotal.getChildren().get(8);
		listcellTotal.setLabel(String.valueOf(formato.format(total)));
	}

	private void list() {
		stockManager.cleanUp();
		UIHelper.BuildProductCodeCombobox(cbx_product, itemManager.allProducts(true));
		UIHelper.buildCombobox(cbx_provider, stockManager.allProviders(true));
	}


	private void listOfItems() {
		UIHelper.buildSimpleList(listbox, stockManager.selectedItems());
		cleanTotal();
	}

	private void listBatch(Product product) {
		// UIHelper.buildCombobox(cbx_lote, produtoService.allBatches(produto));
		UIHelper.BuildBatchCombobox(cbx_batch, itemManager.allBatches(product));
	}

	private void batchSelected() {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			fillBatch(batch);
			btnAdd.setDisabled(false);
		} catch (NullPointerException ex) {
			try {
				if (!cbx_batch.getText().isEmpty() || cbx_batch.getText() != "") {
					// Messages.warning_right(Labels.getLabel("select.batch"), cbx_lote);
					sendComponentsBatch();
					Executions.createComponents("item/batch/batch-add.zul", null, null);
				}
			} catch (UiException e) {
			}
		}
	}

	private void productSelected() {
		try {
			Product productSelected = cbx_product.getSelectedItem().getValue();
			fillProduct(productSelected);
		} catch (NullPointerException ex) {
			try {
				if (!cbx_product.getText().isEmpty() || cbx_product.getText() != "") {
					// Messages.warning_center(Labels.getLabel("select.product"), cbx_produto);
					sendComponentsProduct();
					Executions.createComponents("item/product/product-add.zul", null, null);
				}
			} catch (UiException e) {
			}
		}
	}

	private void providerSelected() {
		try {
			Provider provider = cbx_provider.getSelectedItem().getValue();
			fillFields(provider);
		} catch (NullPointerException ex) {
			Messages.warning_right(Labels.getLabel("select.provider"), cbx_provider);
		}
	}

	private void updateAmount() {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			calculateTotalUnit(batch);
		} catch (NullPointerException e) {
			Messages.warning_right(Labels.getLabel("select.batch"), cbx_batch);
		}
	}

	private void fillRequest(Request request) {
		Provider provider = cbx_provider.getSelectedItem().getValue();
		int x = stockManager.allRequests().size() + 1;
		request.setHour(DateUtil.getTime());
		request.setCode(DateUtil.getYear() + "/" + x);
		request.setInvoiceProvider(txtReference.getValue());
		request.setSubtotal(subTotal);
		request.setVat(vat);
		request.setTotal(total);
		request.setProvider(provider);
	}

	private void fillItem(ItemRequest item) {
		try {
			Batch batch = cbx_batch.getSelectedItem().getValue();
			if (Mathematics.availableQuantityOfProduct(item.getAmount(), batch.getProduct())) {
				item.setBatch(batch);
				item.setAmount(spi.getValue());
				item.setVatUnitTotal(Mathematics.calculateTotalUnityVat(item.getAmount(), batch));
				item.setUnitTotal(Mathematics.calculateTotalUnity(item.getAmount(), batch.getCostPrice()));
			} else {
				Messages.warning_right(Labels.getLabel("exceeded.amount"), listbox);
			}
		} catch (NullPointerException e) {
			Messages.warning_right(Labels.getLabel("select.batch"), cbx_batch);
		}
	}

	private void fillBatch(Batch batch) {
		lbPrice.setValue(String.valueOf(batch.getCostPrice()));
		spi.setValue(1);
		lbUnity.setValue(batch.getProduct().getUnity().getVariant());
		calculateTotalUnit(batch);
	}

	private void fillProduct(Product product) {
		lbName.setValue(product.getName());
		listBatch(product);
		lbVat.setValue(String.valueOf(product.getVat()));
	}

	private void fillFields(Provider provider) {
		txtNuit.setValue(provider.getNuit());
		txtAddress.setValue(provider.getAddress());
		txtCity.setValue(provider.getCity());
		txtProvince.setValue(provider.getProvince());
		txtCountry.setValue(provider.getCountry());
		txtPhone.setValue(provider.getTelephone());
		txtMobile.setValue(provider.getMobile());
		txtFax.setValue(provider.getFax());
		txtEmail.setValue(provider.getEmail());
	}

	private void cleanUp() {
		stockManager.cleanUp();
		cleanComponents();
		cleanItens();
		listOfItems();
	}

	private void cleanTotal() {
		subTotal = 0;
		vat = 0;
		total = 0;
		calculateTotal();
	}

	private void cleanComponents() {
		cbx_provider.setValue(null);
		txtNuit.setValue(null);
		txtAddress.setValue(null);
		txtCity.setValue(null);
		txtProvince.setValue(null);
		txtCountry.setValue(null);
		txtPhone.setValue(null);
		txtMobile.setValue(null);
		txtFax.setValue(null);
		txtEmail.setValue(null);
		txtReference.setValue(null);
		UIHelper.buildCombobox(cbx_provider, stockManager.allProviders(true));
	}

	private void cleanItens() {
		lbName.setValue(null);
		lbVat.setValue(null);
		cbx_batch.getItems().clear();
		cbx_batch.setValue(null);
		cbx_product.setValue(null);
		spi.setValue(1);
		lbTotalUnit.setValue(null);
		lbPrice.setValue(null);
		btnAdd.setDisabled(true);
		lbUnity.setValue(null);
		UIHelper.BuildProductCodeCombobox(cbx_product, itemManager.allProducts(true));
	}

	private boolean checkComponents() {
		if (!UIHelper.selection(cbx_provider)) {
			if (!UIHelper.field(txtReference)) {
				if (!Verifications.emptyOrNull(stockManager.selectedItems())) {
					return true;
				} else {
					Messages.warning_right(Labels.getLabel("messages.empty"), listbox);
				}
			}
		}
		return false;
	}

	public void sendComponentsProduct() {
		SessionHelper.setObject("productCode", cbx_product.getText());
		SessionHelper.setObject("label", lbName);
		SessionHelper.setObject("searchProduct", cbx_product);
	}

	public void sendComponentsBatch() {
		Product productSelected = cbx_product.getSelectedItem().getValue();
		SessionHelper.setObject("product", productSelected);
		SessionHelper.setObject("batchCode", cbx_batch.getText());
		SessionHelper.setObject("lbPrice", lbPrice);
		SessionHelper.setObject("searchBatch", cbx_batch);
	}
}
