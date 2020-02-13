package it.giunti.chimera.mvc;

import org.springframework.http.HttpStatus;

public class UnprocessableEntity422Exception extends HttpException {

	private static final long serialVersionUID = -5990639751134573832L;
	
	private HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;
	
	public UnprocessableEntity422Exception(String message){
		super(message);
		this.setStatus(STATUS);
	}
	
	public UnprocessableEntity422Exception(String message, Throwable ex){
		super(message, ex);
		this.setStatus(STATUS);
	}

	public UnprocessableEntity422Exception(Throwable ex){
		super(ex);
		this.setStatus(STATUS);
	}
	
}
