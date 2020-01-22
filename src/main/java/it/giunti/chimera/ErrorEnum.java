package it.giunti.chimera;

public enum ErrorEnum {
	NO_ERROR("0", "Terminato con successo. "),
	INTERNAL_ERROR("1", "Errore interno. "),
	EMPTY_PARAMETER("2", "Un parametro obbligatorio e' vuoto. "),
	WRONG_PARAMETER_VALUE("3", "Il parametro ha un valore errato. "),
	WRONG_ACCESS_KEY("4", "Non e' stata fornita una accessKey. "),
	DATA_NOT_FOUND("5", "Dati non trovati. ");
	
	private String errorCode; 
	private String errorDescr;
	
	private ErrorEnum(String errorCode, String errorDescr) {
		this.errorCode=errorCode;
		this.errorDescr=errorDescr;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDescr() {
		return errorDescr;
	}
	
}
