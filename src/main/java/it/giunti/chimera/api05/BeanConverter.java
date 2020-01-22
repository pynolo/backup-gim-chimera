package it.giunti.chimera.api05;

import it.giunti.chimera.model.entity.ProviderAccount;

public class BeanConverter {

	public static ProviderAccountBean toProviderAccountBean(ProviderAccount entity) {
		ProviderAccountBean bean = new ProviderAccountBean();
		bean.setError(null);
		bean.setIdentityUid(entity.getIdentity().getIdentityUid());
		bean.setSocialId(entity.getProvider().getCasPrefix()+"#"+entity.getAccountIdentifier());
		return bean;
	}
	public static ProviderAccountBean toProviderAccountBean(String errorCode, String errorMessage) {
		ProviderAccountBean bean = new ProviderAccountBean();
		ErrorBean error = new ErrorBean();
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		bean.setError(error);
		return bean;
	}
	
	
}
