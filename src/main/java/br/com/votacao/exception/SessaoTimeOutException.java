package br.com.votacao.exception;

import org.springframework.http.HttpStatus;

public class SessaoTimeOutException extends BusinessException {

	private static final long serialVersionUID = 5553707156721755355L;

	public SessaoTimeOutException() {
		super("sessao-7", HttpStatus.REQUEST_TIMEOUT);
	}
}
