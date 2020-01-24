package it.giunti.chimera.api05;

import java.util.Map;

public class ValidationBean {
	
	private ErrorBean error = null;
	private Boolean successfulValidation = null;
	private Map<String, String> warnings = null;
	
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public Boolean getSuccessfulValidation() {
		return successfulValidation;
	}
	public void setSuccessfulValidation(Boolean successfulValidation) {
		this.successfulValidation = successfulValidation;
	}
	public Map<String, String> getWarnings() {
		return warnings;
	}
	public void setWarnings(Map<String, String> warnings) {
		this.warnings = warnings;
	}
	 
	 
}
