package sms.item.batch.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;

import sms.ManagerFactory;
import sms.item.ItemManager;
import sms.item.batch.model.Batch;
import sms.report.Report;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.DateUtil;

/**
 * This class will input all data into the report
 * 
 * @see ItemManager
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ExpiringBatchesReportController {

	private static ItemManager itemManager = ManagerFactory.getItemManager();
	
	public static void  generateReport(){
		List<Batch> lotes = itemManager.outOfDateBatches(new Date());
		if (!lotes.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(lotes), "foravalidade.jasper",
					SessionHelper.getUser());
			
			String name = Labels.getLabel("batch.expired")+"-"+DateUtil.parse()+".pdf"; 
			UIHelper.generateReport(report,name);
		} 
	}
	
	
	
	
	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("REPORT_NAME", Labels.getLabel("batch.expired"));
		parameters.put("DATA", Labels.getLabel("date.record")+": ");
		parameters.put("DATE", String.valueOf(DateUtil.parse()));
		parameters.put("REFERENCIA", Labels.getLabel("reference.record"));
		parameters.put("DESIGNACAO", Labels.getLabel("designation.record"));
		parameters.put("LOTE", Labels.getLabel("batch.record"));
		parameters.put("QUANTIDADE", Labels.getLabel("amount.record"));
		parameters.put("VALIDADE", Labels.getLabel("expiration.date"));
		
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(List<Batch> lotes) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		
		for (Batch lote : lotes) {
			reportObject = new HashMap<String, Object>();
			reportObject.put("referencia", lote.getProduct().getCode());
			reportObject.put("designacao", lote.getProduct().getName());
			reportObject.put("COLUMN_4",lote.getCode());
			reportObject.put("quantidade", lote.getAmount());
			reportObject.put("data_validade",lote.getExpirationDate());
			reportObjects.add(reportObject);
		}
		return reportObjects;
	}
}
