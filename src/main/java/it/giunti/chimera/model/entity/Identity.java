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

/**
 *
 * @author paolo
 */
@Entity
@Table(name = "identities")
public class Identity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "identity_uid", nullable = false, length = 32)
    private String identityUid;
    @Basic(optional = false)
    @Column(name = "identity_uid_old", nullable = false, length = 32)
    private String identityUidOld;
    @Basic(optional = false)
    @Column(name = "change_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;
    @Basic(optional = false)
    @Column(name = "change_type", nullable = false, length = 8)
    private String changeType;
    @Basic(optional = false)
    @Column(name = "id_service", nullable = false)
    private Integer idService;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified; // SOLA LETTURA
    
    @Column(name = "user_name", length = 256)
    private String userName;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 256, unique = true)
    private String email;
    @Column(name = "password_md5", length = 64)
    private String passwordMd5;
    @Column(name = "first_name", length = 32)
    private String firstName;
    @Column(name = "last_name", length = 64)
    private String lastName;
    @Column(name = "sex", length = 1)
    private String sex;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "address_street", length = 64)
    private String addressStreet;
    @Column(name = "address_zip", length = 16)
    private String addressZip;
    @Column(name = "address_province_id", length = 4)
    private String addressProvinceId;
    @Column(name = "address_town", length = 64)
    private String addressTown;
    @Column(name = "telephone", length = 32)
    private String telephone;
    @Column(name = "giunti_card", length = 16)
    private String giuntiCard; 
    @Column(name = "giunti_card_mode", length = 4)
    private String giuntiCardMode; 
    @Column(name = "codice_fiscale", length = 16)
    private String codiceFiscale; 
    @Column(name = "partita_iva", length = 16)
    private String partitaIva;
    @Column(name = "interest", length = 256)
    private String interest;
    @Column(name = "job", length = 256)
    private String job;
    @Column(name = "school", length = 256)
    private String school;
    
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "identity")
	//private Set<ProviderAccount> providerAccountSet;
    
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "identity")
	//private Set<IdentityConsent> identityConsentSet;
	
    public Identity() {
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentityUid() {
		return identityUid;
	}

	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswordMd5() {
		return passwordMd5;
	}

	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

//	public Date getLastModified() {
//		return lastModified;
//	}

	public Integer getIdService() {
		return idService;
	}

	public void setIdService(Integer idService) {
		this.idService = idService;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public String getAddressProvinceId() {
		return addressProvinceId;
	}

	public void setAddressProvinceId(String addressProvinceId) {
		this.addressProvinceId = addressProvinceId;
	}

	public String getAddressTown() {
		return addressTown;
	}

	public void setAddressTown(String addressTown) {
		this.addressTown = addressTown;
	}

	public String getGiuntiCard() {
		return giuntiCard;
	}

	public void setGiuntiCard(String giuntiCard) {
		this.giuntiCard = giuntiCard;
	}

	public String getGiuntiCardMode() {
		return giuntiCardMode;
	}

	public void setGiuntiCardMode(String giuntiCardMode) {
		this.giuntiCardMode = giuntiCardMode;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getIdentityUidOld() {
		return identityUidOld;
	}

	public void setIdentityUidOld(String identityUidOld) {
		this.identityUidOld = identityUidOld;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	//public Set<ProviderAccount> getProviderAccountSet() {
	//	return providerAccountSet;
	//}
	//
	//public void setProviderAccountSet(Set<ProviderAccount> providerAccountSet) {
	//	this.providerAccountSet = providerAccountSet;
	//}
	//
	//public Set<IdentityConsent> getIdentityConsentSet() {
	//	return identityConsentSet;
	//}
	//
	//public void setIdentityConsentSet(Set<IdentityConsent> identityConsentSet) {
	//	this.identityConsentSet = identityConsentSet;
	//}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Identity)) {
            return false;
        }
        Identity other = (Identity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Identity[id=" + id + "] "+identityUid;
    }

}
