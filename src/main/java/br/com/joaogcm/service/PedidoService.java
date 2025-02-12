package br.com.joaogcm.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogcm.dao.PedidoDAO;
import br.com.joaogcm.entity.Pedido;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO pedidoDAO;

	public boolean criarPedido(Pedido pedido) {
		return pedidoDAO.criarPedido(pedido);
	}

	public boolean atualizarPedidoPorCodigo(Pedido pedido) {
		return pedidoDAO.atualizarPedidoPorCodigo(pedido);
	}

	public boolean removerPedidoPorCodigo(Integer codigo) {
		return pedidoDAO.removerPedidoPorCodigo(codigo);
	}

	public Set<Pedido> buscarTodosPedidos() {
		return pedidoDAO.buscarTodosPedidos();
	}

	public Optional<Pedido> buscarPedidoPorCodigo(Integer codigo) {
		return pedidoDAO.buscarPedidoPorCodigo(codigo);
	}
}