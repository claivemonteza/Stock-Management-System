package sms.item.product.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
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
public class ProductBatchReportController {

	public static void generateReport(Product product,List<Batch> batches,double totalcost, double totalSale, double totalAmount) {
		if (!batches.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(product, batches,totalcost, totalSale, totalAmount), "productBatch.jasper",
					SessionHelper.getUser());
			String name = Labels.getLabel("batches.product")+" "+DateUtil. parse(new Date())+".pdf"; 
			UIHelper.generateReport(report,name);
		} else {
			throw new WrongValueException(Labels.getLabel("no.records"));
		}
	}

	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("REPORT_NAME", Labels.getLabel("batches.product"));
		parameters.put("REFERENCE",Labels.getLabel("code.record"));
		parameters.put("NAME",Labels.getLabel("name.record"));
		parameters.put("DATA",Labels.getLabel("date.record"));
		parameters.put("DATE",DateUtil.parse(new Date()));
		parameters.put("CODE", Labels.getLabel("code.record"));
		parameters.put("MANUFACTORE",Labels.getLabel("date.manufacture"));
		parameters.put("EXPIRATION", Labels.getLabel("expiration.date"));
		parameters.put("COST", Labels.getLabel("cost.price"));
		parameters.put("SALE", Labels.getLabel("sale.price"));
		parameters.put("AMOUNT", Labels.getLabel("amount.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(Product product, List<Batch> batches,double totalcost, double totalSale, double totalAmount) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		for (Batch batch : batches) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("reference", product.getCode());
			reportObject.put("name", product.getName());
			reportObject.put("code", batch.getCode());
			reportObject.put("manufactore",String.valueOf(batch.getDateManufacturing()));
			reportObject.put("expiration",String.valueOf(batch.getExpirationDate()));
			reportObject.put("cost", batch.getCostPrice());
			reportObject.put("sale",batch.getSalePrice());
			reportObject.put("amount", batch.getAmount());
			reportObjects.add(reportObject);
		}
		reportObject.put("totalCost", totalcost);
		reportObject.put("totalSale", totalSale);
		reportObject.put("totalAmount", totalAmount);
		return reportObjects;
	}
}
