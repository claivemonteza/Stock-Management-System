package sms.stock.provider.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.stock.StockManager;
import sms.stock.provider.model.Provider;
import sms.util.Messages;

/**
 * The class that will controller all the functionalities of the view
 * 
 * @see StockManager
 * @see Provider
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProviderEditController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3427591684573675888L;

	@Wire
	private Window winEditProvider;
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
	private StockManager stockManager;

	private Provider providerSelected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		stockManager = ManagerFactory.getStockManager();
		fillFields();
	}

	public void onClick$btnSave(Event e) {
		try {
			if (checkComponents()) {
				details(providerSelected);
				stockManager.update(providerSelected);
				Messages.info_center(Labels.getLabel("messages.update"), winEditProvider);
				cleanUp();
				UIHelper.refreshListBox("listProviders", "searchProvider", stockManager.allProviders(true), stockManager.allProviders());
			}
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.provider"), winEditProvider);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("profile.already.exist"), winEditProvider);
		}
	}

	public void onClick$btnCancel(Event e) {
		cleanUp();
	}

	private void cleanUp() {
		txtName.setValue(null);
		txtNuit.setValue(null);
		txtAddress.setValue(null);
		txtCity.setValue(null);
		txtProvince.setValue(null);
		txtCountry.setValue(null);
		txtPhone.setValue(null);
		txtMobile.setValue(null);
		txtFax.setValue(null);
		txtEmail.setValue(null);
		winEditProvider.detach();
	}

	private void details(Provider provider) {
		provider.setAddress(txtAddress.getValue());
		provider.setCity(txtCity.getValue());
		provider.setProvince(txtProvince.getValue());
		provider.setCountry(txtCountry.getValue());
		provider.setTelephone(txtPhone.getValue());
		provider.setMobile(txtMobile.getValue());
		provider.setFax(txtFax.getValue());
		provider.setEmail(txtEmail.getValue());
	}

	private void fillFields() {
		providerSelected = (Provider) SessionHelper.takeObject("provider");
		txtName.setValue(providerSelected.getName());
		txtNuit.setValue(providerSelected.getNuit());
		txtAddress.setValue(providerSelected.getAddress());
		txtCity.setValue(providerSelected.getCity());
		txtProvince.setValue(providerSelected.getProvince());
		txtCountry.setValue(providerSelected.getCountry());
		txtPhone.setValue(providerSelected.getTelephone());
		txtMobile.setValue(providerSelected.getMobile());
		txtFax.setValue(providerSelected.getFax());
		txtEmail.setValue(providerSelected.getEmail());

	}

	private boolean checkComponents() {
		if (!UIHelper.field(txtName)) {
			return true;
		}
		return false;
	}

	/*private boolean checkProvider(Provider provider) {
		if (providerManager.findByName(provider.getName()) == null) {
			if (providerManager.findByNuit(provider.getNuit()) == null) {
				return true;
			} else {
				Messages.warning_right(Labels.getLabel("provider.nuit.already.exist"), txtNuit);
			}
		} else {
			Messages.warning_right(Labels.getLabel("provider.name.already.exist"), txtName);
		}
		return false;
	}
*/
}
