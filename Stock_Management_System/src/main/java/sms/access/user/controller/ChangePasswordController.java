package sms.access.user.controller;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.access.AccessManager;
import sms.access.user.model.User;
import sms.util.Messages;


/**
 * <code>ChangePasswordController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see User
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class ChangePasswordController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7777494032672614736L;
	@Wire
	private Window win;
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtProfile;
	@Wire
	private Textbox txtPassword;
	@Wire
	private Textbox txtNewPassword;
	@Wire
	private Textbox txtConfirmPassword;
	
	@WireVariable
	private AccessManager accessManager;
	private User userSelected;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		userSelected = (User) Sessions.getCurrent().getAttribute("util");
		fillFields(userSelected);
	}
	
	/**
	 * will check and change the password
	 * */
	public void onClick$btnSave(Event e) {
		if(txtNewPassword.getValue().equals(txtConfirmPassword.getValue())){
			if(accessManager.changePassword(userSelected, txtNewPassword.getValue())){
				Messages.info_center(Labels.getLabel("password.changed"), win);
				cleanUp();
			}else
				Messages.error_right(Labels.getLabel("wrong.password"),txtPassword);
		}else
			Messages.error_right(Labels.getLabel("different.passwords"),txtPassword);
	}
	
	/**
	 * will clean the fields and close the panel
	 * */
	public void onClick$btnCancel(Event e) {
		cleanUp();
		win.detach();
	}
	
	/**
	 * details of user
	 * 
	 * @param user
	 * */
	private void fillFields(User user){
		txtName.setValue(user.getName());
		txtUsername.setValue(user.getUsername());
		txtProfile.setValue(user.getProfile().getName());
	} 
	
	/**
	 * Clean up all the components
	 * */
	private void cleanUp() {
		txtPassword.setValue(null);
		txtConfirmPassword.setValue(null);
		txtNewPassword.setValue(null);
	}
}
