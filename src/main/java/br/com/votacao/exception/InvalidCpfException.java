package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class InvalidCpfException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public InvalidCpfException() {
		super("validacao-cpf", HttpStatus.BAD_REQUEST);
	}
}
