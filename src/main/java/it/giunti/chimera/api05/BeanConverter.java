package it.giunti.chimera.api05;

import java.util.ArrayList;
import java.util.List;

import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityConsent;
import it.giunti.chimera.model.entity.ProviderAccount;

public class BeanConverter {

	public static ProviderAccountBean toProviderAccountBean(ProviderAccount entity) {
		ProviderAccountBean bean = new ProviderAccountBean();
		bean.setError(null);
		bean.setIdentityUid(entity.getIdentity().getIdentityUid());
		bean.setSocialId(entity.getProvider().getCasPrefix()+"#"+entity.getAccountIdentifier());
		return bean;
	}
	
	public static IdentityBean toIdentityBean(Identity entity) {
		IdentityBean bean = new IdentityBean();
		bean.setError(null);
		bean.setAddressTown(entity.getAddressTown());
		bean.setAddressProvinceId(entity.getAddressProvinceId());
		bean.setAddressStreet(entity.getAddressStreet());
		bean.setAddressZip(entity.getAddressZip());
		bean.setBirthDate(entity.getBirthDate());
		bean.setChangeTime(entity.getChangeTime());
		bean.setCodiceFiscale(entity.getCodiceFiscale());
		bean.setEmail(entity.getEmail());
		bean.setFirstName(entity.getFirstName());
		bean.setIdentityUid(entity.getIdentityUid());
		bean.setIdentityUidOld(entity.getIdentityUidOld());
		bean.setInterest(entity.getInterest());
		bean.setJob(entity.getJob());
		bean.setLastName(entity.getLastName());
		bean.setPartitaIva(entity.getPartitaIva());
		bean.setSchool(entity.getSchool());
		bean.setSex(entity.getSex());
		bean.setTelephone(entity.getTelephone());
		List<ConsentBean> beanList = new ArrayList<ConsentBean>();
		for (IdentityConsent consent:entity.getIdentityConsentSet())
			beanList.add(BeanConverter.toConsentBean(consent));
		bean.setConsent(beanList);
		return bean;
	}
	public static ConsentBean toConsentBean(IdentityConsent entity) {
		ConsentBean bean = new ConsentBean();
		bean.setMarketing(entity.getMarketing());
		bean.setMarketingDate(entity.getMarketingDate());
		bean.setProfiling(entity.getProfiling());
		bean.setRange(entity.getRange());
		bean.setTos(entity.getTos());
		bean.setTosDate(entity.getTosDate());
		return bean;
	}
	
}
