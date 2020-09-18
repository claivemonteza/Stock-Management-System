package sms.report;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import sms.access.profile.model.Profile;
import sms.access.transaction.model.Transaction;
import sms.access.user.model.User;
import sms.controller.MainController;
import sms.invoicing.invoice.model.Invoice;
import sms.invoicing.payment.model.ItemPaidBill;
import sms.invoicing.payment.model.PayMethod;
import sms.item.batch.model.Batch;
import sms.item.product.model.Product;
import sms.item.product.model.Unity;
import sms.language.Language;
import sms.stock.request.model.Request;
import sms.util.Extra;
import sms.util.Messages;
import sms.util.Verifications;

/**
 * <code>UIHelper</code> will have methods that create templates will
 * been use to create components in zk. 
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
public class UIHelper {

	
	/**
	 * Will generate any kind report
	 * 
	 * @param report
	 * @param fileName
	 * */
	public static void generateReport(Report report, String fileName) {
		Media media = new AMedia(fileName, "pdf", "application/pdf", report.getStream());
		SessionHelper.setObject("reportToGenerate", media);
		Executions.createComponents("./report/report.zul", null, null);
	}
	
	/**
	 * will build a menu depending on user transactions
	 * 
	 * @param user
	 * */
	public static void buildMenu(User user) {
		MainController main = SessionHelper.getMainController();
		main.cleanUpMenu();
		main.buildMenu(user);
	}

	/**
	 * Will change the label of the button
	 * 
	 * @param obj
	 * @param btn
	 * */
	public static void mudarLabels(Object obj, Button btn) {
		if (obj != null) {
			if (((Extra) obj).isActive()) {
				btn.setLabel(Labels.getLabel("inactive.record"));
			} else {
				btn.setLabel(Labels.getLabel("active.record"));
			}
		} else {
			btn.setLabel(Labels.getLabel("active.inactive"));
		}
	}

	
	/* COMBOBOX */

	/**
	 * Will build a combobox that have inputed a list os objects
	 * 
	 * @param combobox
	 * @param objects
	 * */
	public static void buildCombobox(Combobox combobox, List<?> objects) {
		clearCombobox(combobox);
		combobox.setModel(new ListModelList<>(objects));
	}

	
	/**
	 * will build a combobox and put on a item each object
	 * 
	 * @param combobox
	 * @param objects
	 * */
	public static void buildComboboxAlt(Combobox combobox, List<?> objects) {
		clearCombobox(combobox);
		for (Object object : objects) {
			Comboitem item = new Comboitem();
			item.setValue(object);
			item.setLabel(String.valueOf(object));
			combobox.appendChild(item);
		}
	}
	
	
	/**
	 * will build a combobox and put on each item of the combobox a product
	 * 
	 * @param combobox
	 * @param products
	 * */
	public static void BuildProductCombobox(Combobox combobox, List<Product> products) {
		combobox.getItems().clear();
		for (Product product : products) {
			Comboitem item = new Comboitem();
			item.setStyle("font-size: 16px;font-family: Times New Roman");
			item.setValue(product);
			item.setLabel(product.getName());
			// listitem.appendChild(new Listcell(product.getReferencia()));
			combobox.appendChild(item);
		}
	}
	
	/**
	 * will build a combobox and put on each item of the combobox a product
	 * 
	 * @param combobox
	 * @param products
	 * */
	public static void BuildProductCodeCombobox(Combobox combobox, List<Product> products) {
		combobox.getItems().clear();
		for (Product product : products) {
			Comboitem item = new Comboitem();
			item.setValue(product);
			item.setLabel(product.getCode());
			// listitem.appendChild(new Listcell(product.getReferencia()));
			combobox.appendChild(item);
		}
	}
	
	public static void BuildBankCombobox(Combobox combo, List<String> banks) {
		combo.getItems().clear();
		for (String bank : banks) {
			Comboitem item = new Comboitem();
			item.setValue(bank);
			item.setLabel(bank);
			// listitem.appendChild(new Listcell(product.getReferencia()));
			combo.appendChild(item);
		}
	}

	public static void BuildBatchCombobox(Combobox combo, List<Batch> batches) {
		combo.getItems().clear();
		for (Batch batch : batches) {
			Comboitem listitem = new Comboitem();
			listitem.setValue(batch);
			listitem.setLabel(batch.getCode());
			// listitem.appendChild(new Listcell(batch.getReferencia()));
			combo.appendChild(listitem);
		}
	}
	
	public static void buildUnityCombobox(Combobox combo) {
		combo.getItems().clear();
		Comboitem unity = new Comboitem(Unity.UNITY.getVariant());
		unity.setValue(Unity.UNITY);
		Comboitem cx = new Comboitem(Unity.CX.getVariant());
		cx.setValue(Unity.CX);
		Comboitem table = new Comboitem(Unity.TABLE.getVariant());
		table.setValue(Unity.TABLE);
		Comboitem kg = new Comboitem(Unity.KG.getVariant());
		kg.setValue(Unity.KG);
		Comboitem l = new Comboitem(Unity.L.getVariant());
		l.setValue(Unity.L);
		Comboitem mg = new Comboitem(Unity.MG.getVariant());
		mg.setValue(Unity.MG);
		combo.appendChild(unity);
		combo.appendChild(cx);
		combo.appendChild(table);
		combo.appendChild(kg);
		combo.appendChild(l);
		combo.appendChild(mg);
	}
	
	public static void buildPayMethodsCombobox(Combobox combo) {
		combo.getItems().clear();
		Comboitem cash = new Comboitem(Labels.getLabel(PayMethod.CASH.getMessageKey()));
		cash.setValue(PayMethod.CASH);
		Comboitem mobile = new Comboitem(Labels.getLabel(PayMethod.MOBILE.getMessageKey()));
		mobile.setValue(PayMethod.MOBILE);
		Comboitem pos = new Comboitem(Labels.getLabel(PayMethod.POS.getMessageKey()));
		pos.setValue(PayMethod.POS);
		combo.appendChild(cash);
		combo.appendChild(mobile);
		combo.appendChild(pos);
	}
	

	public static void clearCombobox(Combobox combobox) {
		List<Component> children = combobox.getChildren();
		for (Iterator<Component> iterator = children.iterator(); iterator.hasNext();) {
			iterator.next();
			iterator.remove();
		}
	}

	public static void setSelectedValueOnCombobox(Combobox combobox, Object value) {
		List<Comboitem> children = combobox.getItems();
		for (Comboitem item : children) {
			if (item.getValue().equals(value)) {
				combobox.setSelectedItem(item);
				// return;
			}
		}
	}


	/* LISTBOX */

	
	public static void refreshListBox(String list, String box, List<?> listItems, List<?> boxlist) {
		Listbox listbox = (Listbox) SessionHelper.takeObject(list);
		Combobox combobox = (Combobox) SessionHelper.takeObject(box);
		try {
			UIHelper.buildComplexList(listbox, listItems);
			UIHelper.buildCombobox(combobox, boxlist);
		} catch (NullPointerException e) {
			//Messages.warning_center(Labels.getLabel("no.records"), listbox);
		}
	}
	
	
	public static void buildLanguageListbox(Listbox listbox) {
		listbox.getItems().clear();
		Listitem portuguese = new Listitem(Labels.getLabel("portuguese.record"));
		portuguese.setStyle("border:none");
		portuguese.setValue(Language.PORTUGUESE);
		Listitem english = new Listitem(Labels.getLabel("english.record"));
		english.setStyle("border:none");
		english.setValue(Language.ENGLISH);
		listbox.appendChild(portuguese);
		listbox.appendChild(english);
	}
	
	
	public static void buildUnityListbox(Listbox listbox) {
		listbox.getItems().clear();
		Listitem unity = new Listitem(Labels.getLabel("unity.record"));
		unity.setStyle("border:none");
		unity.setValue(Unity.UNITY);
		Listitem cx = new Listitem(Labels.getLabel("box.record"));
		cx.setStyle("border:none");
		cx.setValue(Unity.CX);
		Listitem table = new Listitem(Labels.getLabel("table.record"));
		table.setStyle("border:none");
		table.setValue(Unity.TABLE);
		Listitem kg = new Listitem(Labels.getLabel("kilogram.record"));
		kg.setStyle("border:none");
		kg.setValue(Unity.KG);
		Listitem l = new Listitem(Labels.getLabel("liter.record"));
		l.setStyle("border:none");
		l.setValue(Unity.L);
		Listitem mg = new Listitem(Labels.getLabel("milligrams.record"));
		mg.setStyle("border:none");
		mg.setValue(Unity.MG);
		listbox.appendChild(unity);
		listbox.appendChild(cx);
		listbox.appendChild(table);
		listbox.appendChild(kg);
		listbox.appendChild(l);
		listbox.appendChild(mg);
	}
	
	

	public static void buildProfileListbox(Listbox list, List<Profile> perfis) {
		list.getItems().clear();
		for (Profile profile : perfis) {
			Listitem listitem = new Listitem();
			listitem.setValue(profile);
			listitem.appendChild(new Listcell(profile.getName()));
			list.appendChild(listitem);
		}
	}

	public static void buildTransactionListbox(Listbox list, List<Transaction> transactions) {
		list.getItems().clear();
		for (Transaction transaction : transactions) {
			Listitem listitem = new Listitem();
			listitem.setValue(transaction);
			listitem.appendChild(new Listcell(Labels.getLabel(transaction.getName())));
			list.appendChild(listitem);
		}
	}

	
	public static void buildInvoicesToPayListbox(Listbox list, Set<ItemPaidBill> items) {
		list.getItems().clear();
		for (ItemPaidBill item : items) {
			Listitem listitem = new Listitem();
			listitem.setValue(item);
			listitem.appendChild(new Listcell(item.getInvoice().getCode()));
			listitem.appendChild(new Listcell(String.valueOf(item.getInvoice().getDate())));
			listitem.appendChild(new Listcell(String.valueOf(item.getInvoice().getDueDate())));
			listitem.appendChild(new Listcell(String.valueOf(item.getInvoice().getTotal())));
			listitem.appendChild(new Listcell(String.valueOf(item.getInvoice().getToPay())));
			listitem.appendChild(new Listcell(String.valueOf(item.getAmount())));
			list.appendChild(listitem);
		}
	}
	
	
	public static void buildInvoicesListbox(Listbox list, Set<Invoice> invoices) {
		list.getItems().clear();
		for (Invoice invoice : invoices) {
			Listitem listitem = new Listitem();
			listitem.setValue(invoice);
			listitem.appendChild(new Listcell(invoice.getCode()));
			listitem.appendChild(new Listcell(String.valueOf(invoice.getDate())));
			listitem.appendChild(new Listcell(String.valueOf(invoice.getDueDate())));
			listitem.appendChild(new Listcell(String.valueOf(invoice.isCanceled())));
			listitem.appendChild(new Listcell(String.valueOf(invoice.isMadePayment())));
			listitem.appendChild(new Listcell(String.valueOf(invoice.getTotal())));
			list.appendChild(listitem);
		}
	}
	
	public static void buildProductBatchListbox(Listbox list, List<Batch> batchs) {
		list.getItems().clear();
		for (Batch batch : batchs) {
			Listitem listitem = new Listitem();
			listitem.setValue(batch);
			listitem.appendChild(new Listcell(batch.getCode()));
			listitem.appendChild(new Listcell(String.valueOf(batch.getDateManufacturing())));
			listitem.appendChild(new Listcell(String.valueOf(batch.getExpirationDate())));
			listitem.appendChild(new Listcell(String.valueOf(batch.getCostPrice())));
			listitem.appendChild(new Listcell(String.valueOf(batch.getSalePrice())));
			listitem.appendChild(new Listcell(String.valueOf(batch.getAmount())));
			list.appendChild(listitem);
		}
	}
	
	public static void buildProviderActivityListbox(Listbox list, List<Request> requests) {
		list.getItems().clear();
		for (Request request : requests) {
			Listitem listitem = new Listitem();
			listitem.setValue(request);
			listitem.appendChild(new Listcell(request.getCode()));
			listitem.appendChild(new Listcell(String.valueOf(request.getDate())));
			listitem.appendChild(new Listcell(String.valueOf(request.getTotal())));
			list.appendChild(listitem);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getSelectedValuesOnLIstBox(Listbox listbox) throws NullPointerException {
		Set<Listitem> items = listbox.getSelectedItems();
		Vector objects = new Vector();
		for (Listitem listitem : items) {
			objects.add(listitem.getValue());
		}
		return objects;
	}
	
	public static List<ItemPaidBill> getSelectedItemPaidBillOnLIstBox(Listbox listbox) throws NullPointerException {
		Set<Listitem> items = listbox.getSelectedItems();
		List<ItemPaidBill> objects = new ArrayList<ItemPaidBill>();
		for (Listitem item : items) {
			if(item.isSelected()) {
				objects.add(item.getValue());
			}
		}
		return objects;
	}
	
	
	public static void setSelectedValuesOnListbox(Listbox listbox, Object value) {
		List<Listitem> items = listbox.getItems();
		for (Listitem item : items) {
			if (item.getValue().equals(value)) {
				listbox.setSelectedItem(item);
			}
		}
	}

	public static void setSelectedListOfValuesOnListbox(Listbox listbox, List<?> selectedObjects) {
		List<Listitem> items = listbox.getItems();
		Set<Listitem> selectedItems = new HashSet<Listitem>();
		for (Listitem item : items) {
			if (selectedObjects.contains(item.getValue())) {
				selectedItems.add(item);
			}
		}
		listbox.setSelectedItems(selectedItems);
	}

	public static void buildSimpleList(Listbox listbox, List<?> selectedObjects) {
		UIHelper.clearListbox(listbox);
		listbox.setModel(new ListModelList<>(selectedObjects));
	}

	public static void buildComplexList(Listbox list, List<?> selectedObjects) {
		UIHelper.clearListbox(list);
		list.setModel(new ListModelList<>(Verifications.duplication(selectedObjects)));
		UIHelper.setRecordsNumber(Labels.getLabel("records.record") + " : ", selectedObjects.size(), list);
	}
	
	public static void buildListBox(Listbox list, List<?> selectedObjects) {
		UIHelper.clearListbox(list);
		list.setModel(new ListModelList<>(selectedObjects));
		UIHelper.setRecordsNumber(Labels.getLabel("records.record") + " : ", selectedObjects.size(), list);
	}
	

	public static void clearListbox(Listbox listbox) {
		List<Component> children = listbox.getChildren();
		for (Iterator<Component> iterator = children.iterator(); iterator.hasNext();) {
			Component component = iterator.next();
			if (component instanceof Listitem || component instanceof Listfoot) {
				iterator.remove();
			}
		}
	}

	private static Listfoot setRecordsNumber(String label, int recordsNumber, Listbox listbox) {
		return setRecordsNumber(label, recordsNumber, listbox, 3);
	}

	private static Listfoot setRecordsNumber(String label, int recordsNumber, Listbox listbox, int span) {
		Listfoot listfoot = new Listfoot();
		Listfooter listfooter = new Listfooter();
		listfooter.setSpan(span);
		listfooter.setLabel(label + recordsNumber);
		listfoot.appendChild(listfooter);
		listbox.appendChild(listfoot);

		return listfoot;
	}

	/**
	 * verifies that the selected component is empty
	 * 
	 * @param component
	 * @return
	 * */
	public static boolean selection(Component component) {
		try {
			if (checkSelectedComponent(component).equals("") || checkSelectedComponent(component).equals(null)) {
				Messages.warning_right(Labels.getLabel("mandatory.field"), component);
				return true;
			}
			return false;
		} catch (NullPointerException | UiException ex) {
			Messages.warning_right(Labels.getLabel("mandatory.field"), component);
			return true;
		}
	}

	/**
	 * check if the filled field has empty space.
	 * 
	 * @param component
	 * @return
	 * */

	public static boolean field(Component component) {
		try {
			if (checkFilledFields(component).equals("") || checkFilledFields(component).equals(null)) {
				Messages.warning_right(Labels.getLabel("mandatory.field"), component);
				return true;
			}
			return false;
		} catch (NullPointerException ex) {
			Messages.warning_right(Labels.getLabel("mandatory.field"), component);
			return true;
		}
	}

	/* METODOS AUXILIZAR */
	/**
	 * Will check if the selected component is empty or not
	 * 
	 * @param component
	 * @return
	 * */
	private static Object checkSelectedComponent(Component component) {
		try {
			if (component instanceof Radiogroup) {
				Object obj = ((Radiogroup) component).getSelectedItem().getLabel();
				if (Verifications.characters(String.valueOf(obj))) {
					return obj;
				}
			} else if (component instanceof Listbox) {
				return ((Listbox) component).getSelectedItem().getValue();
			} else if (component instanceof Combobox) {
				return ((Combobox) component).getSelectedItem().getValue();
			}
			return null;

		} catch (UiException e) {
			return null;
		}
	}

	/**
	 * Will check if the component is empty or not
	 * 
	 * @param component
	 * @return
	 */
	public static Object checkFilledFields(Component component) throws NullPointerException {
		if (component instanceof Textbox) {
			Object obj = ((Textbox) component).getValue();
			((Textbox) component).setValue(((String) obj).replaceAll("^\\s+", ""));
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Spinner) {
			Object obj = ((Spinner) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Doublespinner) {
			Object obj = ((Doublespinner) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Datebox) {
			Object obj = ((Datebox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Timebox) {
			Object obj = ((Timebox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Decimalbox) {
			Object obj = ((Decimalbox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Intbox) {
			Object obj = ((Intbox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Longbox) {
			Object obj = ((Longbox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Doublebox) {
			Object obj = ((Doublebox) component).getValue();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		} else if (component instanceof Combobox) {
			Object obj = ((Combobox) component).getText();
			if (Verifications.characters(String.valueOf(obj))) {
				return obj;
			}
		}
		return null;
	}

}