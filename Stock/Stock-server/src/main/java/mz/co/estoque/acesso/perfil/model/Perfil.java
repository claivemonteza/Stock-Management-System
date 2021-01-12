package mz.co.estoque.acesso.perfil.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mz.co.estoque.Disponibilidade;


/**
 * is the class that will give transaction
 * to the user.
 * 
 * @see Extra
 *
 * @author Claive Monteza
 *
 * @version 1.0
 * @since 1.7
 */

@Entity
@Table(name = "perfil")
public class Perfil extends Disponibilidade implements Comparable<Perfil> {

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	private Date data = new Date();
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Permissao> permissoes;
	
	
	/**
	 * 
	 * This method will compare this class with another 
	 *  with the same name.
	 * 
	 * @param perfil
	 */
	@Override
	public int compareTo(Perfil perfil) {
		return this.nome.compareTo(perfil.getNome());
	}


	/**
	 * 
	 * This method will compare with one object is
	 * equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Perfil) {
			return this.nome.equals(((Perfil) obj).getNome());
		}
		return false;
	}

	
	@Override
	public int hashCode() {
		return this.nome.hashCode();
	}

	/**
	 * @return the name
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the name to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the transactions
	 */
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * @param permissoes the transactions to set
	 */
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}


	/**
	 * @return the date
	 */
	public Date getData() {
		return data;
	}


	/**
	 * @param data the date to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
}