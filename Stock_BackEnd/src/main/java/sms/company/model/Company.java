/**
 * 
 */
package sms.company.model;

import java.awt.Image;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import sms.Entidade;

/**
 * <code>Company</code> will be inherit all methods of class @see Entidade that
 * will be a person or company that will be use this system.
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */

@Entity
@PrimaryKeyJoinColumn(name = "entidade_id")
@Table(name = "company")
@Access(AccessType.FIELD)
public class Company extends Entidade {

	@Column(nullable = true)
	private Image logo;


	
}
