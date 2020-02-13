package it.giunti.chimera.api.v05.bean;

import java.util.List;

public class FederationListBean {

	private List<FederationBean> federations = null;
		
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
