package it.giunti.chimera.api.v05.bean;

import java.util.Map;

public class ValidationBean {
	
	private Boolean success = null;
	private Map<String, String> warnings = null;
	
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
