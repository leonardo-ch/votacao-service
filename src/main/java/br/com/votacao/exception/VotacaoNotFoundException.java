package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class VotacaoNotFoundException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public VotacaoNotFoundException() {
		super("votacao-1", HttpStatus.NOT_FOUND);
	}
}
