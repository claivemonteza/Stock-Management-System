package sms.item.batch.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.DateUtil;
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
public class BatchAddController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713209471379347892L;

	@Wire
	private Window winNewBatch;
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

	private Product product;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		UIHelper.BuildProductCombobox(cbx_product, itemManager.allProducts(true));
		fillFields();
	}

	public void onClick$btnSave(Event e) {
		try {
			Batch batch = new Batch();
			if (checkComponents()) {
				details(batch);
				itemManager.save(batch);
				Messages.info_center(Labels.getLabel("messages.add"), winNewBatch);
				// Inventario.quantidadeProduto();
				cleanUp();
				list(batch);
				 list2();
				winNewBatch.detach();
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_right(Labels.getLabel("batch.code.already.exist"), txtCode);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
		winNewBatch.detach();
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
		dbManufacture.setValue(null);
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

	private void fillFields() {
		try {
			String code = (String) SessionHelper.takeObject("batchCode");
			product = (Product) SessionHelper.takeObject("product");
			txtCode.setValue(code);
			UIHelper.setSelectedValueOnCombobox(cbx_product, product);
		} catch (NullPointerException e) {
		}
	}

	
	private void list2() {
		Listbox listbox = (Listbox) Sessions.getCurrent().getAttribute("listBatches");
		try {
			UIHelper.buildProductBatchListbox(listbox, itemManager.allBatches(product));
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.records"), listbox);
		}
		
	}
	
	private void list(Batch batch) {
		Combobox combobox = (Combobox) SessionHelper.takeObject("searchBatch");
		Label label = (Label) SessionHelper.takeObject("lbPrice");
		try {
			UIHelper.BuildBatchCombobox(combobox, itemManager.allBatches(true));
			UIHelper.setSelectedValueOnCombobox(combobox, batch);
			label.setValue(String.valueOf(batch.getCostPrice()));
		} catch (NullPointerException e) {
		}
	}
}
