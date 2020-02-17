package sms.invoicing.client.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.model.Invoice;
import sms.management.bank.manager.BankManager;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Mathematics;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see InvoicingManager
 * @see BankManager
 * @see Client
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class CustomerBallsController extends AutoClosableController {
	private static final long serialVersionUID = -7107339895394631551L;

	@Wire
	private Tab tabInvoices;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtNuit;

	@Wire
	private Textbox txtNumber;
	@Wire
	private Listbox listbox;
	@Wire
	private Listbox lbx_total;

	@WireVariable
	private InvoicingManager invoicingManager;
	@WireVariable
	private BankManager bankManager;

	private Client clientSelected;
	private double total;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
		bankManager = ManagerFactory.getBankManager();
		fillFields();
	}

	//TODO Make the button function work and all invoice selected print
	public void print() {
		
	}
	
	
	//TODO Make the button function work
	public void cancel() {
		cleanUp();
		this.close();
	}
	
	private void fillFields() {
		try {
			clientSelected = (Client) SessionHelper.takeObject("customerballs");
			txtName.setValue(clientSelected.getName());
			txtNuit.setValue(clientSelected.getNuit());
			auxiliaryBuild(invoicingManager.allInvoices(clientSelected));
			// Messages.warning_center(Labels.getLabel("messages.doubleclick.details"),
			// listbox);
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		}
	}

	private void auxiliaryBuild(List<Invoice> invoices) {
		Set<Invoice> listOfInvoices = Set.copyOf(invoices);
		UIHelper.buildInvoicesListbox(listbox, listOfInvoices);
		calcularTotal(listOfInvoices);
	}

	private void calcularTotal(Set<Invoice> invoices) {
		total = Mathematics.calculateTotalOfAllInvoiceOfCustomer(invoices);
		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcell = (Listcell) listitem.getChildren().get(5);
		listcell.setLabel(String.valueOf(formato.format(total)));
	}
	
	private void cleanUp() {
		txtName.setValue(null);
		txtNuit.setValue(null);
		total = 0.00;
		clientSelected = null;
		listbox.getItems().clear();
	}

}
