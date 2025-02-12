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

import br.com.joaogcm.dto.RetornoLancheDTO;
import br.com.joaogcm.entity.Lanche;
import br.com.joaogcm.service.LancheService;

@RestController
@RequestMapping(value = "/api/lanche")
public class LancheController {

	@Autowired
	private LancheService lancheService;

	@PostMapping(value = "/criarLanche")
	public ResponseEntity<RetornoLancheDTO> criarLanche(@RequestBody Lanche lanche) {
		try {
			boolean isLancheCriado = lancheService.criarLanche(lanche);

			if (isLancheCriado) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new RetornoLancheDTO(lanche, "Lanche criado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new RetornoLancheDTO("Erro ao tentar criar o Lanche."));
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoLancheDTO(e.getMessage()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoLancheDTO("Erro ao processar a requisição de criação: " + e.getMessage()));
		}
	}

	@PutMapping(value = "/atualizarLanchePorCodigo/{codigo}")
	public ResponseEntity<RetornoLancheDTO> atualizarLanchePorCodigo(@PathVariable Integer codigo,
			@RequestBody Lanche lanche) {
		try {
			if (codigo == null || !codigo.equals(lanche.getCodigo())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RetornoLancheDTO(
						"O código do Lanche não pode ser nulo ou o código na URL deve corresponder ao código no corpo da requisição."));
			}

			boolean isLancheAtualizado = lancheService.atualizarLanchePorCodigo(lanche);

			if (isLancheAtualizado) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoLancheDTO(lanche, "Lanche atualizado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoLancheDTO("Lanche com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoLancheDTO("Erro ao processar a requisição de atualização: " + e.getMessage()));
		}
	}

	@DeleteMapping(value = "/removerLanchePorCodigo/{codigo}")
	public ResponseEntity<RetornoLancheDTO> removerLanchePorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoLancheDTO("O código do Lanche não pode ser nulo."));
			}

			boolean isLancheRemovido = lancheService.removerLanchePorCodigo(codigo);

			if (isLancheRemovido) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoLancheDTO("Lanche com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoLancheDTO("Erro ao processar a requisição de remoção: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarTodosLanches")
	public ResponseEntity<RetornoLancheDTO> buscarTodosLanches() {
		try {
			Set<Lanche> lanches = lancheService.buscarTodosLanches();

			if (!lanches.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoLancheDTO(lanches, lanches.size() + " Lanches encontrados com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoLancheDTO(lanches, "Nenhum Lanche encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoLancheDTO("Erro ao processar a requisição de busca geral: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/buscarLanchePorCodigo/{codigo}")
	public ResponseEntity<RetornoLancheDTO> buscarLanchePorCodigo(@PathVariable Integer codigo) {
		try {
			if (codigo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new RetornoLancheDTO("O código do Lanche não pode ser nulo."));
			}

			Optional<Lanche> lanche = lancheService.buscarLanchePorCodigo(codigo);

			if (lanche.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new RetornoLancheDTO(lanche.get(), "Lanche encontrado com sucesso."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new RetornoLancheDTO("Lanche com código " + codigo + " não encontrado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RetornoLancheDTO("Erro ao processar a requisição de busca: " + e.getMessage()));
		}
	}
}