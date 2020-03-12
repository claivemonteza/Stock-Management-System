package sms.access.user.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sms.Identidade;

/**
 * <code>UserRoler</code> 
 * 
 * @see Identidade
 * @see User
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */

@Entity
@Table (name="user_role")
public class UserRoler extends Identidade{

	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private User user;
	
	

	/**
	 * 
	 * @return
	 **/
	public String getRole() {
		return role;
	}
	
	/**
	 * 
	 * @param role
	 **/
	public void setRole(String role) {
		this.role = role;
	}
}
