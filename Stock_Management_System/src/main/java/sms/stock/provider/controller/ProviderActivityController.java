package sms.stock.provider.controller;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.report.UIHelper;
import sms.stock.StockManager;
import sms.stock.provider.model.Provider;
import sms.stock.request.model.Request;
import sms.stock.request.report.RequestReportController;
import sms.util.AutoClosableController;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see StockManager
 * @see Request
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProviderActivityController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7457180969574257137L;

	@Wire
	private Combobox cbx_search;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtNuit;
	@Wire
	private Listbox listbox;

	@WireVariable
	private StockManager stockManager;

	private List<Request> requisiction;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		stockManager = ManagerFactory.getStockManager();
		fillFields();
		list();
	}

	@Listen("onClick = #btnCancel")
	public void cancel() {
		close();
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
			Request requist = listbox.getSelectedItem().getValue();
			RequestReportController.generateReport(requist);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.request"), listbox);
		}
	}
	
	
	//TODO Don´t have the function yet
	@Listen("onClick = #btnPrint")
	public void print() {
		
	}

	private void list() {
		auxiliaryBuild(requisiction);
		UIHelper.buildCombobox(cbx_search, requisiction);
	}

	private void search() {
		try {
			auxiliaryBuild(stockManager.selectedRequest((Request) cbx_search.getSelectedItem().getValue()));
		} catch (NullPointerException ex) {
			list();
		}
	}

	private void fillFields() {
		try {
			Provider provider = (Provider) Sessions.getCurrent().getAttribute("providerMovements");
			txtName.setValue(provider.getName());
			txtNuit.setValue(provider.getNuit());
			requisiction = stockManager.allRequisitions(provider);
			auxiliaryBuild(requisiction);
			Messages.warning_center(Labels.getLabel("messages.doubleclick.details"), listbox);
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("select.Provider"), listbox);
		}
	}

	private void auxiliaryBuild(List<Request> requisiction) {
		UIHelper.buildProviderActivityListbox(listbox, requisiction);
	}
}
