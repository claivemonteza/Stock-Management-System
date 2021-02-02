package mz.co.stock;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;



/**
 *will active and inactive any class that inherit here.
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.7
 */
@MappedSuperclass
public class Availability{
	
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
	
}
