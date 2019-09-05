package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class UnableCpfException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public UnableCpfException() {
		super("validacao-cpf-unable", HttpStatus.UNAUTHORIZED);
	}
}
