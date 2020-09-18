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
import sms.invoicing.item.model.SaleItem;
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
public class InvoiceReportController {
	private static InvoicingManager invoicingManager = ManagerFactory.getInvoicingManager();
	
	public static void generateReport(Invoice invoice) {
		List<SaleItem> itens = invoicingManager.allItems(invoice);
		if (!itens.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(invoice), "Invoice.jasper",
					SessionHelper.getUser());
			String name = Labels.getLabel("invoice.record")+invoice.getDate()+".pdf"; 
			UIHelper.generateReport(report,name);
		} else {
			throw new WrongValueException("no.report.data");
		}
	}

	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("CLIENT",Labels.getLabel("name.record"));
		parameters.put("ADDRESS", Labels.getLabel("address.record"));
		parameters.put("NUIT",Labels.getLabel("nuit.record"));
		parameters.put("REPORT_NAME", Labels.getLabel("invoice.record"));
		parameters.put("DUEDATE",Labels.getLabel("duedate.record"));
		parameters.put("DATE",Labels.getLabel("date.record"));
		parameters.put("REFERENCE", Labels.getLabel("reference.record"));
		parameters.put("NAME", Labels.getLabel("name.record"));
		parameters.put("BATCH", Labels.getLabel("batch.record"));
		parameters.put("AMOUNT",Labels.getLabel("amount.record"));
		parameters.put("PRICE", Labels.getLabel("price.record"));
		parameters.put("VAT", Labels.getLabel("vat.record"));
		parameters.put("UNIT_PRICE", Labels.getLabel("total.unit"));
		parameters.put("SUBTOTAL", Labels.getLabel("subtotal.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(Invoice invoice) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		List<SaleItem> itens = invoicingManager.allItems(invoice);
		for (SaleItem item : itens) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("client", invoice.getClient().getName());
			reportObject.put("address", invoice.getClient().getAddress());
			reportObject.put("nuit", invoice.getClient().getNuit());
			reportObject.put("reference",invoice.getCode());
			reportObject.put("duedate",DateUtil.parse(invoice.getDueDate()));
			reportObject.put("date", invoice.getDate());
			reportObject.put("code",item.getBatch().getProduct().getCode());
			reportObject.put("name", item.getBatch().getProduct().getName());
			reportObject.put("batch", item.getBatch().getCode());
			reportObject.put("amount", item.getAmount());
			reportObject.put("price", item.getBatch().getSalePrice());
			reportObject.put("vat", item.getBatch().getProduct().getVat());
			reportObject.put("unittotal", item.getUnitTotal());
			reportObjects.add(reportObject);
		}
		reportObject.put("subTotal", invoice.getSubTotal());
		reportObject.put("COLUMN_13", invoice.getTotalVat());
		reportObject.put("total", invoice.getTotal());
		return reportObjects;
	}
}
