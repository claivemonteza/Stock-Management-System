package sms.item.batch.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.util.Inventory;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.DateUtil;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see ItemManager
 * @see Batch
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class BatchEditController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713209471379347892L;

	@Wire
	private Window panel;
	@Wire
	private Textbox txtCode;
	@Wire
	private Combobox cbx_product;
	@Wire
	private Doublebox dbSale;
	@Wire
	private Doublebox dbCost;
	@Wire
	private Intbox iAmount;
	@Wire
	private Datebox dbExpiration;
	@Wire
	private Datebox dbManufacture;

	@WireVariable
	private ItemManager itemManager;

	private Batch batchSelected;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		UIHelper.BuildProductCombobox(cbx_product, itemManager.allProducts(true));
		fillFields();
	}

	/**
	 * On click the button btnSearch will open the panel where are list
	 * of product to select. 
	 * */
	public void onClick$btnSearch(Event e) {
		SessionHelper.setObject("component", cbx_product);
		Executions.createComponents("produto/search-produto.zul", null, null);
	}

	/**
	 * will update the batch and will check 
	 * */
	public void onClick$btnSave(Event e) {
		try {
			if (checkComponents()) {
				details(batchSelected);
				itemManager.update(batchSelected);
				Messages.info_center(Labels.getLabel("messages.update"), panel);
				Inventory.addBatchAmount();
				Inventory.updateAmountOfProduct();
				cleanUp();
				list();
			}
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.batch"), panel);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e2) {
			Messages.warning_right(Labels.getLabel("batch.code.already.exist"), txtCode);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
		panel.detach();
	}


	private void fillFields() {
		batchSelected = (Batch) SessionHelper.takeObject("batch");
		txtCode.setValue(batchSelected.getCode());
		UIHelper.setSelectedValueOnCombobox(cbx_product, batchSelected.getProduct());
		dbExpiration.setValue(batchSelected.getExpirationDate());
		dbManufacture.setValue(batchSelected.getDateManufacturing());
		dbCost.setValue(batchSelected.getCostPrice());
		dbSale.setValue(batchSelected.getSalePrice());
		iAmount.setValue(batchSelected.getAmount());
	}

	private void details(Batch batch) {
		Product product = cbx_product.getSelectedItem().getValue();
		batch.setCode(txtCode.getValue());
		batch.setExpirationDate(DateUtil.parse(dbExpiration.getValue()));
		batch.setDateManufacturing(DateUtil.parse(dbManufacture.getValue()));
		batch.setCostPrice(dbCost.getValue());
		batch.setSalePrice(dbSale.getValue());
		batch.setAmount(iAmount.getValue());
		batch.setProduct(product);
	}

	private void cleanUp() {
		cbx_product.setValue(null);
		txtCode.setValue(null);
		dbSale.setValue(null);
		dbCost.setValue(null);
		iAmount.setValue(0);
		dbExpiration.setValue(null);
		batchSelected = null;
	}

	private boolean checkComponents() {
		if (!UIHelper.field(txtCode)) {
			if (!UIHelper.field(dbCost)) {
				if (!UIHelper.field(dbSale)) {
					if (!UIHelper.field(iAmount)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void list() {
		Listbox listbox = (Listbox) Sessions.getCurrent().getAttribute("listBatches");
		Combobox combobox = (Combobox) Sessions.getCurrent().getAttribute("searchBatch");
		try {
			UIHelper.buildComplexList(listbox, itemManager.allBatches(true));
			UIHelper.buildCombobox(combobox, itemManager.allBatches());
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.records"), listbox);
		}
	}
}
