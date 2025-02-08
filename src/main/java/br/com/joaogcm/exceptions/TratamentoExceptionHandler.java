package br.com.joaogcm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import br.com.joaogcm.exceptions.standard.StandardError;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TratamentoExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<StandardError> validacaoDeErrosNotFound(RuntimeException e, HttpServletRequest request) {
		Integer status = HttpStatus.NOT_FOUND.value();
		String erro = "Recurso não encontrado.";
		String mensagem = e.getMessage();

		StandardError standardError = new StandardError(status, erro, mensagem, request.getRequestURI());

		return ResponseEntity.status(status).body(standardError);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<StandardError> validacaoDeErrosNullPointer(NullPointerException e,
			HttpServletRequest request) {
		Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		String erro = "Algum dos valores são nulos.";
		String mensagem = e.getMessage();

		StandardError standardError = new StandardError(status, erro, mensagem, request.getRequestURI());

		return ResponseEntity.status(status).body(standardError);
	}

	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<Object> validacaoDeErrosInternalServerError(InternalServerError e,
			HttpServletRequest request) {
		Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		String erro = "Erro interno do servidor.";
		String mensagem = e.getMessage();

		StandardError standardError = new StandardError(status, erro, mensagem, request.getRequestURI());

		return ResponseEntity.status(status).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> validacaoDeErrosMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
		Integer status = HttpStatus.BAD_REQUEST.value();
		String erro = "Argumentos inválidos para um dos campos.";
		String mensagem = e.getMessage();
		
		StandardError standardError = new StandardError(status, erro, mensagem, request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
}