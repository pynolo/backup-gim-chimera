package it.giunti.chimera.api.v05.bean;

import java.util.List;

public class FederationListBean {

	private ErrorBean error = null;
	private List<FederationBean> federations = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}
	
	public List<FederationBean> getFederations() {
		return federations;
	}
	public void setFederations(List<FederationBean> federations) {
		this.federations = federations;
	}


	public class FederationBean {
		private String name = null;
		private String federationUid = null;
		
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
}
