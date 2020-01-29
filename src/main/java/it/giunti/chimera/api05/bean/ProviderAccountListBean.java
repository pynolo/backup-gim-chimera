package it.giunti.chimera.api05.bean;

import java.util.List;

public class ProviderAccountListBean {

	private ErrorBean error = null;
	private List<ProviderAccountBean> providerAccounts = null;
	
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
	}

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
