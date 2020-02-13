package it.giunti.chimera.api.v05.bean;

import java.util.List;

public class IdentityHistoryBean {

	private String identityUid = null;
	private List<String> replacedIdentityUids = null;
	
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
