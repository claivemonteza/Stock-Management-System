package sms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sms.util.Extra;


/**
 * This superclass <code>Entidade</code> will be extends at class Client and Provider
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.6
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "entidade")
@Access(AccessType.FIELD)
public class Entidade extends Extra implements Comparable<Entidade>{

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Column( nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String nuit;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String city;
	
	@Column( nullable = false)
	private String province;
	
	@Column(nullable = false)
	private String country;
	
	@Column(nullable = false)
	private String telephone;
	
	@Column(nullable = false)
	private String mobile;
	
	@Column(nullable = false)
	private String fax;

	@Column(nullable = false)
	private String email;
	
	@Override
	public int compareTo(Entidade entidade) {
		return this.nuit.compareTo(entidade.getNuit());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entidade) {
			return this.name.equals(((Entidade) obj).getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
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
	 * @return the nuit
	 */
	public String getNuit() {
		return nuit;
	}

	/**
	 * @param nuit the nuit to set
	 */
	public void setNuit(String nuit) {
		this.nuit = nuit;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the provine to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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
	
	
}
