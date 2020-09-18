package sms.util;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;

import sms.report.SessionHelper;


/**
 * <code>AutoClosableController</code> will close any panel that is open.
 * 
 * @see SessionHelper
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 * */
public abstract class AutoClosableController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 3596275369739954737L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		SessionHelper.getMainController().bindUbindedTabToController(this);
	}
	
	/**
	 * get the present panel and close
	 * */
	protected void close() {
		SessionHelper.getMainController().closeControllersTab(this);
	}
}
