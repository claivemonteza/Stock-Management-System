/**
 * 
 */
package sms.invoicing.invoice.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.receipt.model.Receipt;
import sms.management.bank.model.ViaBank;
import sms.report.Report;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.DateUtil;

/**
 * This class will input all data into the report
 * 
 * @author Claive Monteza
 *
 */
public class InvoicesReceiptReportController {

	private static InvoicingManager invoicingManager = ManagerFactory.getInvoicingManager();
	private static List<ViaBank> viasBanks;

	public static void generateReport(List<ViaBank> banks, Receipt receipt) {

		List<Invoice> invoices = invoicingManager.allInvoices(receipt.getPaid());
		viasBanks = banks;
		if (!invoices.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(receipt), "InvoicesReceipt.jasper",
					SessionHelper.getUser());
			String name = Labels.getLabel("Receipt.record") + receipt.getDate() + ".pdf";
			UIHelper.generateReport(report, name);
		} else {
			throw new WrongValueException("no.report.data");
		}
	}

	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("CLIENT", Labels.getLabel("name.record"));
		parameters.put("ADDRESS", Labels.getLabel("address.record"));
		parameters.put("NUIT", Labels.getLabel("nuit.record"));
		parameters.put("REPORT_NAME", Labels.getLabel("receipt.record"));
		parameters.put("DATE", Labels.getLabel("date.record"));
		parameters.put("INVOICE", Labels.getLabel("invoice.record"));
		parameters.put("EMISSION", Labels.getLabel("emission.record"));
		parameters.put("DUEDATE", Labels.getLabel("duedate.record"));
		parameters.put("VALUE", Labels.getLabel("value.record"));
		parameters.put("PAID", Labels.getLabel("paid.out"));
		parameters.put("UNPAID", Labels.getLabel("unpaid.record"));
		parameters.put("BANK", Labels.getLabel("bank.record"));
		parameters.put("TYPE", Labels.getLabel("form.payment"));
		parameters.put("REFERENCE", Labels.getLabel("reference.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(Receipt receipt) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		List<Invoice> invoices = invoicingManager.allInvoices(receipt.getPaid());

		for (Invoice invoice : invoices) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("client", invoice.getClient().getName());
			reportObject.put("address", invoice.getClient().getAddress());
			reportObject.put("nuit", invoice.getClient().getNuit());
			reportObject.put("reference", receipt.getCode());
			reportObject.put("date", invoice.getDate());
			reportObject.put("code", invoice.getCode());
			reportObject.put("emission", invoice.getDate());
			reportObject.put("duedate", DateUtil.parse(invoice.getDueDate()));
			reportObject.put("value", invoice.getTotal());
			reportObject.put("unpaid", invoice.getToPay());
			reportObject.put("paid", invoice.getPaid());
			reportObjects.add(reportObject);
		}

		for (ViaBank viaBank : viasBanks) {
			reportObject.put("bank", viaBank.getBank());
			reportObject.put("type", viaBank.getType());
			reportObject.put("number", viaBank.getNumber());
			reportObject.put("terms", viaBank.getTerm());
		}

		reportObject.put("total", receipt.getPaid().getAmountPaid());
		return reportObjects;
	}
}
