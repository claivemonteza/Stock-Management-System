package sms.item.product.controller;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
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

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.product.model.Product;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Messages;


/**
 * The class that will controller all the functionalities of the view
 * 
 * @see ItemManager
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProductListController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713209471379347892L;

	@Wire
	private Combobox cbx_search;
	@Wire
	private Listbox listbox;
	@Wire
	private Checkbox showInactive;

	@WireVariable
	private ItemManager itemManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		list();
	}

	@Listen("onClick = #btnNew")
	public void add() {
		sendComponents();
		Executions.createComponents("item/product/product-add.zul", null, null);
	}

	@Listen("onClick = #btnDelete")
	public void delete() {
		try {
			Product productSelected = listbox.getSelectedItem().getValue();
			itemManager.delete(productSelected);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			list();
		} catch (NullPointerException | IllegalArgumentException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException e2) {
			Messages.error_center(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	@Listen("onClick = #btnActiveInactive")
	public void activeInactive() {
		try {
			Product productSelected = listbox.getSelectedItem().getValue();
			productSelected.activeInactive();
			itemManager.update(productSelected);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e2) {
			Messages.warning_right(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	@Listen("onCheck = #showInactive")
	public void showInactive() {
		list();
	}

	@Listen("onSelect = #cbx_search")
	public void onSelect() {
		search();
	}

	@Listen("onOK = #cbx_search")
	public void onOK() {
		search();
	}

	@Listen("onDoubleClick = #listbox")
	public void fillSelection() {
		try {
			Product productSelected = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("product", productSelected);
			sendComponents();
			Executions.createComponents("item/product/product-edit.zul", null, null);
		} catch (NullPointerException e2) {
			Messages.warning_center(Labels.getLabel("select.product"), listbox);
		}
	}
	
	@Listen("onClick = #btnBatches")
	public void batches() {
		try {
			Product productBatches = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("productBatches", productBatches);
			SessionHelper.getMainController().createNewTab(Labels.getLabel("batches.record"),
					"item/product-batches.zul");
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), listbox);
		}
	}
	

	@Listen("onClick = #btnTransations")
	public void transactions() {
		try {
			Product productTransaction = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("productTransaction", productTransaction);
			SessionHelper.getMainController().createNewTab(Labels.getLabel("product.transaction"),
					"item/product/product-logs.zul");
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.product"), listbox);
		}
	}

	private void search() {
		try {
			Product produto = cbx_search.getSelectedItem().getValue();
			auxiliaryBuild(itemManager.selectedProduct(produto));
		} catch (NullPointerException ex) {
			List<Product> products = itemManager.find(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null && !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(products);
			} else {
				list();
			}
		}
	}

	private void list() {
		try {
			auxiliaryBuild(itemManager.allProducts(!showInactive.isChecked()));
			UIHelper.buildCombobox(cbx_search, itemManager.allProducts());
		} catch (NullPointerException e) {
			Messages.error_center(Labels.getLabel("no.records"), listbox);
		}
	}
	
	private void auxiliaryBuild(List<Product> produtos) {
		UIHelper.buildComplexList(listbox, produtos);
	}

	public void sendComponents() {
		SessionHelper.setObject("listProducts", listbox);
		SessionHelper.setObject("searchProduct", cbx_search);
	}

}
