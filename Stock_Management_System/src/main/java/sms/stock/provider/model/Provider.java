package sms.stock.provider.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import sms.Entidade;

/**
 * <code>Providert</code> inheritance all methods of @see Entidade.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "entidade_id")
@Table(name = "provider")
@Access(AccessType.FIELD)
public class Provider extends Entidade{

	
}
