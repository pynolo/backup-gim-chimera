package it.giunti.chimera.api05.bean;

import java.util.List;

public class IdentityHistoryBean {

	private ErrorBean error = null;
	private String identityUid = null;
	private List<String> replacedIdentityUids = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	public String getIdentityUid() {
		return identityUid;
	}
	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}
	public List<String> getReplacedIdentityUids() {
		return replacedIdentityUids;
	}
	public void setReplacedIdentityUids(List<String> replacedIdentityUids) {
		this.replacedIdentityUids = replacedIdentityUids;
	}
	
	
}
