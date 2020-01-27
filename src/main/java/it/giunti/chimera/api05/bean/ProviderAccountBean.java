package it.giunti.chimera.api05.bean;

public class ProviderAccountBean implements IInputBean {

	private String accessKey = null;
	private ErrorBean error = null;
	private String identityUid = null;
	private String socialId = null;
	
	@Override
	public String getAccessKey() {
		return accessKey;
	}
	@Override
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
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
	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	
  
}
