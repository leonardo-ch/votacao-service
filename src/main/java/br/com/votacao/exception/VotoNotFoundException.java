package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class VotoNotFoundException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public VotoNotFoundException() {
		super("voto-6", HttpStatus.NOT_FOUND);
	}
}
