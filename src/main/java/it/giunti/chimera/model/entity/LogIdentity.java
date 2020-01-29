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
import javax.persistence.Transient;

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "log_identities")
public class LogIdentity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "identity_uid",length = 16)
    private String identityUid;
    @Basic(optional = false)
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Basic(optional = false)
    @Column(name = "id_federation", nullable = false)
    private Integer idFederation;
    @Column(name = "function", length = 128)
    private String function;
    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;
    @Column(name = "result", length = 256)
    private String result;
    
    @Transient
    private String serviceDescr;
    
    public LogIdentity() {
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getIdFederation() {
		return idFederation;
	}

	public void setIdFederation(Integer idFederation) {
		this.idFederation = idFederation;
	}

	public String getServiceDescr() {
		return serviceDescr;
	}

	public void setServiceDescr(String serviceDescr) {
		this.serviceDescr = serviceDescr;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getIdentityUid() {
		return identityUid;
	}

	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LogIdentity)) {
            return false;
        }
        LogIdentity other = (LogIdentity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LogIdentity[id=" + id + "]";
    }

}
