package sms.item.batch.controller;

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
import sms.item.batch.model.Batch;
import sms.item.product.util.Inventory;
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
public class BatchListController extends AutoClosableController {

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

	@Listen("onCheck = #showInactive")
	public void showInactive() {
		list();
	}

	@Listen("onSelect = #cbx_search")
	public void onSelect() {
		search();
	}

	@Listen("onOK = #cbx_search")
	public void onOk() {
		search();
	}

	@Listen("onDoubleClick = #listbox")
	public void listbox() {
		try {
			Batch batch = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("batch", batch);
			sendComponents();
			Executions.createComponents("item/batch/batch-edit.zul", null, null);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.batch"), listbox);
		}
	}

	private void list() {
		auxiliaryBuild(itemManager.allBatches(!showInactive.isChecked()));
		UIHelper.buildCombobox(cbx_search, itemManager.allBatches());
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

	private void auxiliaryBuild(List<Batch> batches) {
		UIHelper.buildComplexList(listbox, batches);
	}

	public void sendComponents() {
		SessionHelper.setObject("listBatches", listbox);
		SessionHelper.setObject("searchBatch", cbx_search);
	}

	public void check() {
		Inventory.updateAmountOfProduct();
		list();
	}

}
