package sms.access.user.controller;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;

import sms.ManagerFactory;
import sms.access.AccessManager;
import sms.access.user.model.User;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Messages;

/**
 * <code>UserListController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see User
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class UserListController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 140530111955603215L;

	@Wire
	private Combobox cbx_search;
	@Wire
	private Checkbox showInactive;
	@Wire
	private Listbox listbox;

	@WireVariable
	private AccessManager accessManager;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		list();
	}

	
	/**
	 * will open a panel the create a new user.
	 * */
	@Listen("onClick = #btnNew")
	public void add() {
		sendComponents();
		Executions.createComponents("accesscontrol/user/user-add.zul", null, null);
		/*SessionHelper.getMainController().createNewTab(
				Labels.getLabel("add.user"),
				"accesscontrol/user/user-add.zul");*/
	}
	
	/**
	 * will reset the password of the user that been selected.
	 * */
	@Listen("onClick = #btnReset")
	public void resetPassword() {
		try {
			User userSelected = listbox.getSelectedItem().getValue();
			accessManager.resetPassword(userSelected);
			Messages.info_center(Labels.getLabel("password.changed"), listbox);
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.user"), listbox);
		}
	}

	/**
	 * will delete the user that been selected.
	 * */
	@Listen("onClick = #btnDelete")
	public void delete() {
		try {
			User userSelected = listbox.getSelectedItem().getValue();
			accessManager.delete(userSelected);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			list();
		} catch (NullPointerException | IllegalArgumentException ex) {
			Messages.warning_center(Labels.getLabel("select.user"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException ex) {
			Messages.error_center(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	/**
	 * will active or inactive the user that been selected.
	 * */
	@Listen("onClick = #btnActiveInactive")
	public void activeInactive(){
		try {
			User userSelected = listbox.getSelectedItem().getValue();
			userSelected.activeInactive();
			accessManager.update(userSelected);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.user"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("user.already.exist"), listbox);
		}
	}

	/**
	 * when checked or unchecked will list of users.
	 * */
	@Listen("onCheck = #showInactive")
	public void showInactive() {
		list();
	}

	/**
	 * will search a list of users that contains the input on the field.
	 * */
	@Listen("onSelect = #cbx_search")
	public void onSelect() {
		search();
	}

	/**
	 * will search a list of users that contains the input on the field.
	 * */
	@Listen("onOK = #cbx_search")
	public void onOk() {
		search();
	}

	/**
	 * will open the panel of modify the details of the user.
	 * */
	@Listen("onDoubleClick = #listbox")
	public void listbox() {
		try {
			User user = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("user", user);
			sendComponents();
			Executions.createComponents("accesscontrol/user/user-edit.zul", null, null);
		/*	SessionHelper.getMainController().createNewTab(
					Labels.getLabel("modify.user"),
					"accesscontrol/user/user-edit.zul");*/
			
		} catch (NullPointerException ex) {
			//Messages.warning_center(Labels.getLabel("select.user"), listbox);
		}
	}

	/**
	 * list of the user
	 * */
	private void list() {
		try {
			auxiliaryBuild(accessManager.allUsers(!showInactive.isChecked()));
			UIHelper.buildCombobox(cbx_search, accessManager.allUsers());
		} catch (NullPointerException e) {
			//Messages.error_center(Labels.getLabel("no.records"), listbox);
		}
	}


	/**
	 * will create a table with a list of users.
	 * 
	 * @param users
	 * */
	private void auxiliaryBuild(List<User> users) {
		UIHelper.buildComplexList(listbox, users);
	}

	/**
	 * will search a list of users that contains the input on the field then input at the table
	 * */
	private void search() {
		try {
			auxiliaryBuild(accessManager.selectedUser((User) cbx_search.getSelectedItem().getValue()));
		} catch (NullPointerException ex) {
			List<User> users = accessManager.findUsers(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null
					&& !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(users);
			} else {
				list();
			}
		}
	}
	
	/**
	 * will put in the session the components of the table and the combo box 
	 * to later update the list and visualize.
	 * */
	public void sendComponents() {
		SessionHelper.setObject("listUsers", listbox);
		SessionHelper.setObject("searchUser", cbx_search);
	}
}
