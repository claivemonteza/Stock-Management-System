package sms.item.product.controller;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.product.model.Product;
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
public class ProductSearchController extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 8210611026358965455L;

	@Wire
	private Window winSearch;
	@Wire
	private Combobox cbx_pesquisar;
	@Wire
	private Listbox listbox;
	
	@WireVariable
	private ItemManager itemManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		listar();
	}

	public void onClick$btnCancel(Event e) {
		winSearch.detach();
	}

	public void onClick$btnOk(Event e) {
		Product product = listbox.getSelectedItem().getValue();
		selectedProduct(product);
		winSearch.detach();
	}

	public void onSelect$cbx_pesquisar(Event e) {
		pesquisar();
	}

	public void onOK$cbx_pesquisar(Event e) {
		pesquisar();
	}

	private void selectedProduct(Product product) {
		Combobox combobox = (Combobox) SessionHelper.takeObject("component");
		try {
			UIHelper.setSelectedValueOnCombobox(combobox, product);
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.record"), combobox);
		}
	}
	
	private void pesquisar() {
		try {
			Product produto = cbx_pesquisar.getSelectedItem().getValue();
			onOK_onSelect(itemManager.selectedProduct(produto));
		} catch (NullPointerException ex) {
			List<Product> products = itemManager.find(cbx_pesquisar.getText());
			if (UIHelper.checkFilledFields(cbx_pesquisar) != null
					&& !UIHelper.checkFilledFields(cbx_pesquisar).equals("")) {
				onOK_onSelect(products);
			} else {
				listar();
			}
		}
	}

	private void onOK_onSelect(List<Product> products) {
		UIHelper.buildSimpleList(listbox, products);
	}

	private void listar() {
		try {
			onOK_onSelect(itemManager.allProducts(true));
			UIHelper.buildCombobox(cbx_pesquisar, itemManager.allProducts(true));
		} catch (NullPointerException e) {
			Messages.error_center(Labels.getLabel("no.records"), listbox);
		}
	}
}
