package it.giunti.chimera;

import java.io.Serializable;

public class EmptyResultException extends Exception implements Serializable {
	private static final long serialVersionUID = -2105681056394250457L;
	private String message;

	public EmptyResultException() {
		super();
		message="";
	}
	
	public EmptyResultException(Throwable e) {
		super(e);
	}
	
	public EmptyResultException(String message) {
		super(message);
		this.message=message;
	}
	
	public EmptyResultException(String message, Throwable e) {
		super(message, e);
		this.message=message;
	}
	
	public String getMessage() {
		if (message == null) return super.getCause().getMessage();
		if (message.equals("")) return super.getCause().getMessage();
		return message;
	}

	
}
