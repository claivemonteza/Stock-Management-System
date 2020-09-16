package sms.util;

import java.util.List;
import java.util.Set;

/**
 * <code>Verifications</code> will make all the check.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public class Verifications {

	/**
	 * This method will check if the list have object duplication.
	 * 
	 * @param list
	 * @return
	 */
	public static List<?> duplication(List<?> list) {
		Set<?> objects = Set.copyOf(list);
		return List.copyOf(objects);
	}

	/**
	 * This method will check if the list is empty or not.
	 * 
	 * @param list
	 * @return
	 */
	public static boolean emptyOrNull(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will check in the begin if have empty space and delete.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean characters(String str) {
		boolean b;
		str = str.replaceAll("^\\s+", "");/* Will check in the begin if have empty space or not if have will remove the space */
		b = str.matches(".*");/* none or many */
		return b;
	}

	/**
	 * This method will check if the password have 8 characters and if the 
	 * have letters, number and special characters.
	 * 
	 * @param password
	 * @return
	 */
	public static boolean password(String password) {
		return password.matches("^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])[a-zA-Z_0-9@#$%^&+=]{8,}+");
	}

}
