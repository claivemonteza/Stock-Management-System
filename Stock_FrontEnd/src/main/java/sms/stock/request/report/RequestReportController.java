package sms.stock.request.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import sms.ManagerFactory;
import sms.report.Report;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.stock.StockManager;
import sms.stock.item.model.ItemRequest;
import sms.stock.request.model.Request;

/**
 * This class will input all data into the report
 * 
 * @see StockManager
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public class RequestReportController {

	private static StockManager stockManager = ManagerFactory.getStockManager();
	
	public static void  generateReport(Request requisitar){
		List<ItemRequest> itens = stockManager.allItems(requisitar);
		if (!itens.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(requisitar), "Request.jasper",
					SessionHelper.getUser());
			
			String name = Labels.getLabel("request.record")+" "+requisitar.getDate()+".pdf"; 
			UIHelper.generateReport(report,name);
		} else {
			throw new WrongValueException(Labels.getLabel("messages.empty"));
		}
	}
	
	
	
	
	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("CLIENT", Labels.getLabel("client.record"));
		parameters.put("ADDRESS", Labels.getLabel("address.record"));
		parameters.put("NUIT", Labels.getLabel("nuit.record"));
		parameters.put("REPORT_NAME",Labels.getLabel("request.record"));
		parameters.put("DATE", Labels.getLabel("date.record"));
		parameters.put("REFERENCE", Labels.getLabel("code.record"));
		parameters.put("NAME",Labels.getLabel("name.record"));
		parameters.put("BATCH", Labels.getLabel("batch.record"));
		parameters.put("COST_PRICE", Labels.getLabel("cost.price"));
		parameters.put("AMOUNT",Labels.getLabel("amount.record"));
		parameters.put("UNITY", Labels.getLabel("unity.record"));
		parameters.put("VAT", Labels.getLabel("vat.record"));
		parameters.put("UNIT_PRICE", Labels.getLabel("total.unit"));
		parameters.put("REMARK", Labels.getLabel("remark.record")+" ");
		parameters.put("SUBTOTAL", Labels.getLabel("subtotal.record"));
		parameters.put("VAT", Labels.getLabel("vat.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(Request requisitar) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		List<ItemRequest> itens = stockManager.allItems(requisitar);
		
		for (ItemRequest item : itens) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("NAME", requisitar.getProvider().getName());
			reportObject.put("address", requisitar.getProvider().getAddress());
			reportObject.put("nuit", requisitar.getProvider().getNuit());
			reportObject.put("code", requisitar.getCode());
			reportObject.put("date", requisitar.getDate());
			reportObject.put("COLUMN_3",item.getBatch().getProduct().getCode());
			reportObject.put("COLUMN_5", item.getBatch().getProduct().getName());;
			reportObject.put("COLUMN_12", item.getBatch().getCode());
			reportObject.put("cost_price", item.getBatch().getCostPrice());
			reportObject.put("amount", item.getAmount());
			reportObject.put("unity", item.getBatch().getProduct().getUnity().getVariant());
			reportObject.put("COLUMN_6", item.getBatch().getProduct().getVat());
			reportObject.put("unit_total", item.getUnitTotal());
			reportObjects.add(reportObject);
		}
		reportObject.put("remark", Labels.getLabel("provider.invoice.number") +" "+ requisitar.getInvoiceProvider());
		reportObject.put("subtotal", requisitar.getSubtotal());
		reportObject.put("vat", requisitar.getVat());
		reportObject.put("total", requisitar.getTotal());
		return reportObjects;
	}
}
