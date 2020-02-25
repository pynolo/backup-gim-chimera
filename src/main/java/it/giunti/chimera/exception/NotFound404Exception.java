package it.giunti.chimera.exception;

import org.springframework.http.HttpStatus;

public class NotFound404Exception extends HttpException {
	
	private static final long serialVersionUID = -6009685911023260235L;

	private HttpStatus STATUS = HttpStatus.NOT_FOUND;
	
	public NotFound404Exception(String message){
		super(message);
		this.setStatus(STATUS);
	}
	
	public NotFound404Exception(String message, Throwable ex){
		super(message, ex);
		this.setStatus(STATUS);
	}

	public NotFound404Exception(Throwable ex){
		super(ex);
		this.setStatus(STATUS);
	}
	
}
