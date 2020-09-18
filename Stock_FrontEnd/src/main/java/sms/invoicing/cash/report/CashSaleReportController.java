package sms.invoicing.cash.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.cash.model.CashSale;
import sms.invoicing.item.model.SaleItem;
import sms.report.Report;
import sms.report.SessionHelper;
import sms.report.UIHelper;

/**
 * <code>CashSaleReportController</code> will controller all the functionalities of the view
 * 
 * @see InvoicingManager
 * @see CashSale
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class CashSaleReportController {

	private static InvoicingManager invoicingManager = ManagerFactory.getInvoicingManager();
	//TODO Missing the value change when view the report
	public static void generateReport(CashSale cashSale){
		List<SaleItem> itens = invoicingManager.allItems(cashSale);
		if (!itens.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(cashSale), "cashsale.jasper",
					SessionHelper.getUser());
			
			String name = Labels.getLabel("cash.sale")+cashSale.getDate()+".pdf"; 
			UIHelper.generateReport(report,name);
		} else {
			throw new WrongValueException("no.report.data");
		}
	}
	
	
	/**
	 * Details of the labels for the report.
	 * */
	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("CLIENT",Labels.getLabel("name.record"));
		parameters.put("NUIT",Labels.getLabel("nuit.record"));
		parameters.put("REPORT_NAME", Labels.getLabel("cashsale.record"));
		parameters.put("DATE",Labels.getLabel("date.record"));
		parameters.put("CODE", Labels.getLabel("reference.record"));
		parameters.put("NAME", Labels.getLabel("name.record"));
		parameters.put("BATCH", Labels.getLabel("batch.record"));
		parameters.put("PRICE", Labels.getLabel("price.record"));
		parameters.put("AMOUNT",Labels.getLabel("amount.record"));
		parameters.put("VAT", Labels.getLabel("vat.record"));
		parameters.put("UNIT_PRICE", Labels.getLabel("total.unit"));
		parameters.put("SUBTOTAL", Labels.getLabel("subtotal.record"));
		parameters.put("VAT", Labels.getLabel("vat.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		parameters.put("CHANGE", Labels.getLabel("change.record"));
		return parameters;
	}


	/**
	 * Details of the input of the report.
	 * 
	 * @param cashSale
	 * @return 
	 * */
	private static List<Map<String, Object>> generateReportData(CashSale cashSale) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		List<SaleItem> itens = invoicingManager.allItems(cashSale);
		for (SaleItem item : itens) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("client", cashSale.getClient().getName());
			reportObject.put("nuit", cashSale.getClient().getNuit());
			reportObject.put("reference",cashSale.getCode());
			reportObject.put("date", cashSale.getDate());
			reportObject.put("code",item.getBatch().getProduct().getCode());
			reportObject.put("name", item.getBatch().getProduct().getName());
			reportObject.put("batch", item.getBatch().getCode());
			reportObject.put("price", item.getBatch().getSalePrice());
			reportObject.put("amount", item.getAmount());
			reportObject.put("vat", item.getBatch().getProduct().getVat());
			reportObject.put("unittotal", item.getUnitTotal());
			reportObjects.add(reportObject);
		}
		reportObject.put("subTotal", cashSale.getSubTotal());
		reportObject.put("COLUMN_13", cashSale.getTotalVat());
		reportObject.put("total", cashSale.getTotal());
		reportObject.put("change", cashSale.getTransshipment());
		return reportObjects;
	}
}
