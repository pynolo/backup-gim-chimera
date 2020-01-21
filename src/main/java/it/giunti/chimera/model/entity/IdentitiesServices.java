package it.giunti.chimera.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "identities_services",
		uniqueConstraints = @UniqueConstraint(columnNames = {"id_identity", "id_service"}))
public class IdentitiesServices {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "id_identity", nullable = false)
    private Integer idIdentity;
    @Basic(optional = false)
    @Column(name = "id_service", nullable = false)
    private Integer idService;

    public IdentitiesServices() {
    }

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

	public Integer getIdService() {
		return idService;
	}

	public void setIdService(Integer idService) {
		this.idService = idService;
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
        if (!(object instanceof IdentitiesServices)) {
            return false;
        }
        IdentitiesServices other = (IdentitiesServices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IdentitiesServices[id=" + id + "]";
    }

}
