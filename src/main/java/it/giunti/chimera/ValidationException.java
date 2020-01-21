package it.giunti.chimera;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -2115811823713647993L;
	
	private String message;
	
	public ValidationException(String message){
		super(message);
		this.message=message;
	}
	
	public ValidationException(String message, Throwable ex){
		super(message, ex);
		this.message=message;
	}

	public ValidationException(Throwable ex){
		super(ex);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
