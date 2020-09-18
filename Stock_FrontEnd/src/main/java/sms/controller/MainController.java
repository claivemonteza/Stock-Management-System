package sms.controller;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import sms.access.transaction.model.Transaction;
import sms.access.user.model.User;
import sms.item.batch.report.ExpiringBatchesReportController;
import sms.report.SessionHelper;
import sms.util.Messages;

/**
 * <code>MainController</code> will controller all the functionalities of the view
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 **/
public class MainController extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Tabbox contentTabbox;
	@Wire
	private Menu sistema;
	@Wire
	private Menu reportsMenu;
	@Wire
	private Menu stockMenu;
	@Wire
	private Menu managerMenu;
	@Wire
	private Menu guestMenu;
	@Wire
	private Label login;
	@Wire
	private Label profile;
	@Wire
	private Tab unbindedTab;

	private boolean status;

	@WireVariable
	private Map<SelectorComposer<Component>, Tab> controllerToTab = new HashMap<SelectorComposer<Component>, Tab>();
	private Map<String, MenuUsage> startCodeToMenu;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		wellcome();
	}

	/**
	 * Log out on the system
	 * 
	 **/
	@Listen("onClick = #logoutMenuItem")
	public void onClick$logoutMenuItem(Event e) {
		Sessions.getCurrent().invalidate();
		SessionHelper.logout();
		Executions.sendRedirect("/");
	}

	/**
	 * will open the panel to change the password
	 *
	 **/
	@Listen("onClick =#changePasswordMenuItem")
	public void onClick$changePasswordMenuItem(Event e) {
		Executions.createComponents("accesscontrol/user/change-password.zul", null, null);
	}

	/**
	 * will lock the system
	 **/
	@Listen("onClick =#lockMenuItem")
	public void onClick$lockMenuItem(Event e) {
		status = true;
		SessionHelper.setStatus(status);
		checkLock(status);
	}

	/**
	 * Message of wellcome
	 * 
	 * @param user
	 **/
	private void wellcome() {
		try {
			status = SessionHelper.getStatus();
			if (status) {
				checkLock(status);
			} else {
				User user = SessionHelper.getUser();
				if (user != null) {
					showWarnings(user);
					guestMenu.setLabel(user.getName());
					Messages.info_right(Labels.getLabel("well.come"), guestMenu);
					login.setValue(Labels.getLabel("login.header", new Object[] { user.getUsername() }));
					profile.setValue(Labels.getLabel("profile.header", new Object[] { user.getProfile().getName() }));
					buildMenu(user);
				}
			}
		} catch (NullPointerException e) {
			Executions.sendRedirect("/");
		}
	}

	/**
	 * this method will build a menu if all transaction of the @see user
	 * 
	 * @param user
	 **/
	// TODO Make new menu that is flexible When update the transaction of the user
	public void buildMenu(User user) {
		startCodeToMenu = new HashMap<String, MenuUsage>();
		startCodeToMenu.put("1", new MenuUsage(sistema));
		startCodeToMenu.put("2", new MenuUsage(stockMenu));
		startCodeToMenu.put("3", new MenuUsage(managerMenu));
		startCodeToMenu.put("4", new MenuUsage(reportsMenu));

		List<Transaction> transactions = new ArrayList<Transaction>(List.copyOf(user.getTransactions()));
		transactions = sort(transactions, on(Transaction.class).getCode());

		for (final Transaction transaction : transactions) {
			if (transaction.getUrl() != null) {
				String startCode = transaction.getCode().substring(0, 1);
				MenuUsage menuUsage = startCodeToMenu.get(startCode);
				Menu menu = menuUsage.getMenu();
				Menuitem menuitem = new Menuitem(Labels.getLabel(transaction.getName()));
				menuitem.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						createNewTab(Labels.getLabel(transaction.getName()), transaction.getUrl());
					}
				});
				menu.getMenupopup().appendChild(menuitem);
				menuUsage.setUsed(true);
			}
		}
		Collection<MenuUsage> menuUsages = startCodeToMenu.values();
		for (MenuUsage menuUsage : menuUsages) {
			if (!menuUsage.isUsed()) {
				menuUsage.getMenu().detach();
			}
		}
		SessionHelper.setMainController(this);
		
	}

	/**
	 * clean all the components of menu bar
	 *
	 **/
	public void cleanUpMenu() {
		startCodeToMenu.clear();
		sistema.getChildren().clear();
		stockMenu.getChildren().clear();
		managerMenu.getChildren().clear();
		reportsMenu.getChildren().clear();
		Menupopup menupopup = new Menupopup();
		sistema.appendChild(menupopup);
		menupopup = new Menupopup();
		stockMenu.appendChild(menupopup);
		menupopup = new Menupopup();
		managerMenu.appendChild(menupopup);
		menupopup = new Menupopup();
		reportsMenu.appendChild(menupopup);
	}

	/**
	 * report of all batch that have expiring date
	 * 
	 * @param user
	 **/
	private void showWarnings(User user) {
		if (user.isTransactionAccessible(Transaction.EXPIRING_PRODUCTS_REPORT)) {
			ExpiringBatchesReportController.generateReport();
		}
	}

	/**
	 * will create a new tab
	 * 
	 * @param name
	 * @param url
	 **/
	public void createNewTab(String name, String url) {
		Tab tab = new Tab(name);
		tab.setClosable(true);
		Include include = new Include();
		include.setSrc(url);
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.appendChild(include);
		contentTabbox.getTabs().appendChild(tab);
		contentTabbox.getTabpanels().appendChild(tabpanel);
		tab.setSelected(true);
		unbindedTab = tab;
	}

	/**
	 * will add a new tab at tabbox and will view
	 * 
	 * @param controller
	 **/
	public void bindUbindedTabToController(SelectorComposer<Component> controller) {
		controllerToTab.put(controller, unbindedTab);
		unbindedTab = null;
	}

	/**
	 * will close the tab and remove on the tabbox
	 * 
	 * @param controller
	 **/
	public void closeControllersTab(SelectorComposer<Component> controller) {
		controllerToTab.get(controller).close();
		controllerToTab.remove(controller);
	}

	public void checkLock(boolean status) {
		if (status) {
			Executions.createComponents("accesscontrol/user/lock.zul", null, null);
		}
	}
}
