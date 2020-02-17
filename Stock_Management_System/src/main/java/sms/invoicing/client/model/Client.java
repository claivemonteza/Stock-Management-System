package sms.invoicing.client.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import sms.Entidade;

/**
 * <code>Client</code> will be inheritance all methods of class @see Entidade.
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */
@Entity
@PrimaryKeyJoinColumn(name = "entidade_id")
@Table(name = "client")
@Access(AccessType.FIELD)
public class Client extends Entidade {

	@Column(nullable = true)
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
