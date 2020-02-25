package it.giunti.chimera.exception;

import org.springframework.http.HttpStatus;

public class Unauthorized401Exception extends HttpException {

	private static final long serialVersionUID = -5147293555650138112L;
	
	private HttpStatus STATUS = HttpStatus.UNAUTHORIZED;
	
	public Unauthorized401Exception(String message){
		super(message);
		this.setStatus(STATUS);
	}
	
	public Unauthorized401Exception(String message, Throwable ex){
		super(message, ex);
		this.setStatus(STATUS);
	}

	public Unauthorized401Exception(Throwable ex){
		super(ex);
		this.setStatus(STATUS);
	}
}
