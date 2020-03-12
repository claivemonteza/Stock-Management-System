package sms.item.product.controller;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DecimalFormat;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.report.ProductBatchReportController;
import sms.item.product.util.Inventory;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see ItemManager
 * @see Batch
 * @see Product
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProductBatchesController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8124418103879875503L;

	@Wire
	private Textbox txtCode;
	@Wire
	private Textbox txtName;
	@Wire
	private Listbox listbox;
	@Wire
	private Listbox lbx_total;
	@Wire
	private Checkbox showActive;
	@Wire
	private Checkbox showInactive;
	@Wire
	private Combobox cbx_search;

	@WireVariable
	private ItemManager itemManager;

	private Product productSelected;
	private List<Batch> batchesSelected;
	private double totalCost;
	private double totalSale;
	private double totalAmount;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		fillFields();
	}

	@Listen("onClick = #btnNew")
	public void add() {
		sendComponents();
		Executions.createComponents("item/batch/batch-add.zul", null, null);
	}

	@Listen("onClick = #btnDelete")
	public void delete() {
		try {
			Batch batch = listbox.getSelectedItem().getValue();
			itemManager.delete(batch);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			check();
		} catch (NullPointerException | IllegalArgumentException ex) {
			Messages.warning_center(Labels.getLabel("select.batch"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException e2) {
			Messages.warning_right(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	@Listen("onClick = #btnActiveInactive")
	public void activeInactive() {
		try {
			Batch batch = listbox.getSelectedItem().getValue();
			batch.activeInactive();
			itemManager.update(batch);
			check();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.batch"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e2) {
			Messages.warning_right(Labels.getLabel("batch.code.already.exist"), listbox);
		}
	}
	
	@Listen("onCheck = #showActive")
	public void active() {
		check(productSelected);
		fillTotal();
	}

	@Listen("onCheck = #showInactive")
	public void inactive() {
		check(productSelected);
		fillTotal();
	}
	
	@Listen("onSelect = #cbx_search")
	public void onSelect() {
		search();
	}

	@Listen("onOK = #cbx_search")
	public void onOk() {
		search();
	}

	@Listen("onClick = #btnPrint")
	public void print() {
		ProductBatchReportController.generateReport(productSelected, batchesSelected, totalCost, totalSale,
				totalAmount);
	}

	@Listen("onClick = #btnCancel")
	public void cancel() {
		cleanUp();
		close();
	}

	private void search() {
		try {
			Batch batch = cbx_search.getSelectedItem().getValue();
			auxiliaryBuild(itemManager.selectedBatch(batch));
		} catch (NullPointerException ex) {
			List<Batch> batches = itemManager.findBatch(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null && !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(batches);
			} else {
				list();
			}
		}
	}
	
	private void cleanUp() {
		txtCode.setValue(null);
		txtName.setValue(null);
		productSelected = null;
	}

	private void fillFields() {
		try {
			productSelected = (Product) SessionHelper.takeObject("productBatches");
			txtCode.setValue(productSelected.getCode());
			txtName.setValue(productSelected.getName());
			batchList(productSelected);
			fillTotal();
		} catch (NullPointerException e) {
		//	Messages.error_center(Labels.getLabel("messages.empty"), listbox);
		}
	}

	private void batchList(Product product) {
		try {
			check(product);
		} catch (Exception e) {
			//Messages.error_center(Labels.getLabel("messages.empty"), listbox);
		}
	}

	private void fillTotal() {
		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcell = (Listcell) listitem.getChildren().get(3);
		listcell.setLabel(String.valueOf(formato.format(totalCost)));
		Listitem liIva = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcellIva = (Listcell) liIva.getChildren().get(4);
		listcellIva.setLabel(String.valueOf(formato.format(totalSale)));
		Listitem liTotal = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcellTotal = (Listcell) liTotal.getChildren().get(5);
		listcellTotal.setLabel(String.valueOf(totalAmount));
	}

	private void check(Product product) {
		try {
			if (showInactive.isChecked() && showActive.isChecked()
					|| !showInactive.isChecked() && !showActive.isChecked()) {
				batchesSelected = itemManager.allBatches(product);
				auxiliaryBuild(batchesSelected);
				calculateTotal();
			} else {
				if (showActive.isChecked()) {
					batchesSelected = itemManager.allBatches(productSelected, showActive.isChecked());
					auxiliaryBuild(batchesSelected);
					calculateTotalActive(showActive.isChecked());
				}
				if (showInactive.isChecked()) {
					batchesSelected = itemManager.allBatches(productSelected, !showInactive.isChecked());
					auxiliaryBuild(batchesSelected);
					calculateTotalActive(!showInactive.isChecked());
				}
			}
		} catch (NullPointerException e) {
		}
	}

	public void calculateTotal() {
		totalCost = itemManager.calculateCost(productSelected);
		totalSale = itemManager.calculateSale(productSelected);
		totalAmount = itemManager.calculateAmountBatch(productSelected);
	}

	public void calculateTotalActive(Boolean active) {
		totalCost = itemManager.calculateCostOfBatchActive(productSelected, active);
		totalSale = itemManager.calculateSaleOfBatchActive(productSelected, active);
		totalAmount = itemManager.calculateAmountOfBatchActive(productSelected, active);
	}

	private void auxiliaryBuild(List<Batch> batches) {
		UIHelper.buildProductBatchListbox(listbox, batches);
	}
	
	public void check() {
		Inventory.updateAmountOfProduct();
		list();
	}
	
	private void list() {
		try {
			txtCode.setValue(productSelected.getCode());
			txtName.setValue(productSelected.getName());
			batchList(productSelected);
			fillTotal();
		} catch (NullPointerException e) {
		//	Messages.error_center(Labels.getLabel("messages.empty"), listbox);
		}
	}
	
	public void sendComponents() {
		SessionHelper.setObject("product", productSelected);
		SessionHelper.setObject("listBatches", listbox);
		SessionHelper.setObject("searchBatch", cbx_search);
	}
}
