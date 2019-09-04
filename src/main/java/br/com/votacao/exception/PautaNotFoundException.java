package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class PautaNotFoundException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public PautaNotFoundException() {
		super("pauta-6", HttpStatus.NOT_FOUND);
	}
}
