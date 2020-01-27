package it.giunti.chimera.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "provider_accounts")
public class ProviderAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
	@Basic(optional = false)
    @Column(name = "id_identity")
    private Integer idIdentity;
    @Basic(optional = false)
    @Column(name = "account_identifier", nullable = false, length = 256)
    private String accountIdentifier;
    @JoinColumn(name = "id_provider", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Provider provider;
    @Basic(optional = false)
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    
    public ProviderAccount() {
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public Integer getIdIdentity() {
		return idIdentity;
	}

	public void setIdIdentity(Integer idIdentity) {
		this.idIdentity = idIdentity;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProviderAccount)) {
            return false;
        }
        ProviderAccount other = (ProviderAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProviderAccount[id=" + id + "]";
    }

}
