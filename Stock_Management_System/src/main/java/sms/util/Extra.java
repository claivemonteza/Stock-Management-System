package sms.util;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import sms.Identidade;


/**
 * <code>Extra</code> will active and inactive any class that inherit here.
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@MappedSuperclass
public class Extra extends Identidade{
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean active = true;

	/**
	 * This method will active and inactive.
	 */
	public void activeInactive() {
		if (this.active) {
			this.active = false;
		} else {
			this.active = true;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * 
	 * @param active 
	 */
	public void setActive(boolean active) {
		this.active = active;
	}	
	
}
