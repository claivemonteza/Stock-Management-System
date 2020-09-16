/**
 * 
 */
package sms.company.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import sms.Identidade;
import sms.access.user.model.User;
import sms.util.DateUtil;

/**
 * <code>Stock</code> will control in and out of the product that
 * been sale and request.
 * 
 * @see Company
 * @see Identidade
 * @see User
 * 
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.6
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "stock")
@Access(AccessType.FIELD)
public class Stock extends Identidade{


	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = DateUtil.parse();
	
	@Temporal(TemporalType.TIME)
	@Column(nullable = false)
	private Date hour = new Date();
	
	@Column(nullable = false)
	private String type;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "company_id")
	@ForeignKey(name = "fk_stock_company")
	private Company company;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@ForeignKey(name = "fk_stock_user")
	private User user;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the hour
	 */
	public Date getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(Date hour) {
		this.hour = hour;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
}
