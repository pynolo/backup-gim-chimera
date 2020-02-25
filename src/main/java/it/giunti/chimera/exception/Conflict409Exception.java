package it.giunti.chimera.exception;

import org.springframework.http.HttpStatus;

public class Conflict409Exception extends HttpException {
	
	private static final long serialVersionUID = -8073175402240272660L;
	
	private HttpStatus STATUS = HttpStatus.CONFLICT;
	
	public Conflict409Exception(String message){
		super(message);
		this.setStatus(STATUS);
	}
	
	public Conflict409Exception(String message, Throwable ex){
		super(message, ex);
		this.setStatus(STATUS);
	}

	public Conflict409Exception(Throwable ex){
		super(ex);
		this.setStatus(STATUS);
	}
	
}
