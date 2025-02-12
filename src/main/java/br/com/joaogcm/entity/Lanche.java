package br.com.joaogcm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lanche")
public class Lanche implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	@Size(max = 100, message = "O nome deve ter o máximo de 100 caracteres.")
	@Column(name = "nome")
	private String nome;

	@Size(max = 200, message = "A descrição do conteúdo deve ter o máximo de 200 caracteres.")
	@Column(name = "descricao_conteudo")
	private String descricao_conteudo;

	@Column(name = "preco")
	private BigDecimal preco;

	@ManyToMany(mappedBy = "lanches", cascade = CascadeType.PERSIST)
	private Set<Pedido> pedidos = new LinkedHashSet<Pedido>();

	public Lanche() {

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

	public String getDescricao_conteudo() {
		return descricao_conteudo;
	}

	public void setDescricao_conteudo(String descricao_conteudo) {
		this.descricao_conteudo = descricao_conteudo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Set<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}