package com.ms.electronic.invoice.main.config;

import com.ms.electronic.invoice.main.properties.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ResponseExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleDefaultException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
		  responseFactory(ex, 400),
		  HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionResponse> handleSQLException(DataIntegrityViolationException ex) {
		String message = ex.getMessage();
		int start = message.indexOf("[");
		int end = message.indexOf("]");

		var text = message.substring(start + 1, end);
		text = text.substring(0, text.indexOf("for key") - 2);

		var exception = new ExceptionResponse(
		  text,
		  406,
		  LocalDateTime.now()
		);

		return new ResponseEntity<>(exception, HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException ex,
	  HttpHeaders headers,
	  HttpStatusCode status,
	  WebRequest request
	) {
		List<String> errors = ex.getBindingResult()
		  .getFieldErrors()
		  .stream()
		  .map(FieldError::getDefaultMessage)
		  .toList();

		StringBuilder message = new StringBuilder();

		for (int i = 0; i < errors.size(); i++) {
			message.append(errors.get(i));

			if (i != (errors.size() - 1)) {
				message.append(". ");
			} else {
				message.append(".");
			}
		}

		var exception = new ExceptionResponse(
		  message.toString(),
		  status.value(),
		  LocalDateTime.now()
		);

		return new ResponseEntity<>(exception, status);
	}

	private ExceptionResponse responseFactory(Exception ex, int code) {
		return new ExceptionResponse(
		  ex.getMessage(),
		  code,
		  LocalDateTime.now()
		);
	}
}
