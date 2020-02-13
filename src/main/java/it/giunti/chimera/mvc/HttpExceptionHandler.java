package it.giunti.chimera.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	ObjectMapper jsonMapper;
	
	@ExceptionHandler(value = { Conflict409Exception.class, Internal418Exception.class,
			NotFound404Exception.class, Unauthorized401Exception.class,
			UnprocessableEntity422Exception.class})
	protected ResponseEntity<Object> handleConflict(HttpException ex, WebRequest request) {
		String bodyOfResponse;
		try {
			ErrorBean errorBean = new ErrorBean();
			errorBean.getError().setMessage(ex.getMessage());
			errorBean.getError().setStatus(ex.getStatus().value());
			bodyOfResponse = jsonMapper.writeValueAsString(errorBean);
		} catch (JsonProcessingException e) {
			bodyOfResponse = e.getMessage();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Status", ex.getStatus().value()+"");
		return handleExceptionInternal(ex, bodyOfResponse, 
				headers, ex.getStatus(), request);
	}
	
	// Inner Classes
	
	public static class ErrorBean {
		private ErrorContentBean error = new ErrorContentBean();
		public ErrorContentBean getError() {
			return error;
		}
		public void setError(ErrorContentBean error) {
			this.error = error;
		}
	}
	public static class ErrorContentBean {
		private String message;
		private int status;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
	}
}