package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class VotoAlreadyExistsException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public VotoAlreadyExistsException() {
		super("voto-7", HttpStatus.ALREADY_REPORTED);
	}
}
