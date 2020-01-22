package it.giunti.chimera.api05;

import java.util.List;

import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;

public class BeanConverter {

	public static ProviderAccountBean toProviderAccountBean(ProviderAccount entity) {
		ProviderAccountBean bean = new ProviderAccountBean();
		bean.setError(null);
		bean.setIdentityUid(entity.getIdentity().getIdentityUid());
		bean.setSocialId(entity.getProvider().getCasPrefix()+"#"+entity.getAccountIdentifier());
		return bean;
	}
	
	public static IdentityBean toIdentityBean(Identity entity, List<IdentityConsent> consentArray) {
		IdentityBean bean = new IdentityBean();
		bean.setError(null);
		bean.setAddressTown(entity.getAddressTown());
		bean.setAddressProvinceId(entity.getAddressProvinceId());
		bean.setAddressStreet(entity.getAddressStreet());
		bean.setAddressZip(entity.getAddressZip());
		bean.setBirthDate(entity.getBirthDate());
		bean.setChangeTime(entity.getChangeTime());
		bean.setCodiceFiscale(entity.getCodiceFiscale());
		bean.setConsent(entity.get);
		bean.setEmail(entity.getEmail());
		bean.setFirstName(entity.getFirstName());
		bean.setIdentityUid(entity.getIdentityUid());
		bean.setIdentityUidOld(entity.get);
		bean.setInterest(entity.get);
		bean.setJob(entity.get);
		bean.setLastName(entity.getLastName());
		bean.setNlEdu(entity.get);
		bean.setNlScuolaInfanzia(entity.get);
		bean.setNlScuolaPrimaria(entity.get);
		bean.setNlScuolaSecondaria1(entity.get);
		bean.setNlScuolaSecondaria2(entity.get);
		bean.setPartitaIva(entity.getPartitaIva());
		bean.setSchool(entity.get);
		bean.setSex(entity.getSex());
		bean.setTelephone(entity.getTelephone());
		return bean;
	}
	
	
}
