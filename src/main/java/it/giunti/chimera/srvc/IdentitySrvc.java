package it.giunti.chimera.srvc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.ProviderAccountDao;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityConsent;
import it.giunti.chimera.model.entity.ProviderAccount;

@Service("identitySrvc")
public class IdentitySrvc {


	@Autowired
	@Qualifier("socialSrvc")
	private SocialSrvc socialSrvc;
	
	@Autowired
	@Qualifier("identityDao")
	private IdentityDao identityDao;
	@Autowired
	@Qualifier("providerAccountDao")
	private ProviderAccountDao providerAccountDao;
	
	@Transactional
	public Identity getIdentity(String identityUid) {
		return identityDao.findByIdentityUid(identityUid);
	}
	
	@Transactional
	public Identity getIdentityByEmail(String email) throws DuplicateResultException {
		Identity entity = identityDao.findByEmail(email);
		if (entity == null) entity = identityDao.findByUserName(email);
		return entity;
	}
	
	@Transactional
	public Identity getIdentityBySocialId(String socialId) throws BusinessException, DuplicateResultException {
		String pac4jPrefix = socialSrvc.getCasPrefixFromSocialId(socialId);
		String accountIdentifier = socialSrvc.getIdentifierFromSocialId(socialId);
		ProviderAccount account = providerAccountDao.findByProviderIdentifier(pac4jPrefix, accountIdentifier);
		if (account != null) {
			Identity entity = account.getIdentity();
			return entity;
		} else return null;
	}

	@Transactional
	public Identity addOrUpdateIdentity(Identity identity, boolean isNew) {
		Identity result = null;
		if (identity != null) {
			result = identityDao.insert(identity);
		} else {
			result = identityDao.update(identity);
		}
		return result;
	}
	
	@Transactional
	public IdentityConsent addOrUpdateConsent(IdentityConsent consent) {
		boolean isNew = true;
		if (consent.getId() != null || consent.) isNew = true;
		Identity result = null;
		if (identity != null) {
			result = identityDao.insert(identity);
		} else {
			result = identityDao.update(identity);
		}
		return result;
	}
	
}
