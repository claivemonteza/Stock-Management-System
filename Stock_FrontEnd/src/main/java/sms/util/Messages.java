package sms.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;


/**
 *	<code>Messages</code> will create the bubble for different type of messages.
 *
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 * */
public class Messages {
	
	/**
	 *  this method will create a bubble of information and will appear at the left side
	 *  of the component.
	 * */
	public static void info_left(String message, Component component){
		Clients.showNotification(message, "info",component, "start_center", 3000);
	}

	/**
	 *  this method will create a bubble of information and will appear at the middle
	 *  of the component.
	 * */
	public static void info_center(String message, Component component){
		Clients.showNotification(message, "info",component, "middle_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of information and will appear at the right side
	 *  of the component.
	 * */
	public static void info_right(String message, Component component){
		Clients.showNotification(message, "info",component, "end_center", 3000);
	}
	
	
	/**
	 *  this method will create a bubble of warning and will appear at the left side
	 *  of the component.
	 * */
	public static void warning_left(String message, Component component){
		Clients.showNotification(message, "warning",component, "start_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of warning and will appear at the middle
	 *  of the component.
	 * */
	public static void warning_center(String message, Component component){
		Clients.showNotification(message, "warning",component, "middle_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of warning and will appear at the right side
	 *  of the component.
	 * */
	public static void warning_right(String message, Component component){
		Clients.showNotification(message, "warning",component, "end_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of error and will appear at the left side
	 *  of the component.
	 * */
	public static void error_left(String message, Component component){
		Clients.showNotification(message, "error",component, "start_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of error and will appear at the middle
	 *  of the component.
	 * */
	public static void error_center(String message, Component component){
		Clients.showNotification(message, "error",component, "middle_center", 3000);
	}
	
	/**
	 *  this method will create a bubble of error and will appear at the right side
	 *  of the component.
	 * */
	public static void error_right(String message, Component component){
		Clients.showNotification(message, "error",component, "end_center", 3000);
	}
}
