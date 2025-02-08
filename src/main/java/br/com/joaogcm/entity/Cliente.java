package br.com.joaogcm.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 5291247984897394154L;

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	@Size(max = 100, message = "O nome deve ter o máximo de 100 caracteres.")
	@Column(name = "nome")
	private String nome;

	@Size(max = 100, message = "O email deve ter o máximo de 100 caracteres.")
	@Email(message = "O email deve ser válido!")
	@Column(name = "email")
	private String email;

	@Size(max = 30, message = "O telefone deve ter o máximo de 30 caracteres.")
	@Column(name = "telefone")
	private String telefone;

	@Size(max = 11, message = "O cpf deve ter o máximo de 11 caracteres.")
	@CPF(message = "O cpf deve ser válido!")
	@Column(name = "cpf")
	private String cpf;

	@Size(max = 50, message = "A senha deve ter o máximo de 50 caracteres.")
	@Column(name = "senha")
	private String senha;

	public Cliente() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}