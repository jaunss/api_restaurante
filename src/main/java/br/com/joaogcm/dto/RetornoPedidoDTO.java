package br.com.joaogcm.dto;

import java.util.Set;

import br.com.joaogcm.entity.Pedido;

public class RetornoPedidoDTO {

	private Set<Pedido> pedidos;
	private Pedido pedido;

	private String mensagem;

	public RetornoPedidoDTO(Set<Pedido> pedidos, String mensagem) {
		this.pedidos = pedidos;
		this.mensagem = mensagem;
	}

	public RetornoPedidoDTO(Pedido pedido, String mensagem) {
		this.pedido = pedido;
		this.mensagem = mensagem;
	}

	public RetornoPedidoDTO(String mensagem) {
		this.mensagem = mensagem;
	}

	public Set<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}