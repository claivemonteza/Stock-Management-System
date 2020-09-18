package sms.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.zkoss.util.resource.Labels;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import sms.access.user.model.User;

/**
 * The class that will create the report
 * 
 * @author Claive Monteza
 *
 */
public class Report {

	private Map<String, Object> parameters;
	private List<Map<String, Object>> reportObjects;
	private String jasperFileName;
	private User user;

	public Report(Map<String, Object> parameters, List<Map<String, Object>> reportObjects, String jasperFileName,
			User user) {
		super();
		this.parameters = parameters;
		this.reportObjects = reportObjects;
		this.jasperFileName = jasperFileName;
		this.user = user;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InputStream getStream() {
		try {
			List list = reportObjects;
			JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(list);
			ClassPathResource reportFile = new ClassPathResource(jasperFileName);
			ClassPathResource logo = new ClassPathResource("logotipo.png");
			addDefaultParameters(logo,parameters);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getInputStream(), parameters,mapCollectionDataSource);
			byte[] content = JasperExportManager.exportReportToPdf(jasperPrint);
			return new ByteArrayInputStream(content);
		} catch (JRException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void addDefaultParameters(ClassPathResource logo, 
			Map<String, Object> parameters) throws IOException {
		parameters.put("IMAGE", logo.getInputStream());
		parameters.put("USER", Labels.getLabel("user.record")+": ");
		parameters.put("USER_NAME", user.getName());
	}
	
	public String getName() {
		return (String) parameters.get("REPORT_NAME");
	}

}
