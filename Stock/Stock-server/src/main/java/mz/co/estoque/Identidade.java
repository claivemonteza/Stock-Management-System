package mz.co.estoque;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * This superclass <code>Identidade</code> will be extend in all class
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 * */

@MappedSuperclass
public abstract class Identidade {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;

	
	
	/**
	 * 
	 * @return
	 */
	
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

}