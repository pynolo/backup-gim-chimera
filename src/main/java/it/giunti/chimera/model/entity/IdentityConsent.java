package it.giunti.chimera.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "identities_consent")
public class IdentityConsent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "id_identity", nullable = false)
	private Integer idIdentity;
	@Basic(optional = false)
	@Column(name = "range", nullable = false, length = 256)
	private String range;
	@Basic(optional = false)
	@Column(name = "tos", nullable = false)
	private Boolean tos;
	@Basic(optional = false)
	@Column(name = "marketing", nullable = false)
	private Boolean marketing;
	@Basic(optional = false)
	@Column(name = "profiling", nullable = false)
	private Boolean profiling;
	@Basic(optional = false)
	@Column(name = "tos_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date tosDate;
	@Basic(optional = false)
	@Column(name = "marketing_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date marketingDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdIdentity() {
		return idIdentity;
	}

	public void setIdIdentity(Integer idIdentity) {
		this.idIdentity = idIdentity;
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
