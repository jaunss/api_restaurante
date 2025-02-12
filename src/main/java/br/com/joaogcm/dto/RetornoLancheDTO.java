package br.com.joaogcm.dto;

import java.util.Set;

import br.com.joaogcm.entity.Lanche;

public class RetornoLancheDTO {

	private Set<Lanche> lanches;
	private Lanche lanche;

	private String mensagem;

	public RetornoLancheDTO(Set<Lanche> lanches, String mensagem) {
		this.lanches = lanches;
		this.mensagem = mensagem;
	}

	public RetornoLancheDTO(Lanche lanche, String mensagem) {
		this.lanche = lanche;
		this.mensagem = mensagem;
	}

	public RetornoLancheDTO(String mensagem) {
		this.mensagem = mensagem;
	}

	public Set<Lanche> getLanches() {
		return lanches;
	}

	public void setLanches(Set<Lanche> lanches) {
		this.lanches = lanches;
	}

	public Lanche getLanche() {
		return lanche;
	}

	public void setLanche(Lanche lanche) {
		this.lanche = lanche;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}