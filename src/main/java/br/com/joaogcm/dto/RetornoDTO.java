package br.com.joaogcm.dto;

import java.util.Set;

import br.com.joaogcm.entity.Cliente;

public class RetornoDTO {

	private Set<Cliente> clientes;
	private Cliente cliente;
	private String mensagem;

	public RetornoDTO(Set<Cliente> clientes, String mensagem) {
		this.clientes = clientes;
		this.mensagem = mensagem;
	}

	public RetornoDTO(Cliente cliente, String mensagem) {
		this.cliente = cliente;
		this.mensagem = mensagem;
	}

	public RetornoDTO(String mensagem) {
		this.mensagem = mensagem;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}