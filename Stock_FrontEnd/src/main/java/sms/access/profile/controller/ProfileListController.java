package sms.access.profile.controller;

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
import sms.access.profile.model.Profile;
import sms.report.SessionHelper;
import sms.report.UIHelper;
import sms.util.AutoClosableController;
import sms.util.Messages;

/**
 * <code>ProfileListController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see Profile
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ProfileListController extends AutoClosableController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * Will open panel to create a new profile
	 * */
	@Listen("onClick = #btnNew")
	public void add() {
		sendComponents();
		Executions.createComponents("accesscontrol/profile/profile-add.zul", null, null);
	}
	
	
	/**
	 * will delete a profile if selected
	 * */
	@Listen("onClick = #btnDelete")
	public void delete() {
		try {
			Profile profile = listbox.getSelectedItem().getValue();
			accessManager.delete(profile);
			Messages.info_center(Labels.getLabel("messages.delete"), listbox);
			list();
		} catch (NullPointerException | IllegalArgumentException ex) {
			Messages.warning_center(Labels.getLabel("select.profile"), listbox);
		} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException | BatchUpdateException ex) {
			Messages.error_center(Labels.getLabel("cant.be.deleted"), listbox);
		}
	}

	/**
	 * will active or inactive profile if selected
	 * */
	@Listen("onClick = #btnActiveInactive")
	public void activeInactive() {
		try {
			Profile profile = listbox.getSelectedItem().getValue();
			profile.activeInactive();
			accessManager.update(profile);
			list();
		} catch (NullPointerException ex) {
			Messages.warning_center(Labels.getLabel("select.profile"), listbox);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| SQLIntegrityConstraintViolationException ex) {
			Messages.warning_center(Labels.getLabel("profile.already.exist"), listbox);
		}
	}

	/**
	 * if checked will show all inactive profile
	 * */
	@Listen("onCheck = #showInactive")
	public void showInactive() {
		list();
	}

	/**
	 * will select the profile and will listed on the listbox 
	 * */
	@Listen("onSelect = #cbx_search")
	public void onSelect() {
		search();
	}

	/**
	 * will search every input after click enter
	 * */
	@Listen("onOK = #cbx_search")
	public void onOK() {
		search();
	}

	/**
	 * will open a panel to modify profile after doubleclick
	 * and if a profile is selected.
	 * */
	@Listen("onDoubleClick = #listbox")
	public void fillSelection() {
		try {
			Profile profile = listbox.getSelectedItem().getValue();
			SessionHelper.setObject("profile", profile);
			sendComponents();
			Executions.createComponents("accesscontrol/profile/profile-edit.zul", null, null);
		} catch (NullPointerException ex) {
		}
	}

	/**
	 * Will list a profile selected or will search every input
	 * that have in profile name 
	 * */
	private void search() {
		try {
			auxiliaryBuild(accessManager.selectedProfile((Profile) cbx_search.getSelectedItem().getValue()));
		} catch (NullPointerException ex) {
			List<Profile> profiles = accessManager.findProfiles(cbx_search.getText());
			if (UIHelper.checkFilledFields(cbx_search) != null && !UIHelper.checkFilledFields(cbx_search).equals("")) {
				auxiliaryBuild(profiles);
			} else {
				list();
			}
		}
	}

	/**
	 * will show in the listbox and combobox a list of profile
	 * */
	private void list() {
		try {
			UIHelper.buildCombobox(cbx_search, accessManager.allProfiles());
			auxiliaryBuild(accessManager.allProfiles(!showInactive.isChecked()));
		} catch (NullPointerException e) {
			Messages.error_center(Labels.getLabel("no.records"), listbox);
		}
	}

	
	/**
	 * will build a listbox with a list of profile
	 * 
	 * @param profiles
	 * */
	private void auxiliaryBuild(List<Profile> profiles) {
		UIHelper.buildComplexList(listbox, profiles);
	}

	/**
	 * save components to be use after
	 * */
	public void sendComponents() {
		SessionHelper.setObject("listProfiles", listbox);
		SessionHelper.setObject("searchProfile", cbx_search);
	}

}
