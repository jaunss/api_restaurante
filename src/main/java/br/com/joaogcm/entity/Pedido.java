package br.com.joaogcm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	@Column(name = "data_pedido")
	private LocalDateTime dataHoraPedido;

	@Column(name = "total")
	private BigDecimal total;

	@ManyToOne
	@Column(name = "cliente_id")
	private Cliente cliente;

	@ManyToMany
	@JoinTable(name = "pedido_lanche", joinColumns = @JoinColumn(name = "codigo_pedido"), inverseJoinColumns = @JoinColumn(name = "codigo_lanche"))
	private Set<Lanche> lanches = new LinkedHashSet<Lanche>();

	public Pedido() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataHoraPedido() {
		return dataHoraPedido;
	}

	public void setDataHoraPedido(LocalDateTime dataHoraPedido) {
		this.dataHoraPedido = dataHoraPedido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<Lanche> getLanches() {
		return lanches;
	}

	public void setLanches(Set<Lanche> lanches) {
		this.lanches = lanches;
	}
}