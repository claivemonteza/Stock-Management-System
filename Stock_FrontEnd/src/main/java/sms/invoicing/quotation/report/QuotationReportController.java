package sms.invoicing.quotation.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

import sms.ManagerFactory;
import sms.invoicing.InvoicingManager;
import sms.invoicing.item.model.SaleItem;
import sms.invoicing.quotation.model.Quotation;
import sms.report.Report;
import sms.report.SessionHelper;
import sms.report.UIHelper;

/**
 * This class will input all data into the report
 * 
 * @author Claive Monteza
 *
 */
public class QuotationReportController {
	private static InvoicingManager invoicingManager = ManagerFactory.getInvoicingManager();
	
	public static void generateReport(Quotation cotacao) {
		List<SaleItem> itens = invoicingManager.allItems(cotacao);
		if (!itens.isEmpty()) {
			Report report = new Report(generateParameters(), generateReportData(cotacao), "cotacao.jasper",
					SessionHelper.getUser());
			
			String name = Labels.getLabel("quotation.record")+cotacao.getDate()+".pdf"; 
			UIHelper.generateReport(report,name);
		} else {
			throw new WrongValueException("no.report.data");
		}
	}

	private static Map<String, Object> generateParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("CLIENTE",Labels.getLabel("client.record"));
		parameters.put("NUIT",Labels.getLabel("nuit.record"));
		parameters.put("REPORT_NAME", Labels.getLabel("quotation.record"));
		parameters.put("VALIDO", Labels.getLabel("expiration.date"));
		parameters.put("DATA",Labels.getLabel("date.record"));
		parameters.put("REFERENCIA", Labels.getLabel("code.record"));
		parameters.put("DESIGNACAO", Labels.getLabel("name.record"));
		parameters.put("LOTE", Labels.getLabel("batch.record"));
		parameters.put("PRECO_VENDA", Labels.getLabel("price.record"));
		parameters.put("QUANTIDADE",Labels.getLabel("amount.record"));
		parameters.put("IVA", Labels.getLabel("vat.record"));
		parameters.put("TOTAL_UNITARIO", Labels.getLabel("total.unit"));
		parameters.put("SUBTOTAL", Labels.getLabel("subtotal.record"));
		parameters.put("VALOR_IVA", Labels.getLabel("vat.record"));
		parameters.put("TOTAL", Labels.getLabel("total.record"));
		return parameters;
	}

	private static List<Map<String, Object>> generateReportData(Quotation cotacao) {
		List<Map<String, Object>> reportObjects = new ArrayList<Map<String, Object>>();
		Map<String, Object> reportObject = null;
		List<SaleItem> itens = invoicingManager.allItems(cotacao);

		for (SaleItem item : itens) {
			reportObject = new HashMap<String, Object>();

			reportObject.put("designacao", cotacao.getClient().getName());
			reportObject.put("nuit", cotacao.getClient().getNuit());
			reportObject.put("referencia",cotacao.getCode());
			reportObject.put("data_validade",cotacao.getDueDate());
			reportObject.put("data", cotacao.getDate());
			reportObject.put("COLUMN_3",item.getBatch().getProduct().getCode());
			reportObject.put("COLUMN_5", item.getBatch().getProduct().getName());
			reportObject.put("COLUMN_10", item.getBatch().getCode());
			reportObject.put("preco_venda", item.getBatch().getSalePrice());
			reportObject.put("quantidade", item.getAmount());
			reportObject.put("taxa_iva", item.getBatch().getProduct().getVat());
			reportObject.put("total_unitario", item.getUnitTotal());

			reportObjects.add(reportObject);
		}

		reportObject.put("subTotal", cotacao.getSubTotal());
		reportObject.put("total_iva", cotacao.getTotalVat());
		reportObject.put("total", cotacao.getTotal());
		return reportObjects;
	}
}
