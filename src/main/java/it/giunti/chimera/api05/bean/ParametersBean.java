package it.giunti.chimera.api05.bean;

public class ParametersBean implements IInputBean {
	
	private String accessKey = null;
	private String identityUid = null;
	private String email = null;
	private String password = null;
	private String socialId = null;
	private String redundantIdentityUid = null;
	private String finalIdentityUid = null;
	private String startTimestamp = null;
	
	@Override
	public String getAccessKey() {
		return accessKey;
	}
	@Override
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getIdentityUid() {
		return identityUid;
	}
	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	public String getRedundantIdentityUid() {
		return redundantIdentityUid;
	}
	public void setRedundantIdentityUid(String redundantIdentityUid) {
		this.redundantIdentityUid = redundantIdentityUid;
	}
	public String getFinalIdentityUid() {
		return finalIdentityUid;
	}
	public void setFinalIdentityUid(String finalIdentityUid) {
		this.finalIdentityUid = finalIdentityUid;
	}
	public String getStartTimestamp() {
		return startTimestamp;
	}
	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	
}
