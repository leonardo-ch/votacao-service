package br.com.votacao.domain;

import br.com.votacao.exception.BusinessException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

	private static final String NO_MESSAGE_AVAILABLE = "Não há Mensagem cadastrada para este erro";
	private final MessageSource apiErrorMessageSource;
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleNotValidException(MethodArgumentNotValidException exception, Locale locale){
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		
		List<ErrorResponseDto.ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage)
										 .map(code -> toApiError(code, locale))
										 .collect(Collectors.toList());
		ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST, apiErrors);
		
		return ResponseEntity.badRequest().body(errorResponseDto);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidFormatException(InvalidFormatException exception, Locale locale){
		final String errorCode = "generic-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		
		final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(status, toApiError(errorCode, locale, exception.getValue()));
		return ResponseEntity.badRequest().body(errorResponseDto);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception exception, Locale locale){
		LOGGER.error("error-1", exception);
		final String errorCode = "error-1";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(status, toApiError(errorCode, locale));
		return ResponseEntity.status(status).body(errorResponseDto);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException exception, Locale locale){
		final String errorCode = exception.getCode();
		final HttpStatus status = exception.getStatus();
		
		final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(status, toApiError(errorCode, locale));
		return ResponseEntity.status(status).body(errorResponseDto);
	}

	private ErrorResponseDto.ApiError toApiError(String code, Locale locale, Object... args){
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			LOGGER.error("Não há Mensagem cadastrada para {} code com o {} locale", code, locale);
			message = NO_MESSAGE_AVAILABLE;
		}
		
		return new ErrorResponseDto.ApiError(code, message);
	}
}
