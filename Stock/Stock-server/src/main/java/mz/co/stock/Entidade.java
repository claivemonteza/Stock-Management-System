package mz.co.stock;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;




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
@Table(name = "entidade", uniqueConstraints=@UniqueConstraint(name="uk_entidade_name_nuit",columnNames= {"name","nuit"}))
@Access(AccessType.FIELD)
public class Entidade extends Availability implements Comparable<Entidade>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="entidadeid_generator")
	@SequenceGenerator(name="entidadeid_generator", initialValue=1, allocationSize=1, sequenceName="entidade_id_seq")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date = new Date();
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="name", nullable = false)
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
