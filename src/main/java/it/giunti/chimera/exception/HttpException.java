package it.giunti.chimera.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {

	private static final long serialVersionUID = 2351127183030638120L;
	
	private String message = null;
	private HttpStatus status = null;
	private Throwable error = null;
	
	public HttpException(String message){
		this.message = message;
	}
	
	public HttpException(String message, Throwable ex){
		this.message = message;
		this.error = ex;
	}

	public HttpException(Throwable ex){
		this.error = ex;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

}
