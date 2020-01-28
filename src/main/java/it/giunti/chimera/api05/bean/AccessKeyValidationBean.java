package it.giunti.chimera.api05.bean;

import it.giunti.chimera.model.entity.Federation;

public class AccessKeyValidationBean {
	
	private ErrorBean error = null;
	private Federation federation = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public Federation getFederation() {
		return federation;
	}
	public void setFederation(Federation federation) {
		this.federation = federation;
	}
	
}
