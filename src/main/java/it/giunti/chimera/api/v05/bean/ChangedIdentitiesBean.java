package it.giunti.chimera.api.v05.bean;

import java.util.List;

public class ChangedIdentitiesBean {
	private ErrorBean error = null;
	private String currentTimestamp = null;
	private List<IdentityBean> identities = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public List<IdentityBean> getIdentities() {
		return identities;
	}
	public void setIdentities(List<IdentityBean> identities) {
		this.identities = identities;
	}
	public String getCurrentTimestamp() {
		return currentTimestamp;
	}
	public void setCurrentTimestamp(String currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}
	
}
