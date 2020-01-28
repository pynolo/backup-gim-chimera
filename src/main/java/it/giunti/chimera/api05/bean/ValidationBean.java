package it.giunti.chimera.api05.bean;

import java.util.Map;

public class ValidationBean {
	
	private ErrorBean error = null;
	private Boolean success = null;
	private Map<String, String> warnings = null;
	
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Map<String, String> getWarnings() {
		return warnings;
	}
	public void setWarnings(Map<String, String> warnings) {
		this.warnings = warnings;
	}
	 
	 
}
