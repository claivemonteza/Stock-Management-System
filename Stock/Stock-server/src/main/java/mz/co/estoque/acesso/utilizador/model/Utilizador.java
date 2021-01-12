package mz.co.estoque.acesso.utilizador.model;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mz.co.estoque.Disponibilidade;
import mz.co.estoque.acesso.perfil.model.Perfil;
import mz.co.estoque.acesso.perfil.model.Permissao;

/**
 * is the class that will access to the system by user name and
 * password.
 * 
 * @see Disponibilidade
 * @see Perfil
 * @see Lingua
 * 
 * @author Claive Monteza
 * 
 * @version 1.0
 * @since 1.7
 */

@Entity
@Table(name = "utilizador")
public class Utilizador extends Disponibilidade implements Comparable<Utilizador> {

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date data = new Date();

	@Column(nullable = false)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Lingua lingua;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false, unique = true)
	private String usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "perfil_id", foreignKey = @ForeignKey(name = "PERFIL_ID_FK"))
	private Perfil perfil;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Permissao> permissoes = new HashSet<>();

	/**
	 * This method will compare this class with another with the same name.
	 * 
	 * @param utilizador
	 */
	@Override
	public int compareTo(Utilizador utilizador) {
		return this.usuario.compareTo(utilizador.getUsuario());
	}

	/**
	 * This method will compare with one object is equals this class.
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Utilizador) {
			return this.usuario.equals(((Utilizador) obj).getUsuario());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.usuario.hashCode();
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * 
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 * @return
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * 
	 * @param senha
	 * 
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * 
	 * @param perfil
	 * 
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String getName() {
		return nome;
	}

	/**
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Lingua getLingua() {
		return lingua;
	}

	public void setLingua(Lingua lingua) {
		this.lingua = lingua;
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

	/**
	 * @return the transactions
	 */
	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * @param permissoes the transactions to set
	 */
	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public boolean isTransactionAccessible(String codigoPermissao) {
		List<Permissao> matchingTransactions = select(perfil.getPermissoes(),
				having(on(Permissao.class).getCodigo(), equalTo(codigoPermissao)));
		return !matchingTransactions.isEmpty();
	}
}
