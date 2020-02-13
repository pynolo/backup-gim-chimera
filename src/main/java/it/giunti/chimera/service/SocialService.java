package it.giunti.chimera.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.ProviderAccountDao;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;
import it.giunti.chimera.mvc.Conflict409Exception;
import it.giunti.chimera.mvc.NotFound404Exception;
import it.giunti.chimera.mvc.UnprocessableEntity422Exception;

@Service("socialService")
public class SocialService {

	@Autowired
	@Qualifier("identityDao")
	private IdentityDao identityDao;
	@Autowired
	@Qualifier("providerAccountDao")
	private ProviderAccountDao providerAccountDao;
	
	public String getCasPrefixFromSocialId(String socialId) 
			throws UnprocessableEntity422Exception {
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
				throw new UnprocessableEntity422Exception("socialId doesn't contain a provider prefix");
			}
		}
	}
	
	public String getIdentifierFromSocialId(String socialId) 
			throws UnprocessableEntity422Exception {
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
				throw new UnprocessableEntity422Exception("socialId doesn't contain an identifier");
			}
		}
	}
	
	@Transactional
	public Identity getIdentityBySocialId(String socialId)
			throws UnprocessableEntity422Exception, NotFound404Exception, Conflict409Exception {
		Identity result = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		String suffix = getIdentifierFromSocialId(socialId);
		ProviderAccount account = providerAccountDao.findByProviderIdentifier(prefix, suffix);
		if (account != null) result = identityDao.selectById(account.getIdIdentity());
		return result;
	}
	
	@Transactional
	public List<ProviderAccount> findAccountsByIdentityUid(String identityUid) {
		Identity identity = identityDao.findByIdentityUid( identityUid);
		List<ProviderAccount> pList = providerAccountDao.findByIdentity(identity.getId());
		return pList;
	}
	
	@Transactional
	public ProviderAccount getAccountByIdentityUidAndSocialId(String identityUid, String socialId)
			throws UnprocessableEntity422Exception, NotFound404Exception, Conflict409Exception {
		ProviderAccount account = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		//String suffix = getIdentifierFromSocialId(socialId);
		Identity identity = identityDao.findByIdentityUid( identityUid);
		List<ProviderAccount> pList = providerAccountDao.findByIdentityAndProvider(identity.getId(), prefix);
		if (pList.size() == 1) {
			account = pList.get(0);
		} else {
			if (pList.size() == 0)
				throw new NotFound404Exception("No rows in ProviderAccount correspond to "+prefix);
			if (pList.size() > 1)
				throw new Conflict409Exception("More rows in ProviderAccount correspond to "+prefix);
		}
		return account;
	}
	
	@Transactional
	public ProviderAccount createProviderAccount(Integer idIdentity, String socialId)
			throws UnprocessableEntity422Exception, Conflict409Exception, NotFound404Exception {
		ProviderAccount result = null;
		String prefix = getCasPrefixFromSocialId(socialId);
		String suffix = getIdentifierFromSocialId(socialId);
		result = providerAccountDao.createProviderAccount(idIdentity, prefix, suffix);
		return result;
	}
	
	@Transactional
	public void deleteProviderAccount(ProviderAccount pa) throws NotFound404Exception {
		if (pa != null) {
			providerAccountDao.delete(pa.getId());
		} else {
			throw new NotFound404Exception("ProviderAccount to delete is null");
		}
	}
}
