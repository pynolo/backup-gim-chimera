package it.giunti.chimera.api05.bean;

public class SocialInputBean {
	private String accessKey = null;
	private String identityUid = null;
	private String socialId = null;
	
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
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
