package it.giunti.chimera.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.dao.IdentitiesDao;
import it.giunti.chimera.model.dao.ProviderAccountsDao;
import it.giunti.chimera.model.entity.Identities;
import it.giunti.chimera.model.entity.ProviderAccounts;

@Service("socialService")
public class SocialService {

	@Autowired
	@Qualifier("identitiesDao")
	IdentitiesDao identitiesDao;
	@Autowired
	@Qualifier("providerAccountsDao")
	ProviderAccountsDao providerAccountsDao;
	
	public String getCasPrefixFromSocialId(String socialId) 
			throws BusinessException {
		int pos = socialId.indexOf("#");
		if (pos >= 0) {
			String prefix = socialId.substring(0, pos);
			return prefix;
		} else {
			pos = socialId.indexOf("%23");
			if (pos >= 0) {
				String prefix = socialId.substring(0, pos);
				return prefix;
			} else {
				throw new BusinessException("socialId doesn't contain a provider prefix");
			}
		}
	}
	
	public String getIdentifierFromSocialId(String socialId) 
			throws BusinessException {
		int pos = socialId.indexOf("#");
		if (pos >= 0) {
			String suffix = socialId.substring(pos+1);
			return suffix;
		} else {
			pos = socialId.indexOf("%23");
			if (pos >= 0) {
				String suffix = socialId.substring(pos+3);
				return suffix;
			} else {
				throw new BusinessException("socialId doesn't contain an identifier");
			}
		}
	}
	
	@Transactional
	public Identities getIdentityBySocialId(String socialId)
			throws BusinessException, EmptyResultException, DuplicateResultException {
		Identities result = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		String suffix = getIdentifierFromSocialId(socialId);
		ProviderAccounts account = providerAccountsDao.findByProviderIdentifier(prefix, suffix);
		if (account != null) result = account.getIdentity();
		return result;
	}
	
	@Transactional
	public ProviderAccounts getAccountByIdentityUidAndSocialId(String identityUid, String socialId)
			throws BusinessException, EmptyResultException, DuplicateResultException {
		ProviderAccounts account = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		//String suffix = getIdentifierFromSocialId(socialId);
		Identities identity = identitiesDao.findByIdentityUid( identityUid);
		List<ProviderAccounts> pList = providerAccountsDao.findByIdentityAndProvider(identity.getId(), prefix);
		if (pList.size() == 1) {
			account = pList.get(0);
		} else {
			if (pList.size() == 0)
				throw new EmptyResultException("No rows in ProviderAccounts correspond to "+prefix);
			if (pList.size() > 1)
				throw new DuplicateResultException("More rows in ProviderAccounts correspond to "+prefix);
		}
		return account;
	}
	
	@Transactional
	public ProviderAccounts createProviderAccount(Identities identity, String socialId)
			throws BusinessException {
		ProviderAccounts result = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		String suffix = getIdentifierFromSocialId(socialId);
		result = providerAccountsDao.createProviderAccount(identity, prefix, suffix);
		return result;
	}
	
	@Transactional
	public void deleteProviderAccount(ProviderAccounts pa) throws BusinessException {
		if (pa != null) {
			providerAccountsDao.delete(pa.getId());
		} else {
			throw new BusinessException("ProviderAccount to delete is null");
		}
	}
}
