package it.giunti.chimera.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Conflict409Exception.class, Internal418Exception.class,
			NotFound404Exception.class, Unauthorized401Exception.class,
			UnprocessableEntity422Exception.class})
	protected ResponseEntity<Object> handleConflict(HttpException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		return handleExceptionInternal(ex, bodyOfResponse, 
				new HttpHeaders(), ex.getStatus(), request);
	}
	
}