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
	private boolean activo = true;

	/**
	 * This method will active and inactive.
	 */
	public void activoInactivo() {
		if (this.activo) {
			this.activo = false;
		} else {
			this.activo = true;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * 
	 * @param activo 
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}	
	
}
