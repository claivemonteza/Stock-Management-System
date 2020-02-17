package sms.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.Locales;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import sms.access.user.model.User;
import sms.controller.MainController;
import sms.language.Language;

/**
 * <code>SessionHelper</code> will transport a object for any page that needed too use the object.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 * */
public class SessionHelper {

	private static Map<String, Object> objects = new HashMap<String, Object>();

	public static User getUser() {
		return (User) objects.get("login");
	}

	public static void setUser(User user) {
		setObject("login", user);
	}
	
	public static boolean getStatus() {
		return (boolean) objects.get("status");
	}

	public static void setStatus(boolean status) {
		setObject("status", status);
	}
	
	public static void logout() {
		objects.remove("login");
	}

	public static void setObject(String key, Object object) {
		objects.put(key, object);
	}

	public static Object takeObject(String key) {
		Object object = objects.get(key);
		objects.remove(key);
		return object;
	}

	public static MainController getMainController() {
		return (MainController) Sessions.getCurrent().getAttribute("mainController");
	}

	public static void setMainController(MainController mainController) {
		Sessions.getCurrent().setAttribute("mainController", mainController);
	}
	
	
	public static void lingua(Language language) {
		// Sessions.getCurrent().setAttribute("org.zkoss.web.preferred.locale", language.getLocale());
		Sessions.getCurrent().setAttribute(Attributes.PREFERRED_LOCALE, language.getLocale());
		//Library.setProperty(Attributes.PREFERRED_LOCALE, language.getVariant());
		try {
			Clients.reloadMessages(language.getLocale());
			Locales.setThreadLocal(language.getLocale());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Executions.sendRedirect(null);
	}
}
