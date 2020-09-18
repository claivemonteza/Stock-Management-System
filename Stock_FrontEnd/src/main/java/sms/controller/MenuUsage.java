package sms.controller;

import org.zkoss.zul.Menu;

/**
 * this class will 
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
class MenuUsage {
	private Menu menu;
	private boolean used;

	public MenuUsage(Menu menu) {
		this.menu = menu;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}

}
