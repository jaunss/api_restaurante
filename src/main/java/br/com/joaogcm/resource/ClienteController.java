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

import br.com.joaogcm.dto.RetornoDTO;
import br.com.joaogcm.entity.Cliente;
import br.com.joaogcm.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping(value = "/criarCliente")
	public ResponseEntity<RetornoDTO> criarCliente(@Valid @RequestBody Cliente cliente) {
		try {
			boolean isClienteCriado = clienteService.criarCliente(cliente);

			if (isClienteCriado) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new RetornoDTO(cliente, "Cliente criado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new RetornoDTO("Erro ao tentar criar o Cliente."));
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoDTO(e.getMessage()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoDTO("Erro ao processar a requisição de criação: " + e.getMessage()));
		}
	}

	@PutMapping(value = "/atualizarClientePorCodigo/{codigo}")
	public ResponseEntity<RetornoDTO> atualizarClientePorCodigo(@PathVariable Integer codigo,
			@RequestBody Cliente cliente) {
		try {
			if (codigo == null || !codigo.equals(cliente.getCodigo())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoDTO(
						"O código do Cliente não pode ser nulo ou o código na URL deve corresponder ao código no corpo da requisição."));
			}

			boolean isClienteAtualizado = clienteService.atualizarClientePorCodigo(cliente);

			if (isClienteAtualizado) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoDTO(cliente, "Cliente atualizado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoDTO("Cliente com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoDTO("Erro ao processar a requisição de atualização: " + e.getMessage()));
		}
	}

	@DeleteMapping(value = "/removerClientePorCodigo/{codigo}")
	public ResponseEntity<RetornoDTO> removerClientePorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoDTO("O código do Cliente não pode ser nulo."));
			}

			boolean isClienteRemovido = clienteService.removerClientePorCodigo(codigo);

			if (isClienteRemovido) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoDTO("Cliente com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoDTO("Erro ao processar a requisição de remoção: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarTodosClientes")
	public ResponseEntity<RetornoDTO> buscarTodosClientes() {
		try {
			Set<Cliente> clientes = clienteService.buscarTodosClientes();

			if (!clientes.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoDTO(clientes, clientes.size() + " Clientes encontrados com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoDTO(clientes, "Nenhum Cliente encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoDTO("Erro ao processar a requisição de busca geral: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarCliente/{codigo}")
	public ResponseEntity<RetornoDTO> buscarClientePorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoDTO("O código do Cliente não pode ser nulo."));
			}

			Optional<Cliente> cliente = clienteService.buscarClientePorCodigo(codigo);

			if (cliente.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoDTO(cliente.get(), "Cliente encontrado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoDTO("Cliente com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoDTO("Erro ao processar a requisição de busca: " + e.getMessage()));
		}
	}
}