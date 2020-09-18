package sms.invoicing.client.controller;

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
import sms.invoicing.InvoicingManager;
import sms.invoicing.client.model.Client;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Messages;



/**
 * The class that will controller all the functionalities of the view
 * 
 * @see InvoicingManager
 * @see Client
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ClientListController extends AutoClosableController{

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
	private InvoicingManager invoicingManager;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
		list();

	}

	@Listen("onClick = #btnNew")
	public void add(){
		sendComponents();
		Executions.createComponents("invoicing/client/client-add.zul", null, null);
	}

	@Listen("onClick = #btnDelete")
	public void delete(){
		try {
			Client client = listbox.getSelectedItem().getValue();
			invoicingManager.delete(client);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			list();
		} catch (NullPointerException | IllegalArgumentException ex) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException ex) {
			Messages.error_center(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	@Listen("onClick = #btnActiveInactive")
	public void activeInactive(){
		try {
			Client client = listbox.getSelectedItem().getValue();
			client.activeInactive();
			invoicingManager.update(client);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("client.already.exist"), listbox);
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
			Client client = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("client", client);
			sendComponents();
			Executions.createComponents("invoicing/client/client-edit.zul", null, null);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		}
	}

	
	@Listen("onClick = #btnMovements")
	public void movements(){
		try {
			Client client = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("customerbilling", client);
			SessionHelper.getMainController().createNewTab(Labels.getLabel("customer.billing"),
					"invoicing/customer-billing.zul");
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		}
	}
	
	//TODO Make the function work and need to show the view of the report
	@Listen("onClick = #btnExtract")
	public void extract() {
		try {
			Client client = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("customerballs", client);
			SessionHelper.getMainController().createNewTab(Labels.getLabel("customer.balls"),
					"invoicing/client/customer-balls.zul");
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		}
	}

	private void list() {
		try {
			auxiliaryBuild(invoicingManager.allClients(!showInactive.isChecked()));
			UIHelper.buildCombobox(cbx_search, invoicingManager.allClients());
		} catch (NullPointerException e) {
			Messages.error_center(Labels.getLabel("no.records"), listbox);
		}
	}

	private void search() {
		try {
			auxiliaryBuild(invoicingManager.selectedClient((Client) cbx_search.getSelectedItem().getValue()));
		} catch (NullPointerException ex) {
			List<Client> clients = invoicingManager.find(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null
					&& !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(clients);
			} else {
				list();
			}
		}
	}

	private void auxiliaryBuild(List<Client> clients) {
		UIHelper.buildComplexList(listbox, clients);
	}

	public void sendComponents() {
		SessionHelper.setObject("listClients", listbox);
		SessionHelper.setObject("searchClient", cbx_search);
	}
}
