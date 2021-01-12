package mz.co.estoque.acesso.perfil.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import mz.co.estoque.Disponibilidade;

/**
 * <code>Transaction</code> is the class that will allow the profile
 * have many task.
 * 
 * @see Extra
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */

@Entity
@Table(name = "transaction")
public class Permissao extends Disponibilidade implements
		Comparable<Permissao> {

	public static final String PRODUCT_RECORD_WITH_MODIFICATIONS_ENABLED = "204";

	public static final String EXPIRING_PRODUCTS_REPORT = "401";


	@Column(nullable = false, unique = true)
	private String code;

	@Column(nullable = false, unique = true)
	private String name;

	private String url;

	
	/**
	 * This method will compare with one object is
	 * equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Permissao) {
			return this.code.equals(((Permissao) obj).getCode());
		}
		return super.equals(obj);
	}

	
	/**
	 * This method will compare this class with another 
	 *  with the same name.
	 * 
	 * @param transaction
	 */
	public int compareTo(Permissao transaction) {
		return name.compareTo(transaction.getName());
	}
	
	@Override
	public int hashCode() {
		return getCode().hashCode();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


}
