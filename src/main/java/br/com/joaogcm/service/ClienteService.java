package br.com.joaogcm.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogcm.dao.ClienteDAO;
import br.com.joaogcm.entity.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;

	public boolean criarCliente(Cliente cliente) {
		return clienteDAO.criarCliente(cliente);
	}

	public boolean atualizarClientePorCodigo(Cliente cliente) {
		return clienteDAO.atualizarClientePorCodigo(cliente);
	}

	public boolean removerClientePorCodigo(Integer codigo) {
		return clienteDAO.removerClientePorCodigo(codigo);
	}

	public Set<Cliente> buscarTodosClientes() {
		return clienteDAO.buscarTodosClientes();
	}

	public Optional<Cliente> buscarClientePorCodigo(Integer codigo) {
		return clienteDAO.buscarClientePorCodigo(codigo);
	}
}