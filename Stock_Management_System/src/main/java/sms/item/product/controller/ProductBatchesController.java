package sms.item.product.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.report.ProductBatchReportController;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;

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
}
