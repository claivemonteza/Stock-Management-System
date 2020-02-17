package sms.util;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import sms.access.user.model.User;
import sms.report.SessionHelper;


/** 
 * <code>AuthenticationValidatorInitiator</code> will make that the authentication for the  @See User.
 * 
 * @author Claive Monteza
 *  
 * @version 1.0
 * @since 1.6
 * */
public class AuthenticationValidatorInitiator implements Initiator {

	/**
	 * will check if the user is legal, if not will redirect to the page of login
	 * */
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		User user = SessionHelper.getUser();
		if (user == null) {
			Executions.sendRedirect("../login.zul");
		}
	}

}
