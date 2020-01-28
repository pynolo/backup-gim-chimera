package it.giunti.chimera.api05.bean;

public class FederationBean {
	private ErrorBean error = null;
	private String name = null;
	private String federationUid = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFederationUid() {
		return federationUid;
	}
	public void setFederationUid(String federationUid) {
		this.federationUid = federationUid;
	}
	
}
