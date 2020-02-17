package sms.invoicing.client.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.client.model.Client;
import sms.report.UIHelper;
import sms.util.Messages;

//TODO Create documentation

public class ClientAddController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427591684573675888L;

	@Wire
	private Window clientAdd;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtNuit;
	@Wire
	private Textbox txtAddress;
	@Wire
	private Textbox txtCity;
	@Wire
	private Textbox txtProvince;
	@Wire
	private Textbox txtCountry;
	@Wire
	private Textbox txtPhone;
	@Wire
	private Textbox txtMobile;
	@Wire
	private Textbox txtFax;
	@Wire
	private Textbox txtEmail;

	@WireVariable
	private InvoicingManager invoicingManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
	}

	public void onClick$btnSave(Event e) {
		try {
			Client client = new Client();
			if (checkComponents()) {
				details(client);
				if (checkClient(client)) {
					invoicingManager.save(client);
					Messages.info_center(Labels.getLabel("messages.add"), clientAdd);
					cleanUp();
					UIHelper.refreshListBox("listClients", "searchClient", invoicingManager.allClients(true), invoicingManager.allClients());
				}
			}
		} catch (ConstraintViolationException | DataIntegrityViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("client.already.exist"), clientAdd);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
		clientAdd.detach();
	}

	

	private void cleanUp() {
		txtName.setValue(null);
		txtNuit.setValue(null);
		txtAddress.setValue(null);
		txtCity.setValue(null);
		txtProvince.setValue(null);
		txtCountry.setValue(null);
		txtPhone.setValue(null);
		txtMobile.setValue(null);
		txtFax.setValue(null);
		txtEmail.setValue(null);
	}

	private void details(Client cliente) {
		cliente.setName(txtName.getValue());
		cliente.setNuit(txtNuit.getValue());
		cliente.setAddress(txtAddress.getValue());
		cliente.setCity(txtCity.getValue());
		cliente.setProvince(txtProvince.getValue());
		cliente.setCountry(txtCountry.getValue());
		cliente.setTelephone(txtPhone.getValue());
		cliente.setMobile(txtMobile.getValue());
		cliente.setFax(txtFax.getValue());
		cliente.setEmail(txtEmail.getValue());
	}

	private boolean checkComponents() {
		if (!UIHelper.field(txtName)) {
			return true;
		}
		return false;
	}

	private boolean checkClient(Client client) {
		if (invoicingManager.findByName(client.getName()) == null) {
			if (invoicingManager.findByNuit(client.getNuit()) == null) {
				return true;
			} else {
				Messages.warning_center(Labels.getLabel("nuit.already.exist"), txtNuit);
			}
		} else {
			Messages.warning_center(Labels.getLabel("name.already.exist"), txtName);
		}
		return false;
	}
/*
	private void list() {
		Listbox listbox = (Listbox) Sessions.getCurrent().getAttribute("listClients");
		Combobox combobox = (Combobox) Sessions.getCurrent().getAttribute("searchClient");
		try {
			UIHelper.buildComplexList(listbox, clientManager.allClients(true));
			UIHelper.buildCombobox(combobox, clientManager.allClients());
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("no.records"), listbox);
		}
	}*/
}
