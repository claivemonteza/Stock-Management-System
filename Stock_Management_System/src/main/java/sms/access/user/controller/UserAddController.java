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
import sms.access.transaction.model.Transaction;
import sms.access.user.model.User;
import sms.language.Language;
import sms.report.UIHelper;
import sms.util.Messages;



/**
 * <code>UserAddController</code> will controller all the functionalities of the view
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
public class UserAddController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 140530111955603215L;

	@Wire
	private Window winNewUser;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtPassword;
	@Wire
	private Textbox txtConfirmPassword;
	@Wire
	private Listbox listProfile;
	@Wire
	private Listbox listLanguage;
	@Wire
	private Listbox listTransactions;

	@WireVariable
	private AccessManager accessManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		list();
	}

	/**
	 * will clean all the fields and close the panel
	 * */	
	public void onClick$btnCancel(Event e) {
		cleanUp();
		winNewUser.detach();
	}

	/**
	 * will will check if the fields have input then will save the user at the database
	 * the clean the fields.
	 * */
	public void onClick$btnSave(Event e) {
		try {
			User user = new User();
			if (checkComponents()) {
				if (checkPassword()) {
					details(user);
					accessManager.save(user);
					Messages.info_center(Labels.getLabel("messages.add"), winNewUser);
					cleanUp();
				}
			}
		} catch (ConstraintViolationException | DataIntegrityViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_right(Labels.getLabel("user.already.exist"), txtUsername);
		}
	}

	/**
	 * will check if the password already exist.
	 * */
	public void onChange$txtPassword(Event e) {
		if (existPassword()) {
			Messages.warning_right(Labels.getLabel("messages.contain"), txtPassword);
		}
	}

	/**
	 * will check if the password already exist.
	 * */
	public void onOK$txtPassword(Event e) {
		if (existPassword()) {
			Messages.warning_right(Labels.getLabel("messages.contain"), txtPassword);
		}
	}
	
	/**
	 * will selected the profile then list the transaction of the profile.
	 * */
	public void onSelect$listProfile(Event e) {
		Profile profile = listProfile.getSelectedItem().getValue();
		UIHelper.buildTransactionListbox(listTransactions, profile.getTransactions());
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
		txtPassword.setValue(null);
		txtConfirmPassword.setValue(null);
		UIHelper.buildProfileListbox(listProfile, accessManager.allProfiles(true));
		UIHelper.buildLanguageListbox(listLanguage);
		UIHelper.clearListbox(listTransactions);
		UIHelper.refreshListBox("listUsers", "searchUser", accessManager.allUsers(true), accessManager.allUsers());
	}

	/**
	 * will put all details of the user at the object.
	 * */
	@SuppressWarnings("unchecked")
	private void details(User user) {
		List<Transaction> transations = UIHelper.getSelectedValuesOnLIstBox(listTransactions);
		user.setPassword(txtPassword.getValue());
		user.setName(txtName.getValue());
		user.setUsername(txtUsername.getValue());
		Profile selectedprofile = listProfile.getSelectedItem().getValue();
		user.setProfile(selectedprofile);
		Language selectedLanguage = ((Language) listLanguage.getSelectedItem().getValue());
		user.setLanguage(selectedLanguage);
		user.setTransactions(Set.copyOf(transations));
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

	/**
	 * will check if the password already exist at the database.
	 * */
	private boolean existPassword() {
		if (accessManager.searchPassword(txtPassword.getValue()) != null) {
			return true;
		}
		return false;
	}

	/**
	 * will check if the fields have the same password.
	 * */
	private boolean checkPassword() {
		if (!UIHelper.field(txtPassword)) {
			if (!UIHelper.field(txtConfirmPassword)) {
				if (txtConfirmPassword.getValue().equals(txtPassword.getValue())) {
					if(!existPassword()) {
						return true;
					}else {
						Messages.warning_right(Labels.getLabel("messages.contain"), txtPassword);
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
