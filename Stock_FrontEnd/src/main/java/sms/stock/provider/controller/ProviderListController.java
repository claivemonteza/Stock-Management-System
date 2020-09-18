package sms.stock.provider.controller;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;

import sms.ManagerFactory;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.stock.StockManager;
import sms.stock.provider.model.Provider;
import sms.util.AutoClosableController;
import sms.util.Messages;


/**
 * The class that will controller all the functionalities of the view
 * 
 * @see StockManager
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProviderListController extends AutoClosableController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427591684573675888L;

	
	@Wire
	private Combobox cbx_search;
	@Wire
	private Listbox listbox;
	@Wire
	private Checkbox showInactive;

	@WireVariable
	private StockManager stockManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		stockManager = ManagerFactory.getStockManager();
		list();
	}

	
	@Listen("onClick = #btnNew")
	public void add(){
		sendComponents();
		Executions.createComponents("stock/provider/provider-add.zul", null, null);
		
	}

	@Listen("onClick = #btnDelete")
	public void delete() {
		try {
			Provider provider = listbox.getSelectedItem().getValue();
			stockManager.delete(provider);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.provider"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException ex) {
			Messages.error_center(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	
	@Listen("onClick = #btnActiveInactive")
	public void activeInactive(){
		try {
			Provider provider = listbox.getSelectedItem().getValue();
			provider.activeInactive();
			stockManager.update(provider);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.provider"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("provider.already.exist"), listbox);
		}
	}

	@Listen("onCheck = #showInactive")
	public void showInactive(){
		list();
	}

	@Listen("onSelect = #cbx_search")
	public void onSelect(){
		search();
	}

	@Listen("onOK = #cbx_search")
	public void onOK(){
		search();
	}


	@Listen("onDoubleClick = #listbox")
	public void fillSelection(){
		try {
			Provider provider = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("provider", provider);
			sendComponents();
			Executions.createComponents("stock/provider/provider-edit.zul", null, null);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.provider"), listbox);
		}
	}

	@Listen("onClick = #btnRequests")
	public void movements() {
		try {
			Provider provider = listbox.getSelectedItem().getValue();
			Sessions.getCurrent().setAttribute("providerMovements", provider);
			SessionHelper.getMainController().createNewTab(Labels.getLabel("request.provider"),
					"stock/provider/provider-activity.zul");
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.provider"), listbox);
		}
	}

	private void list() {
		try {
			auxiliaryBuild(stockManager.allProviders(!showInactive.isChecked()));
			UIHelper.buildCombobox(cbx_search, stockManager.allProviders());
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.records"), listbox);
		}
	}

	private void search() {
		try {
			auxiliaryBuild(stockManager.selectedProvider((Provider) cbx_search.getSelectedItem().getValue()));
		} catch (NullPointerException ex) {
			List<Provider> providers = stockManager.find(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null
					&& !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(providers);
			} else {
				list();
			}
		}
	}

	private void auxiliaryBuild(List<Provider> providers) {
		UIHelper.buildComplexList(listbox, providers);
	}
	
	public void sendComponents() {
		SessionHelper.setObject("listProviders", listbox);
		SessionHelper.setObject("searchProvider", cbx_search);
	}
}
