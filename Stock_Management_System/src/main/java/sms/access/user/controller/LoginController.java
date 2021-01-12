package sms.access.user.controller;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
 * <code>LoginController</code> will controller all the functionalities of the view
 * 
 * @see AccessManager
 * @see SessionHelper
 * @see User
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
public class LoginController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044114335592668296L;

	@Wire
	private Window winlogin;
	@Wire
	private Button btnLogin;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtPassword;
	
	@WireVariable
	private AccessManager accessManager;
	
	private boolean status = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		//hide(true);
		accessManager = ManagerFactory.getAccessManager();
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
	 * will check the authentication of the user if this user is allow
	 * will close and open the main page.
	 * */
	private void login() {
		try {
			User applicationUser = accessManager.authentication(txtUsername.getValue(), txtPassword.getValue());
			if (applicationUser != null) {
				SessionHelper.setUser(applicationUser);
				SessionHelper.setStatus(status);
				SessionHelper.lingua(applicationUser.getLanguage());
				winlogin.detach();
				Executions.sendRedirect("zul/main.zul");
			} else {
				Messages.error_right(Labels.getLabel("incorrect.user.or.password"), btnLogin);
			}
		} catch (EncryptionOperationNotPossibleException e) {
			Messages.error_right(Labels.getLabel("incorrect.user.or.password"), btnLogin);
		}
	}
	
}
