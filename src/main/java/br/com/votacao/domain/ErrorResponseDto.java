package br.com.votacao.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor(access = PRIVATE)
class ErrorResponseDto {
	
	private final int statusCode;
	
	private final List<ApiError> errors;
	
	static ErrorResponseDto of(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponseDto(status.value(), errors);
	}
	
	static ErrorResponseDto of(HttpStatus status, ApiError error) {
		return new ErrorResponseDto(status.value(), Collections.singletonList(error));
	}
	
	
	@JsonAutoDetect(fieldVisibility = ANY)
	@RequiredArgsConstructor(access = PACKAGE)
	static class ApiError{
		private final String code;
		private final String message;
	}
}
