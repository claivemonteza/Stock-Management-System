package sms.invoicing.sale.controller;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.cash.report.CashSaleReportController;
import sms.invoicing.payment.model.ItemPaidPos;
import sms.invoicing.payment.model.PaidPos;
import sms.invoicing.payment.model.PayMethod;
import sms.invoicing.receipt.model.Receipt;
import sms.item.ItemManager;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.Mathematics;
import sms.util.Messages;



/**
 * The class that will controller all the functionalities of the view
 * 
 * @see InvoicingManager
 * @see ItemManager
 * @see CashSale
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class PaymentController extends GenericForwardComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Window winPayment;
	@Wire
	private Doublebox value;
	@Wire
	private Doublebox amountToPay;
	@Wire
	private Doublebox change;
	@Wire
	private Combobox cbx_method;
	@Wire
	private Textbox txtTransacao;

	@WireVariable
	private InvoicingManager invoicingManager;
	@WireVariable
	private ItemManager itemManager;

	private CashSale cashSale;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		invoicingManager = ManagerFactory.getInvoicingManager();
		itemManager = ManagerFactory.getItemManager();
		fillUp();
	}

	public void onChange$value(Event e) {
		change.setValue(Mathematics.change(value.getValue(), amountToPay.getValue()));
	}

	public void onOK$value(Event e) {
		change.setValue(Mathematics.change(value.getValue(), amountToPay.getValue()));
	}

	public void onClick$pay(Event e) {
		paid();
	}

	public void onClick$cancel(Event e) {
		cleanUp();
	}

	private void fillUp() {
		listPayMethod();
		cashSale = (CashSale) SessionHelper.takeObject("vd");
		amountToPay.setValue(cashSale.getTotal());

	}

	private void cleanUp() {
		value.setValue(null);
		amountToPay.setValue(null);
		change.setValue(null);
		txtTransacao.setValue(null);
		cashSale = null;
		listPayMethod();
		winPayment.detach();
	}

	public void paid() {
		PaidPos paid = new PaidPos();
		detailsOfPaid(paid);
		invoicingManager.save(paid);
		detailsOfCashSale(cashSale, paid);
		saveVdItem(cashSale);
		Mathematics.calculateQuantity();
		detailsOfReceipt(paid);
		saveItemPaid(cashSale, paid);
		CashSaleReportController.generateReport(cashSale);
		Messages.info_center(Labels.getLabel("messages.add"), winPayment);
		cleanUp();
	}

	private void detailsOfPaid(PaidPos paid) {
		PayMethod payMethod = cbx_method.getSelectedItem().getValue();
		paid.setPayMethod(payMethod);
		paid.setAmountPaid(value.getValue());
		if (payMethod != PayMethod.CASH) {
			paid.setCodeTransaction(txtTransacao.getValue());
		}
	}

	// TODO Check the remark of the pdf of the vd
	private void detailsOfReceipt(PaidPos paid) {
		Receipt receipt = new Receipt();
		receipt.setCode(String.valueOf(invoicingManager.allReceipts().size() + 1));
		receipt.setPaid(paid);
		receipt.setNote("");
		invoicingManager.save(receipt);
	}

	
	public void detailsOfCashSale(CashSale vd, PaidPos paid) {
		vd.setPaid(paid.getAmountPaid());
		vd.setToPay(Mathematics.unpaid(vd.getTotal(), vd.getPaid()));
		vd.setTransshipment(Mathematics.change(value.getValue(), amountToPay.getValue()));
	}

	public void saveVdItem(CashSale vd) {
		invoicingManager.save(vd);
		invoicingManager.saveItens(vd);
	}

	public void saveItemPaid(CashSale vd, PaidPos paid) {
		ItemPaidPos item = new ItemPaidPos();
		item.setCashSale(vd);
		item.setPaid(paid);
		item.setAmount(vd.getTotal());
		invoicingManager.saveItem(item);
	}

	private void listPayMethod() {
		UIHelper.buildPayMethodsCombobox(cbx_method);
		UIHelper.setSelectedValueOnCombobox(cbx_method, PayMethod.CASH);
	}

}
