/**
 * 
 */
package sms.item.product.controller;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.product.model.Product;
import sms.report.SessionHelper;
import sms.util.AutoClosableController;
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
public class ProdutoLogController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4142753051507837334L;
	@Wire
	private Listbox listbox;
	@Wire
	private Textbox txtCode;
	@Wire
	private Textbox txtName;
	
	@WireVariable
	private ItemManager itemManager;
	
	private Product productSelected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		itemManager = ManagerFactory.getItemManager();
		fillfield();
	}
	
	
	//TODO Make this button function work
	public void print() {
		
	}
	
	//TODO Make this button function work
	public void cancel() {
		cleanFields();
	}

	private void fillfield() {
		try {
			productSelected = (Product) SessionHelper.takeObject("productTransaction");
			txtCode.setValue(productSelected.getCode());
			txtName.setValue(productSelected.getName());
			//auxiliaryBuild(itemManager.allLogs(productSelected));
		} catch (NullPointerException e) {
			Messages.error_center(Labels.getLabel("no.records"), listbox);
		}

	}
/*
	private void auxiliaryBuild(List<ProductLog> produtoLogs) {
		UIHelper.buildComplexList(listbox, produtoLogs);
	}*/

	public void cleanFields() {
		txtCode.setValue(null);
		txtName.setValue(null);
		productSelected = null;
		//auxiliaryBuild(null);
	}
}
