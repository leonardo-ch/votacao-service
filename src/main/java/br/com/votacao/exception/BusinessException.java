package br.com.votacao.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 2329295674665615129L;
	
	private final String code;
	private final HttpStatus status;
	
}
