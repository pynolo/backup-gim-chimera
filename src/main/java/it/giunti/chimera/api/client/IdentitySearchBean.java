package it.giunti.chimera.api.client;

import java.util.Date;

import it.giunti.chimera.model.entity.Identity;

public class IdentitySearchBean {
	
	private String identityUid = null;
	private String replacedByUid = null;
	private Date changeTime = null;
	private String changeType = null;
	private String email = null;
	private String lastName = null;
	private String firstName = null;
	private String sex = null;//"m|f",
	private Date birthDate = null;
	private String addressStreet = null;
	private String addressZip = null;
	private String addressProvinceId = null;
	private String addressTown = null;
	private String telephone = null;
	private String codiceFiscale = null;
	private String partitaIva = null;
	private String passwordMd5 = null;
	private String interest = null;
	private String job = null;
	private String school = null;
	private Date deletionTime = null;
	private String replacedById = null;
	
	public IdentitySearchBean() {
		super();
	}
	
	public IdentitySearchBean(Identity item) {
		super();
		this.setIdentityUid(item.getIdentityUid());
		this.setAddressProvinceId(item.getAddressProvinceId());
		this.setAddressStreet(item.getAddressStreet());
		this.setAddressTown(item.getAddressTown());
		this.setAddressZip(item.getAddressZip());
		this.setBirthDate(item.getBirthDate());
		this.setChangeTime(item.getChangeTime());
		this.setChangeType(item.getChangeType());
		this.setCodiceFiscale(item.getCodiceFiscale());
		this.setPartitaIva(item.getPartitaIva());
		this.setEmail(item.getEmail());
		this.setFirstName(item.getFirstName());
		this.setLastName(item.getLastName());
		this.setInterest(item.getInterest());
		this.setJob(item.getJob());
		this.setSchool(item.getSchool());
		if (item.getPasswordMd5() != null) {
			if (item.getPasswordMd5().length() > 7) {
				this.setPasswordMd5(item.getPasswordMd5().substring(0,8)+"************************");
			}
		}
		this.setDeletionTime(item.getDeletionTime());
		this.setReplacedByUid(item.getReplacedByUid());
	}
	
	public String getIdentityUid() {
		return identityUid;
	}
	public void setIdentityUid(String identityUid) {
		this.identityUid = identityUid;
	}
	public String getReplacedByUid() {
		return replacedByUid;
	}
	public void setReplacedByUid(String replacedByUid) {
		this.replacedByUid = replacedByUid;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getPasswordMd5() {
		return passwordMd5;
	}
	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
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
	public Date getDeletionTime() {
		return deletionTime;
	}
	public void setDeletionTime(Date deletionTime) {
		this.deletionTime = deletionTime;
	}

	public String getReplacedById() {
		return replacedById;
	}

	public void setReplacedById(String replacedById) {
		this.replacedById = replacedById;
	}
	
}
