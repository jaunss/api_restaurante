package br.com.joaogcm.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogcm.dao.LancheDAO;
import br.com.joaogcm.entity.Lanche;

@Service
public class LancheService {

	@Autowired
	private LancheDAO lancheDAO;

	public boolean criarLanche(Lanche lanche) {
		return lancheDAO.criarLanche(lanche);
	}

	public boolean atualizarLanchePorCodigo(Lanche lanche) {
		return lancheDAO.atualizarLanchePorCodigo(lanche);
	}

	public boolean removerLanchePorCodigo(Integer codigo) {
		return lancheDAO.removerLanchePorCodigo(codigo);
	}

	public Set<Lanche> buscarTodosLanches() {
		return lancheDAO.buscarTodosLanches();
	}

	public Optional<Lanche> buscarLanchePorCodigo(Integer codigo) {
		return lancheDAO.buscarLanchePorCodigo(codigo);
	}
}