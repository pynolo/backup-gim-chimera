package it.giunti.chimera.api.v05.bean;

import java.util.List;

public class ProviderAccountListBean {

	private List<ProviderAccountBean> providerAccounts = null;
	
	public List<ProviderAccountBean> getProviderAccounts() {
		return providerAccounts;
	}
	public void setProviderAccounts(List<ProviderAccountBean> providerAccounts) {
		this.providerAccounts = providerAccounts;
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
