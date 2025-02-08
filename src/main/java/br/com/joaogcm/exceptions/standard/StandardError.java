package br.com.joaogcm.exceptions.standard;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String erro;
	private String mensagem;
	private String caminho;

	public StandardError() {

	}

	public StandardError(Integer status, String erro, String mensagem, String caminho) {
		this.status = status;
		this.erro = erro;
		this.mensagem = mensagem;
		this.caminho = caminho;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}