package it.giunti.chimera.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "lookup_interests")
public class LookupInterest {
	
	@Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
        if (!(object instanceof LookupInterest)) {
            return false;
        }
        LookupInterest other = (LookupInterest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LookupInterest[id=" + id + "]";
    }

}
