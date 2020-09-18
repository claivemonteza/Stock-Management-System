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
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.Messages;

/**
 * <code>ProfileEditController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see Profile
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProfileEditController extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7123039554100104160L;

	@Wire
	private Window winEditProfile;
	@Wire
	private Textbox txtName;
	@Wire
	private Listbox listTransactions;

	@WireVariable
	private AccessManager accessManager;
	@WireVariable
	private Profile selectedProfile;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		UIHelper.buildTransactionListbox(listTransactions, accessManager.allTransactions(true));
		fillFields();
	}

	/**
	 * will clean all the fields and close the panel
	 * */
	public void onClick$btnCancel(Event e){
		cleanUpFields();
	}
	

	/**
	 * Will check is the name of profile exist
	 * then will update
	 * */
	public void onClick$btnSave(Event e){
		try {
			if (!UIHelper.field(txtName)) {
				details(selectedProfile);
				accessManager.update(selectedProfile);
				Messages.info_center(Labels.getLabel("messages.update"), winEditProfile);
				cleanUpFields();
			}
		} catch (ConstraintViolationException | DataIntegrityViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("profile.already.exist"), winEditProfile);
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
	 * clean up the components and close the panel
	 * */
	private void cleanUpFields() {
		txtName.setValue(null);
		selectedProfile = null;
		UIHelper.buildTransactionListbox(listTransactions, accessManager.allTransactions(true));
		UIHelper.refreshListBox("listProfiles", "searchProfile", accessManager.allProfiles(true), accessManager.allProfiles());
		winEditProfile.detach();
	}

	/**
	 * field the componentes
	 * */
	private void fillFields() {
		try {
			selectedProfile = (Profile) SessionHelper.takeObject("profile");
			txtName.setValue(selectedProfile.getName());
			UIHelper.setSelectedListOfValuesOnListbox(listTransactions, selectedProfile.getTransactions());
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.profile"), winEditProfile);
			winEditProfile.detach();
		}
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
