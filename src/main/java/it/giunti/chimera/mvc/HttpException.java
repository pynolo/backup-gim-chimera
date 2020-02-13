package it.giunti.chimera.mvc;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {

	private static final long serialVersionUID = 2351127183030638120L;
	
	private String message = null;
	private HttpStatus status = null;
	private Throwable throwable = null;
	
	public HttpException(String message){
		this.message = message;
	}
	
	public HttpException(String message, Throwable ex){
		this.message = message;
		this.throwable = ex;
	}

	public HttpException(Throwable ex){
		this.throwable = ex;
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

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
}
