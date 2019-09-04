package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class SessaoNotFoundException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public SessaoNotFoundException() {
		super("sessao-6", HttpStatus.NOT_FOUND);
	}
}
