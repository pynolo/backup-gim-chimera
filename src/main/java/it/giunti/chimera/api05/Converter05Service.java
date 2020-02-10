package it.giunti.chimera.api05;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.ChangeEnum;
import it.giunti.chimera.api05.bean.IdentityBean;
import it.giunti.chimera.api05.bean.IdentityConsentBean;
import it.giunti.chimera.api05.bean.ProviderAccountBean;
import it.giunti.chimera.model.dao.CounterDao;
import it.giunti.chimera.model.dao.IdentityConsentDao;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityConsent;
import it.giunti.chimera.model.entity.ProviderAccount;

@Service("converter05Service")
public class Converter05Service {

	@Autowired
	@Qualifier("identityDao")
	private IdentityDao identityDao;
	@Autowired
	@Qualifier("identityConsentDao")
	private IdentityConsentDao identityConsentDao;
	@Autowired
	@Qualifier("counterDao")
	private CounterDao counterDao;
	
	public ProviderAccountBean toProviderAccountBean(ProviderAccount entity) {
		ProviderAccountBean bean = new ProviderAccountBean();
		if (entity.getIdIdentity() != null) {
			Identity identity = identityDao.selectById(entity.getId());
			bean.setIdentityUid(identity.getIdentityUid());
		}
		bean.setError(null);
		bean.setSocialId(entity.getProvider().getCasPrefix()+"#"+entity.getAccountIdentifier());
		return bean;
	}
	
	// IDENTITY
	
	public IdentityBean toIdentityBean(Identity entity) {
		IdentityBean bean = new IdentityBean();
		bean.setError(null);
		bean.setIdentityUid(entity.getIdentityUid());
		bean.setReplacedByUid(entity.getReplacedByUid());
		bean.setAddressTown(entity.getAddressTown());
		bean.setAddressProvinceId(entity.getAddressProvinceId());
		bean.setAddressStreet(entity.getAddressStreet());
		bean.setAddressZip(entity.getAddressZip());
		bean.setBirthDate(entity.getBirthDate());
		bean.setChangeTime(entity.getChangeTime());
		bean.setCodiceFiscale(entity.getCodiceFiscale());
		bean.setEmail(entity.getEmail());
		bean.setFirstName(entity.getFirstName());
		bean.setInterest(entity.getInterest());
		bean.setJob(entity.getJob());
		bean.setLastName(entity.getLastName());
		bean.setPartitaIva(entity.getPartitaIva());
		bean.setSchool(entity.getSchool());
		bean.setSex(entity.getSex());
		bean.setTelephone(entity.getTelephone());
		return bean;
	}
	
	@Transactional
	public Identity persistIntoIdentity(IdentityBean bean) {
		Identity entity = new Identity();
		if (bean.getIdentityUid() != null) {
			//ESISTE
			entity = identityDao.findByIdentityUid(bean.getIdentityUid());
			entity.setIdentityUid(bean.getIdentityUid());
		} else {
			//NUOVO
			String identityUid = counterDao.generateIdentityUid();
			entity.setIdentityUid(identityUid);
		}
		if (bean.getReplacedByUid() != null) entity.setReplacedByUid(bean.getReplacedByUid());
		if (bean.getAddressTown() != null) entity.setAddressTown(bean.getAddressTown());
		if (bean.getAddressProvinceId() != null) entity.setAddressProvinceId(bean.getAddressProvinceId());
		if (bean.getAddressStreet() != null) entity.setAddressStreet(bean.getAddressStreet());
		if (bean.getAddressZip() != null) entity.setAddressZip(bean.getAddressZip());
		if (bean.getBirthDate() != null) entity.setBirthDate(bean.getBirthDate());
		if (bean.getChangeTime() != null) entity.setChangeTime(bean.getChangeTime());
		if (bean.getCodiceFiscale() != null) entity.setCodiceFiscale(bean.getCodiceFiscale());
		if (bean.getEmail() != null) entity.setEmail(bean.getEmail());
		if (bean.getFirstName() != null) entity.setFirstName(bean.getFirstName());
		if (bean.getInterest() != null) entity.setInterest(bean.getInterest());
		if (bean.getJob() != null) entity.setJob(bean.getJob());
		if (bean.getLastName() != null) entity.setLastName(bean.getLastName());
		if (bean.getPartitaIva() != null) entity.setPartitaIva(bean.getPartitaIva());
		if (bean.getSchool() != null) entity.setSchool(bean.getSchool());
		if (bean.getSex() != null) entity.setSex(bean.getSex());
		if (bean.getTelephone() != null) entity.setTelephone(bean.getTelephone());
		if (entity.getId() != null) {
			//UPDATE
			entity = identityDao.update(entity, ChangeEnum.UPDATE);
		} else {
			//INSERT
			entity = identityDao.insert(entity);
		}
		return entity;
	}
	
	public IdentityConsentBean toConsentBean(IdentityConsent entity) {
		IdentityConsentBean bean = new IdentityConsentBean();
		if (entity.getIdIdentity() != null) {
			Identity identity = identityDao.selectById(entity.getId());
			bean.setIdentityUid(identity.getIdentityUid());
		}
		bean.setMarketing(entity.getMarketing());
		bean.setMarketingDate(entity.getMarketingDate());
		bean.setProfiling(entity.getProfiling());
		bean.setRange(entity.getRange());
		bean.setTos(entity.getTos());
		bean.setTosDate(entity.getTosDate());
		return bean;
	}
	
	@Transactional
	public IdentityConsent persistIntoConsent(IdentityConsentBean bean) {
		IdentityConsent entity = new IdentityConsent();
		if (bean.getIdentityUid() != null && bean.getRange() != null) {
			entity = identityConsentDao.findByIdentityUidAndRange(
					bean.getIdentityUid(), bean.getRange());
		}
		entity.setMarketing(bean.getMarketing());
		entity.setMarketingDate(bean.getMarketingDate());
		entity.setProfiling(bean.getProfiling());
		entity.setRange(bean.getRange());
		entity.setTos(bean.getTos());
		entity.setTosDate(bean.getTosDate());
		if (entity.getId() != null) {
			//UPDATE
			entity = identityConsentDao.update(entity);
		} else {
			//INSERT
			entity = identityConsentDao.insert(entity);
		}
		return entity;
	}
}
