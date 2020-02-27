package it.giunti.chimera.api.v05.bean;

import java.util.Map;

public class ValidationBean {
	
	private Boolean success = null;
	private String assignedIdentityUid = null;
	private Map<String, String> messages = null;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getAssignedIdentityUid() {
		return assignedIdentityUid;
	}
	public void setAssignedIdentityUid(String assignedIdentityUid) {
		this.assignedIdentityUid = assignedIdentityUid;
	}
	public Map<String, String> getMessages() {
		return messages;
	}
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}	 
	 
}
