package it.giunti.chimera.api.v05.bean;

import java.util.Date;
import java.util.List;

public class IdentityBean implements IInputBean {
	private ErrorBean error = null;
	private String accessKey = null;
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
	private String interest = null;
	private String job = null;
	private String school = null;
	private Boolean nlScuolaInfanzia = null;
	private Boolean nlScuolaPrimaria = null;
	private Boolean nlScuolaSecondaria1 = null;
	private Boolean nlScuolaSecondaria2 = null;
	private Boolean nlEdu = null;
	private List<IdentityConsentBean> consent = null;
	
	@Override
	public String getAccessKey() {
		return accessKey;
	}
	@Override
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public ErrorBean getError() {
		return error;
	}
	public void setError(ErrorBean error) {
		this.error = error;
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
	public Boolean getNlScuolaInfanzia() {
		return nlScuolaInfanzia;
	}
	public void setNlScuolaInfanzia(Boolean nlScuolaInfanzia) {
		this.nlScuolaInfanzia = nlScuolaInfanzia;
	}
	public Boolean getNlScuolaPrimaria() {
		return nlScuolaPrimaria;
	}
	public void setNlScuolaPrimaria(Boolean nlScuolaPrimaria) {
		this.nlScuolaPrimaria = nlScuolaPrimaria;
	}
	public Boolean getNlScuolaSecondaria1() {
		return nlScuolaSecondaria1;
	}
	public void setNlScuolaSecondaria1(Boolean nlScuolaSecondaria1) {
		this.nlScuolaSecondaria1 = nlScuolaSecondaria1;
	}
	public Boolean getNlScuolaSecondaria2() {
		return nlScuolaSecondaria2;
	}
	public void setNlScuolaSecondaria2(Boolean nlScuolaSecondaria2) {
		this.nlScuolaSecondaria2 = nlScuolaSecondaria2;
	}
	public Boolean getNlEdu() {
		return nlEdu;
	}
	public void setNlEdu(Boolean nlEdu) {
		this.nlEdu = nlEdu;
	}
	public List<IdentityConsentBean> getConsent() {
		return consent;
	}
	public void setConsent(List<IdentityConsentBean> consent) {
		this.consent = consent;
	}
	
}
