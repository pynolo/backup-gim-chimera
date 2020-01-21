package it.giunti.chimera;

import java.io.Serializable;

public class BusinessException extends Exception implements Serializable {
	private static final long serialVersionUID = -2105681056394250457L;
	private String message;

	public BusinessException() {
		super();
		message="";
	}
	
	public BusinessException(Throwable e) {
		super(e);
	}
	
	public BusinessException(String message) {
		super(message);
		this.message=message;
	}
	
	public BusinessException(String message, Throwable e) {
		super(message, e);
		this.message=message;
	}
	
	public String getMessage() {
		if (message == null) return super.getCause().getMessage();
		if (message.equals("")) return super.getCause().getMessage();
		return message;
	}

	
}
