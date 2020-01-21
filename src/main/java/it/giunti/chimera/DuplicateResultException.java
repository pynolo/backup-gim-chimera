package it.giunti.chimera;

public class DuplicateResultException extends Exception {
	
	private static final long serialVersionUID = 1352223452316868560L;
	private String message;
	
	public DuplicateResultException(String message){
		super(message);
		this.message=message;
	}
	
	public DuplicateResultException(String message, Throwable ex){
		super(message, ex);
		this.message=message;
	}

	public DuplicateResultException(Throwable ex){
		super(ex);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
