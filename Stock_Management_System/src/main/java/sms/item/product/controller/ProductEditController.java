package sms.item.product.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.product.model.Product;
import sms.item.product.model.Unity;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see ItemManager
 * @see Product
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProductEditController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713209471379347892L;

	@Wire
	private Window productModify;
	@Wire
	private Textbox txtCode;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtBrand;
	@Wire
	private Combobox cbx_search;
	@Wire
	private Combobox cbxCategory;
	@Wire
	private Combobox cbUnity;
	@Wire
	private Listbox listbox;
	@Wire
	private Button btnActiveInactive;
	@Wire
	private Checkbox showInactive;
	@Wire
	private Checkbox isento;
	@Wire
	private Groupbox gbVat;
	@Wire
	private Doublebox dbVat;
	@Wire
	private Intbox iAmount;
	@Wire
	private Intbox iMax;
	@Wire
	private Intbox iMin;

	@WireVariable
	private ItemManager itemManager;

	private Product productSelected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		UIHelper.buildUnityCombobox(cbUnity);
		fillFields();
	}

	public void onCheck$isento(Event e) {
		if (isento.isChecked()) {
			gbVat.setOpen(false);
		} else {
			gbVat.setOpen(true);
		}
	}

	public void onOpen$gbVat(Event e) {
		if (gbVat.isOpen()) {
			isento.setChecked(false);
		} else {
			isento.setChecked(true);
		}
	}

	public void onClick$btnSave(Event e) {
		try {
			if (checkComponents()) {
				details(productSelected);
				itemManager.update(productSelected);
				Messages.info_center(Labels.getLabel("messages.update"), listbox);
				cleanUp();
				UIHelper.refreshListBox("listProducts", "searchProduct", itemManager.allProducts(true),
						itemManager.allProducts());
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException e2) {
			Messages.warning_right(Labels.getLabel("product.already.exist"), txtName);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
		productSelected = null;
		productModify.detach();
	}

	private void fillFields() {
		productSelected = (Product) SessionHelper.takeObject("product");
		txtCode.setValue(productSelected.getCode());
		txtName.setValue(productSelected.getName());
		txtBrand.setValue(productSelected.getBrand());
		iAmount.setValue(productSelected.getAmount());
		iMax.setValue(productSelected.getAmountMax());
		iMin.setValue(productSelected.getAmountMin());
		checkVat(productSelected);
		UIHelper.buildComboboxAlt(cbxCategory, itemManager.allCategories());
		UIHelper.setSelectedValueOnCombobox(cbxCategory, productSelected.getCategory());
		UIHelper.setSelectedValueOnCombobox(cbUnity, productSelected.getUnity());

	}

	private void details(Product product) {
		product.setName(txtName.getValue());
		product.setBrand(txtBrand.getValue());
		product.setFreeVat(isento.isChecked());
		fillVat(product);
		Unity unity = cbUnity.getSelectedItem().getValue();
		product.setUnity(unity);
		product.setCategory(cbxCategory.getText());
		amount(product);
	}

	private void cleanUp() {
		isento.setChecked(false);
		dbVat.setValue(null);
		txtCode.setValue(null);
		txtName.setValue(null);
		txtBrand.setValue(null);
		iAmount.setValue(0);
		iMax.setValue(null);
		iMin.setValue(null);
		cbxCategory.setText(null);
		listUnity();
	}

	private void listUnity() {
		UIHelper.buildUnityCombobox(cbUnity);
		UIHelper.setSelectedValueOnCombobox(cbUnity, Unity.UNITY);
	}

	private boolean checkComponents() {
		if (!UIHelper.field(txtCode)) {
			if (!UIHelper.field(txtName)) {
				if (!UIHelper.selection(cbxCategory)) {
					return true;

				}
			}
		}
		return false;
	}

	private void fillVat(Product product) {
		isento.setChecked(product.isFreeVat());
		if (!product.isFreeVat()) {
			product.setVat(dbVat.getValue());
		} else {
			product.setVat(0);
		}
	}

	private void checkVat(Product product) {
		isento.setChecked(product.isFreeVat());
		if (product.isFreeVat()) {
			gbVat.setOpen(false);
		} else {
			gbVat.setOpen(true);
		}
		dbVat.setValue(product.getVat());
	}

	private void amount(Product product) {
		try {
			product.setAmountMax(iMax.getValue());
		} catch (NullPointerException e) {
			product.setAmountMax(0);
		}
		try {
			product.setAmountMin(iMin.getValue());
		} catch (NullPointerException e) {
			product.setAmountMin(0);
		}
	}

}
