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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
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
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProductAddController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713209471379347892L;
	@Wire
	private Window productAdd;
	@Wire
	private Textbox txtCode;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtBrand;
	@Wire
	private Combobox cbxCategory;
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
	@Wire
	private Combobox cbUnity;
	@WireVariable
	private ItemManager itemManager;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
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
			Product product = new Product();
			if (checkComponents()) {
				details(product);
				if (itemManager.findByCode(product.getCode()) == null) {
					if (itemManager.findByName(product.getName()) == null) {
						itemManager.save(product);
						Messages.info_center(Labels.getLabel("messages.add"), productAdd);
						cleanUp();
						list(product);
						UIHelper.refreshListBox("listProducts", "searchProduct", itemManager.allProducts(true),
								itemManager.allProducts());
						productAdd.detach();
					} else {
						Messages.warning_right(Labels.getLabel("product.name.already.exist"), txtName);
					}
				} else {
					Messages.warning_right(Labels.getLabel("product.code.already.exist"), txtCode);
				}
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_right(Labels.getLabel("product.already.exist"), txtName);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
		productAdd.detach();
	}

	private void details(Product product) {
		product.setCode(txtCode.getValue());
		product.setName(txtName.getValue());
		product.setBrand(txtBrand.getValue());
		product.setFreeVat(isento.isChecked());
		fillVat(product);
		product.setCategory(cbxCategory.getText());
		Unity unity = cbUnity.getSelectedItem().getValue();
		product.setUnity(unity);
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
		cbxCategory.getText();
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
		try {
			isento.setChecked(product.isFreeVat());
			if (!product.isFreeVat()) {
				product.setVat(dbVat.getValue());
			} else {
				product.setVat(0);
			}
		} catch (NullPointerException e) {
			isento.setChecked(false);
			// product.setTaxaIva(0);
		}
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

	private void fillFields() {
		try {
			String code = (String) SessionHelper.takeObject("productCode");
			txtCode.setValue(code);
			UIHelper.buildComboboxAlt(cbxCategory, itemManager.allCategories());
			UIHelper.buildUnityCombobox(cbUnity);
			UIHelper.setSelectedValueOnCombobox(cbUnity, Unity.UNITY);
		} catch (NullPointerException e) {
		}
	}
	
	private void list(Product product) {
		Combobox combobox = (Combobox) SessionHelper.takeObject("searchProduct");
		Label label = (Label) SessionHelper.takeObject("label");
		try {
			UIHelper.BuildProductCodeCombobox(combobox, itemManager.allProducts(true));
			UIHelper.setSelectedValueOnCombobox(combobox, product);
			label.setValue(product.getName());
		} catch (NullPointerException e) {
		}
	}
}
