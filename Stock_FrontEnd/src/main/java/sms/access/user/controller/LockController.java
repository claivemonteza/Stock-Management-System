package sms.access.user.controller;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sms.ManagerFactory;
import sms.access.AccessManager;
import sms.access.user.model.User;
import sms.report.SessionHelper;
import sms.util.Messages;

/**
 * <code>LockController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see User
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class LockController extends GenericForwardComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	private Window winLock;
	@Wire
	private Button btnLogin;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtPassword;

	@WireVariable
	private AccessManager accessManager;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		accessManager = ManagerFactory.getAccessManager();
		fillField();
	}
	
	/**
	 * will check the authentication of the user and password when finish
	 * put the password.
	 * */
	public void onChange$txtPassword(Event e) {
		login();
	}

	/**
	 * will check the authentication of the user and password when finish
	 * put the password.
	 * */
	public void onOK$txtPassword(Event e) {
		login();
	}

	/**
	 * will check the authentication of the user and password when click
	 * the button login.
	 * */
	public void onClick$btnLogin(Event e) {
		login();
	}
	
	
	/**
	 * fill the fields if details of the user.
	 * */
	private void fillField() {
		User user = SessionHelper.getUser();
		txtUsername.setValue(user.getUsername());
	}

	/**
	 * will check the authentication of the user if this user is allow
	 * will close and open the main page.
	 * */
	private void login() {
		try {
			User applicationUser = accessManager.authentication(txtUsername.getValue(), txtPassword.getValue());
			if (applicationUser != null) {
				boolean status = SessionHelper.getStatus();
				status = false;
				SessionHelper.setStatus(status);
				winLock.detach();
			} else {
				Messages.error_right(Labels.getLabel("incorrect.user.or.password"), btnLogin);
			}
		} catch (EncryptionOperationNotPossibleException e) {
			Messages.error_right(Labels.getLabel("incorrect.user.or.password"), btnLogin);
		}
	}
}
