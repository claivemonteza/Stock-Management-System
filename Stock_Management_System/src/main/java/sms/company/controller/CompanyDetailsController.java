/**
 * 
 */
package sms.company.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.company.manager.CompanyManager;
import sms.company.model.Company;

/**
 * <code>CompanyDetailsController</code> will controller all the functionalities of the view
 * 
 * @see Company
 * @see CompanyManager
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class CompanyDetailsController extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3987146278482806251L;

	@Wire
	private Window winCompanyDetails;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtNuit;
	@Wire
	private Textbox txtAddress;
	@Wire
	private Textbox txtCity;
	@Wire
	private Textbox txtProvince;
	@Wire
	private Textbox txtCountry;
	@Wire
	private Textbox txtPhone;
	@Wire
	private Textbox txtMobile;
	@Wire
	private Textbox txtFax;
	@Wire
	private Textbox txtEmail;
	
	@WireVariable
	private CompanyManager companyManager;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		companyManager = ManagerFactory.getCompanyManager();
	}
}
