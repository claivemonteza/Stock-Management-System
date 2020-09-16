/**
 * 
 */
package sms.invoicing.payment.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import sms.management.bank.model.ViaBank;

/**
 * @author Claive Monteza
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "paid_id")
@Table(name = "paidinvoice")
@Access(AccessType.FIELD)
public class PaidInvoice extends Paid{

	@OneToMany(fetch = FetchType.EAGER)
	List<ViaBank> viabank = new ArrayList<ViaBank>();

	/**
	 * @return the viabank
	 */
	public List<ViaBank> getViabank() {
		return viabank;
	}

	/**
	 * @param viabank the viabank to set
	 */
	public void setViabank(List<ViaBank> viabank) {
		this.viabank = viabank;
	}
	
	
}
