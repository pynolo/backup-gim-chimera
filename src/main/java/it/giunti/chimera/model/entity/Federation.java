package it.giunti.chimera.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "federations")
public class Federation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "federation_uid", nullable = false, length = 32)
    private String federationUid;
    @Basic(optional = false)
    @Column(name = "access_key", nullable = false, length = 128)
    private String accessKey;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    @Column(name = "contact", length = 256)
    private String contact;
	@Basic(optional = false)
	@Column(name = "can_update", nullable = false)
	private Boolean canUpdate;
	@Basic(optional = false)
	@Column(name = "can_delete", nullable = false)
	private Boolean canDelete;
	@Basic(optional = false)
	@Column(name = "can_replace", nullable = false)
	private Boolean canReplace;
	
    public Federation() {
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getFederationUid() {
		return federationUid;
	}

	public void setFederationUid(String federationUid) {
		this.federationUid = federationUid;
	}

	public Boolean getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(Boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public Boolean getCanReplace() {
		return canReplace;
	}

	public void setCanReplace(Boolean canReplace) {
		this.canReplace = canReplace;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Federation)) {
            return false;
        }
        Federation other = (Federation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Federation[id=" + id + "]";
    }

}
