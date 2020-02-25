package it.giunti.chimera.exception;

import org.springframework.http.HttpStatus;

public class Internal418Exception extends HttpException {

	private static final long serialVersionUID = 5355516038592525667L;
	
	private HttpStatus STATUS = HttpStatus.I_AM_A_TEAPOT;
	
	public Internal418Exception(String message){
		super(message);
		this.setStatus(STATUS);
	}
	
	public Internal418Exception(String message, Throwable ex){
		super(message, ex);
		this.setStatus(STATUS);
	}

	public Internal418Exception(Throwable ex){
		super(ex);
		this.setStatus(STATUS);
	}
}
