package sms.access.user.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

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
import sms.access.user.model.User;
import sms.language.Language;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.Messages;

/**
 * <code>UserEditController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see User
 * @see Profile
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class UserEditController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 140530111955603215L;

	@Wire
	private Window winEditUser;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Listbox listProfile;
	@Wire
	private Listbox listLanguage;
	@Wire
	private Listbox listTransactions;

	@WireVariable
	private AccessManager accessManager;

	private User userSelected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		list();
		fillFields();
	}
	
	/**
	 * will clean all the fields and close the panel
	 * */	
	public void onClick$btnCancel(Event e) {
		cleanUp();
	}

	/**
	 * will will check if the fields have input then will save the user at the database
	 * the clean the fields.
	 * */
	public void onClick$btnSave(Event e) {
		try {
			if (checkComponents()) {
				details(userSelected);
				accessManager.update(userSelected);
				Messages.info_center(Labels.getLabel("messages.update"), winEditUser);
				crossCheck(userSelected); 
				cleanUp();
				//Executions.sendRedirect("main.zul");
			}
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("user.already.exist"), winEditUser);
		}
	}

	/**
	 * will selected the profile then list the transaction of the profile.
	 * */
	public void onSelect$listProfile(Event e) {
		try {
			Profile profile = listProfile.getSelectedItem().getValue();
			UIHelper.buildTransactionListbox(listTransactions, profile.getTransactions());
			UIHelper.setSelectedListOfValuesOnListbox(listTransactions, List.copyOf(userSelected.getTransactions()));
		} catch (NullPointerException ex) {
			
		}
	}

	/**
	 * will check if the user is the same user in the session
	 * then will check if have same changes on the list of transactions.
	 * 
	 * @param user
	 * */
	private void crossCheck(User user) {
		User user2 = SessionHelper.getUser();
		if(user2.equals(user)) {
			SessionHelper.setUser(user);
			UIHelper.buildMenu(userSelected);
		}
		
	}
	
	/**
	 * will show a list of profile and language.
	 * */
	private void list() {
		UIHelper.buildProfileListbox(listProfile, accessManager.allProfiles(true));
		UIHelper.buildLanguageListbox(listLanguage);
	}

	
	/**
	 * will clean all the fields
	 * */
	private void cleanUp() {
		txtName.setValue(null);
		txtUsername.setValue(null);
		userSelected = null;
		UIHelper.buildProfileListbox(listProfile, accessManager.allProfiles(true));
		UIHelper.buildLanguageListbox(listLanguage);
		UIHelper.clearListbox(listTransactions);
		UIHelper.refreshListBox("listUsers", "searchUser", accessManager.allUsers(true), accessManager.allUsers());
		winEditUser.detach();
	}

	/**
	 * fill all the fields with details of the user
	 * */
	private void fillFields() {
		userSelected = (User) SessionHelper.takeObject("user");
		txtName.setValue(userSelected.getName());
		txtUsername.setValue(userSelected.getUsername());
		UIHelper.buildTransactionListbox(listTransactions, userSelected.getProfile().getTransactions());
		UIHelper.setSelectedValuesOnListbox(listLanguage, userSelected.getLanguage());
		UIHelper.setSelectedValuesOnListbox(listProfile, userSelected.getProfile());
		UIHelper.setSelectedListOfValuesOnListbox(listTransactions, List.copyOf(userSelected.getTransactions()));
	}

	
	/**
	 * will put all details of the user at the object.
	 * 
	 * @param user
	 * */
	@SuppressWarnings("unchecked")
	private void details(User user) {
		user.setName(txtName.getValue());
		user.setUsername(txtUsername.getValue());
		Profile selectedprofile = listProfile.getSelectedItem().getValue();
		user.setProfile(selectedprofile);
		Language selectedLanguage = ((Language) listLanguage.getSelectedItem().getValue());
		user.setLanguage(selectedLanguage);
		user.setTransactions(Set.copyOf(UIHelper.getSelectedValuesOnLIstBox(listTransactions)));
	}

	/**
	 * will check if the field have input.
	 * */
	private boolean checkComponents() {
		if (!UIHelper.field(txtName)) {
			if (!UIHelper.field(txtUsername)) {
				if (!UIHelper.selection(listProfile)) {
					if (!UIHelper.selection(listLanguage)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*	private void list() {
	Listbox listbox = (Listbox) Sessions.getCurrent().getAttribute("listUsers");
	Combobox combobox = (Combobox) Sessions.getCurrent().getAttribute("searchUser");
	try {
		UIHelper.buildComplexList(listbox, userManager.allUsers(true));
		UIHelper.buildCombobox(combobox, userManager.allUsers());
	} catch (NullPointerException e) {
		Messages.warning_center(Labels.getLabel("no.records"), listbox);
	}
}*/
}
