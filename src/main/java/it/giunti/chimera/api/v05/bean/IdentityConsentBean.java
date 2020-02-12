package it.giunti.chimera.api.v05.bean;

import java.util.Date;

public class IdentityConsentBean {
	
	private String identityUid = null;
	private String range = null;
	private Boolean tos = null;
	private Boolean marketing = null;
	private Boolean profiling = null;
	private Date tosDate = null;
	private Date marketingDate = null;
	
	public String getIdentityUid() {
		return identityUid;
	}
	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public Boolean getTos() {
		return tos;
	}
	public void setTos(Boolean tos) {
		this.tos = tos;
	}
	public Boolean getMarketing() {
		return marketing;
	}
	public void setMarketing(Boolean marketing) {
		this.marketing = marketing;
	}
	public Boolean getProfiling() {
		return profiling;
	}
	public void setProfiling(Boolean profiling) {
		this.profiling = profiling;
	}
	public Date getTosDate() {
		return tosDate;
	}
	public void setTosDate(Date tosDate) {
		this.tosDate = tosDate;
	}
	public Date getMarketingDate() {
		return marketingDate;
	}
	public void setMarketingDate(Date marketingDate) {
		this.marketingDate = marketingDate;
	}
	
}
