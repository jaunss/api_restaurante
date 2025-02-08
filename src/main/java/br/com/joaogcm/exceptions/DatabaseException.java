package br.com.joaogcm.exceptions;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}