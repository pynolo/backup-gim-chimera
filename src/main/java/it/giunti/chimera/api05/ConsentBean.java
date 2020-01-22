package it.giunti.chimera.api05;

import java.util.Date;

public class ConsentBean {
	
	private String consentRange = null;
	private Boolean consentTos = null;
	private Boolean consentMkt = null;
	private Boolean consentPrf = null;
	private Date consentDateTos = null;
	private Date consentDateMkt = null;
	
	public String getConsentRange() {
		return consentRange;
	}
	public void setConsentRange(String consentRange) {
		this.consentRange = consentRange;
	}
	public Boolean getConsentTos() {
		return consentTos;
	}
	public void setConsentTos(Boolean consentTos) {
		this.consentTos = consentTos;
	}
	public Boolean getConsentMkt() {
		return consentMkt;
	}
	public void setConsentMkt(Boolean consentMkt) {
		this.consentMkt = consentMkt;
	}
	public Boolean getConsentPrf() {
		return consentPrf;
	}
	public void setConsentPrf(Boolean consentPrf) {
		this.consentPrf = consentPrf;
	}
	public Date getConsentDateTos() {
		return consentDateTos;
	}
	public void setConsentDateTos(Date consentDateTos) {
		this.consentDateTos = consentDateTos;
	}
	public Date getConsentDateMkt() {
		return consentDateMkt;
	}
	public void setConsentDateMkt(Date consentDateMkt) {
		this.consentDateMkt = consentDateMkt;
	}
	
}
