package sms.access.profile.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.access.AccessManager;
import sms.access.profile.model.Profile;
import sms.access.transaction.model.Transaction;
import sms.report.UIHelper;
import sms.util.Messages;

/**
 * <code>ProfileAddController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see Profile
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProfileAddController extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747742758822126389L;

	@Wire
	private Window winNewProfile;
	@Wire
	private Textbox txtName;
	@Wire
	private Listbox listTransactions;

	@WireVariable
	private AccessManager accessManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		UIHelper.buildTransactionListbox(listTransactions, accessManager.allTransactions(true));
	}

	/**
	 * will clean all the fields and close the panel
	 * */	
	public void onClick$btnCancel(Event e) {
		cleanUpFields();
		winNewProfile.detach();
	}

	
	/**
	 * Will check is the name of profile exist
	 * then will save
	 * */
	public void onClick$btnSave(Event e){
		try {
			if (!UIHelper.field(txtName)) {
				Profile profile = new Profile();
				details(profile);
				accessManager.save(profile);
				Messages.info_center(Labels.getLabel("messages.add"), winNewProfile);
				cleanUpFields();
			}
		} catch (ConstraintViolationException | DataIntegrityViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("profile.already.exist"), winNewProfile);
		}
	}

	/**
	 * details of profile
	 * 
	 * @param profile
	 * */
	@SuppressWarnings("unchecked")
	private void details(Profile profile) {
		List<Transaction> transactions = UIHelper.getSelectedValuesOnLIstBox(listTransactions);
		profile.setName(txtName.getValue());
		profile.setTransactions(transactions);
	}

	/**
	 * clean up the components the panel
	 * */
	private void cleanUpFields() {
		txtName.setValue(null);
		UIHelper.buildTransactionListbox(listTransactions, accessManager.allTransactions(true));
		UIHelper.refreshListBox("listProfiles", "searchProfile", accessManager.allProfiles(true), accessManager.allProfiles());
	}

	/*	private void list() {
	Listbox listbox = (Listbox) SessionHelper.takeObject("listProfiles");
	Combobox combobox = (Combobox) SessionHelper.takeObject("searchProfile");
	try {
		UIHelper.buildCombobox(combobox, profileManager.allProfiles(true));
		UIHelper.buildComplexList(listbox, profileManager.allProfiles());
	} catch (NullPointerException e) {
		Messages.warning_center(Labels.getLabel("no.records"), listbox);
	}
}
*/
}
