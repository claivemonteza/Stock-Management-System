package sms.invoicing.client.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.client.model.Client;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.invoice.report.InvoicesReceiptReportController;
import sms.invoicing.payment.model.ItemPaidBill;
import sms.invoicing.payment.model.PaidInvoice;
import sms.invoicing.receipt.model.Receipt;
import sms.management.bank.manager.BankManager;
import sms.management.bank.model.ViaBank;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.DateUtil;
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
public class CustomerBillingController extends AutoClosableController {
	private static final long serialVersionUID = -7107339895394631551L;
	@Wire
	private Tab tabBank;
	@Wire
	private Tab tabInvoices;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtNuit;
	@Wire
	private Combobox cbx_type;
	@Wire
	private Textbox txtNumber;
	@Wire
	private Listbox listbox;
	@Wire
	private Listbox lbx_total;
	@Wire
	private Listbox listViaBank;
	@Wire
	private Combobox cbx_bank;
	@Wire
	private Datebox issued;
	@Wire
	private Doublebox amount;
	@Wire
	private Button btnAdd;
	@WireVariable
	private InvoicingManager invoicingManager;
	@WireVariable
	private BankManager bankManager;



	private Client clientSelected;
	private double total;
	private double totalViaBanks;
	private List<ViaBank> viaBanks = new ArrayList<ViaBank>();
	private Set<ItemPaidBill> items = new HashSet<ItemPaidBill>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
		bankManager = ManagerFactory.getBankManager();
		fillBanks();
		fillTypes();
		fillFields();
		txtNumber.focus();
	}

	@Listen("onChange = #txtNumber")
	public void numberChange() {
		if (!txtNumber.getValue().isEmpty()) {
			cbx_bank.focus();
		}
	}

	@Listen("onOk = #txtNumber")
	public void okNumber() {
		if (!txtNumber.getValue().isEmpty()) {
			cbx_bank.focus();
		}
	}

	@Listen("onSelect = #cbx_bank")
	public void selectBank() {
		if (cbx_bank.getSelectedItem().getValue() != null || !cbx_bank.getText().isEmpty()) {
			cbx_type.focus();
		}
	}

	@Listen("onOK = #cbx_bank")
	public void okBank() {
		if (cbx_bank.getSelectedItem().getValue() != null || !cbx_bank.getText().isEmpty()) {
			issued.focus();
		}
	}

	@Listen("onSelect = #cbx_type")
	public void selectType() {
		if (cbx_type.getSelectedItem().getValue() != null || !cbx_type.getText().isEmpty()) {
			issued.focus();
		}
	}

	@Listen("onOK = #cbx_type")
	public void okType() {
		if (cbx_type.getSelectedItem().getValue() != null || !cbx_type.getText().isEmpty()) {
			cbx_type.focus();
		}
	}

	@Listen("onSelect = #issued")
	public void selectIssued() {
		if (issued != null || !issued.getText().isEmpty()) {
			issued.focus();
		}
	}

	@Listen("onOK = #issued")
	public void okissued() {
		if (issued != null || !issued.getText().isEmpty()) {
			cbx_type.focus();
		}
	}

	@Listen("onSelect = #amount")
	public void selectAmount() {
		if (amount.getValue() != null || !amount.getText().isEmpty()) {
			btnAdd.focus();
		}
	}

	@Listen("onOK = #amount")
	public void okAmount() {
		if (amount.getValue() != null || !amount.getText().isEmpty()) {
			btnAdd.focus();
		}
	}

	@Listen("onClick = #btnAdd")
	public void add() {
		try {
			ViaBank viaBank = new ViaBank();
			viaBank.setNumber(txtNumber.getValue());
			viaBank.setBank(cbx_bank.getText());
			viaBank.setType(cbx_type.getText());
			viaBank.setTerm(DateUtil.parse(issued.getValue()));
			viaBank.setAmount(amount.getValue());
			viaBanks.add(viaBank);
			calculate(viaBanks);
			UIHelper.buildSimpleList(listViaBank, viaBanks);
			clean();
			tabInvoices.setSelected(true);
		} catch (NullPointerException e) {
		}
	}

	@Listen("onClick = #btnCancel")
	public void cancel() {
		cleanUp();
		close();
	}

	// TODO Missing the report of the payment
	@Listen("onClick = #btnPay")
	public void pay() {
		try {
			if (total == totalViaBanks) {
				if (total != 0 && totalViaBanks != 0) {
					PaidInvoice paid = new PaidInvoice();
					paid.setAmountPaid(total);
					saveViaBank(viaBanks);
					paid.setViabank(viaBanks);
					invoicingManager.save(paid);
					saveItem(items, paid);
					Receipt receipt = details(paid);
					invoicingManager.save(receipt);
					List<ItemPaidBill> items = UIHelper.getSelectedItemPaidBillOnLIstBox(listbox);
					for (ItemPaidBill item : items) {
						Invoice invoice = item.getInvoice();
						invoice.setPaid(invoice.getPaid() + item.getAmount());
						if (invoice.getTotal() == invoice.getPaid()) {
							invoice.setMadePayment(true);
						}
						invoice.setToPay(Mathematics.unpaid(invoice.getTotal(), invoice.getPaid()));
						invoicingManager.update(invoice);
					}
					InvoicesReceiptReportController.generateReport(viaBanks, receipt);
					cleanUp();
				}else {
					Messages.warning_center(Labels.getLabel("select.invoice"), listbox);
				}
			} else {
				Messages.warning_right(Labels.getLabel("check.value"), lbx_total);
			}
		} catch (InvalidDataAccessApiUsageException | TransientObjectException e) {
			tabBank.setSelected(true);
		}
	}

	@SuppressWarnings("unchecked")
	@Listen("onSelect = #listbox")
	public void selected() {
		List<ItemPaidBill> items = UIHelper.getSelectedValuesOnLIstBox(listbox);
		calcularTotal(items);
	}

	// TODO Need to enter the value that will be paid
	@Listen("onDoubleClick = #listbox")
	public void fillSelection() {
		try {
			ItemPaidBill item = listbox.getSelectedItem().getValue();
			double value = Double
					.parseDouble(JOptionPane.showInputDialog(null, "Valor a pagar?", JOptionPane.OK_CANCEL_OPTION));
			if (value == 0) {
				item.setAmount(item.getInvoice().getToPay());
			} else {
				item.setAmount(value);
			}
			list(items);
		} catch (NullPointerException ex) {
			// Messages.warning_center(Labels.getLabel("select.invoice"), listbox);
		}
	}

	@Listen("onDoubleClick = #listViaBank")
	public void delete() {
		try {
			ViaBank viaBank = listViaBank.getSelectedItem().getValue();
			if (viaBanks.contains(viaBank)) {
				viaBanks.remove(viaBank);
			}
			calculate(viaBanks);
			UIHelper.buildSimpleList(listViaBank, viaBanks);
		} catch (NullPointerException ex) {
			// Messages.warning_center(Labels.getLabel("select.invoice"), listbox);
		}
	}

	public void verificarPagamento(Invoice invoice) {
		if (invoice.getTotal() < total) {

		}
	}

	public Receipt details(PaidInvoice paid) {
		Receipt receipt = new Receipt();
		receipt.setCode(DateUtil.getYear() + "/" + (invoicingManager.allReceipts().size() + 1));
		receipt.setPaid(paid);
		List<ViaBank> viaBanks = paid.getViabank();
		String remark = "";
		for (ViaBank viaBank : viaBanks) {
			if (remark.isEmpty()) {
				remark = viaBank.getType() + " " + viaBank.getNumber();
			} else {
				remark = "," + viaBank.getType() + " " + viaBank.getNumber();
			}
		}
		receipt.setNote(remark);
		return receipt;
	}

	private void fillFields() {
		try {
			clientSelected = (Client) SessionHelper.takeObject("customerbilling");
			txtName.setValue(clientSelected.getName());
			txtNuit.setValue(clientSelected.getNuit());
			auxiliaryBuild(invoicingManager.allInvoicesUnpaid(clientSelected));
			// Messages.warning_center(Labels.getLabel("messages.doubleclick.details"),
			// listbox);
		} catch (NullPointerException e) {
			Messages.warning_center(Labels.getLabel("select.client"), listbox);
		}
	}

	// TODO Need to change the way how to view the date at the table of banks
	private void fillBanks() {
		try {
			UIHelper.BuildBankCombobox(cbx_bank, List.copyOf(bankManager.allBanks()));
		} catch (NullPointerException e) {
			Messages.info_center(Labels.getLabel("no.record"), cbx_bank);
		}
	}

	private void fillTypes() {
		try {
			UIHelper.BuildBankCombobox(cbx_type, List.copyOf(bankManager.allTypes()));
		} catch (NullPointerException e) {
			Messages.info_center(Labels.getLabel("no.record"), cbx_bank);
		}
	}

	private void list(Set<ItemPaidBill> items) {
		UIHelper.buildInvoicesToPayListbox(listbox, items);
	}

	private void auxiliaryBuild(List<Invoice> invoices) {
		for (Invoice invoice : invoices) {
			ItemPaidBill item = new ItemPaidBill();
			item.setInvoice(invoice);
			items.add(item);
		}
		UIHelper.buildInvoicesToPayListbox(listbox, items);
	}

	private void calcularTotal(List<ItemPaidBill> items) {
		total = Mathematics.calculateTotalToPay(items);
		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(2);
		Listcell listcell = (Listcell) listitem.getChildren().get(5);
		listcell.setLabel(String.valueOf(formato.format(total)));

	}

	private void cleanUp() {
		txtName.setValue(null);
		txtNuit.setValue(null);
		clientSelected = null;
		items.clear();
		viaBanks.clear();
		clean();
	}

	private void clean() {
		txtNumber.setValue(null);
		cbx_type.setText(null);
		issued.setValue(null);
		amount.setValue(0.00);
		cbx_bank.setText(null);
		fillBanks();
	}

	private void calculate(List<ViaBank> viaBanks) {
		String type = "";
		totalViaBanks = 0.00;
		for (ViaBank viaBank : viaBanks) {
			type = type + viaBank.getType() + " " + viaBank.getNumber() + ", ";
			totalViaBanks = totalViaBanks + viaBank.getAmount();
		}
		fillViaBank(totalViaBanks, type);
	}

	private void fillViaBank(double total, String str) {
		DecimalFormat formato = new DecimalFormat("0.00");
		Listitem listitem = (Listitem) lbx_total.getChildren().get(1);
		Listcell listcell = (Listcell) listitem.getChildren().get(4);
		listcell.setLabel(str);
		Listcell listcell2 = (Listcell) listitem.getChildren().get(5);
		listcell2.setLabel(String.valueOf(formato.format(total)));
	}

	public void saveItem(Set<ItemPaidBill> items, PaidInvoice paid) {
		for (ItemPaidBill item : items) {
			item.setPaid(paid);
			invoicingManager.saveItem(item);
		}
	}

	public void saveViaBank(List<ViaBank> viaBanks) {
		for (ViaBank viaBank : viaBanks) {
			try {
				bankManager.save(viaBank);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				UIHelper.setSelectedValuesOnListbox(listViaBank, viaBank);
				Messages.warning_left(Labels.getLabel("check.number.transaction"), listViaBank.getSelectedItem());
			}
		}
	}
}
