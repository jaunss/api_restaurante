package br.com.joaogcm.resource;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaogcm.dto.RetornoPedidoDTO;
import br.com.joaogcm.entity.Pedido;
import br.com.joaogcm.service.PedidoService;

@RestController
@RequestMapping(value = "/api/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping(value = "/criarPedido")
	public ResponseEntity<RetornoPedidoDTO> criarPedido(@RequestBody Pedido pedido) {
		try {
			boolean isPedidoCriado = pedidoService.criarPedido(pedido);

			if (isPedidoCriado) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new RetornoPedidoDTO(pedido, "Pedido criado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new RetornoPedidoDTO("Erro ao tentar criar o Pedido."));
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoPedidoDTO(e.getMessage()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoPedidoDTO("Erro ao processar a requisição de criação: " + e.getMessage()));
		}
	}

	@PutMapping(value = "/atualizarPedidoPorCodigo/{codigo}")
	public ResponseEntity<RetornoPedidoDTO> atualizarPedidoPorCodigo(@PathVariable Integer codigo,
			@RequestBody Pedido pedido) {
		try {
			if (codigo == null || !codigo.equals(pedido.getCodigo())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoPedidoDTO(
						"O código do Pedido não pode ser nulo ou o código na URL deve corresponder ao código no corpo da requisição."));
			}

			boolean isPedidoAtualizado = pedidoService.atualizarPedidoPorCodigo(pedido);

			if (isPedidoAtualizado) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoPedidoDTO(pedido, "Pedido atualizado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoPedidoDTO("Pedido com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoPedidoDTO("Erro ao processar a requisição de atualização: " + e.getMessage()));
		}
	}

	@DeleteMapping(value = "/removerPedidoPorCodigo/{codigo}")
	public ResponseEntity<RetornoPedidoDTO> removerPedidoPorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoPedidoDTO("O código do Pedido não pode ser nulo."));
			}

			boolean isPedidoRemovido = pedidoService.removerPedidoPorCodigo(codigo);

			if (isPedidoRemovido) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoPedidoDTO("Pedido com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoPedidoDTO("Erro ao processar a requisição de remoção: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarTodosPedidos")
	public ResponseEntity<RetornoPedidoDTO> buscarTodosPedidos() {
		try {
			Set<Pedido> pedidos = pedidoService.buscarTodosPedidos();

			if (!pedidos.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoPedidoDTO(pedidos, pedidos.size() + " Pedidos encontrados com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoPedidoDTO(pedidos, "Nenhum Pedido encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoPedidoDTO("Erro ao processar a requisição de busca geral: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarPedidoPorCodigo/{codigo}")
	public ResponseEntity<RetornoPedidoDTO> buscarPedidoPorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoPedidoDTO("O código do Pedido não pode ser nulo."));
			}

			Optional<Pedido> pedido = pedidoService.buscarPedidoPorCodigo(codigo);

			if (pedido.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoPedidoDTO(pedido.get(), "Pedido encontrado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoPedidoDTO("Pedido com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoPedidoDTO("Erro ao processar a requisição de busca: " + e.getMessage()));
		}
	}
}