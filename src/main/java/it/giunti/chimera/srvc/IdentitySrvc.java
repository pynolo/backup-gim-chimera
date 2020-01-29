package it.giunti.chimera.srvc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.ChangeEnum;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.LogIdentityDao;
import it.giunti.chimera.model.dao.ProviderAccountDao;
import it.giunti.chimera.model.entity.Identity;
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
	@Autowired
	@Qualifier("logIdentityDao")
	private LogIdentityDao logIdentityDao;
	
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
			Identity entity = identityDao.selectById(account.getIdIdentity());
			return entity;
		} else return null;
	}
	
	@Transactional
	public void deleteIdentity(String identityUid) throws BusinessException {
		if (identityUid != null) {
			if (identityUid.length() > 0) {
				Identity identity = identityDao.findByIdentityUid(identityUid);
				identityDao.logicalDelete(identity.getId());
				return;
			}
		}
		throw new BusinessException("Nessuna identità con identityUid='"+identityUid+"'");
	}
	
	@Transactional
	public Identity replaceIdentity(String redundantIdentityUid, String finalIdentityUid)
			throws BusinessException {
		Identity red = identityDao.findByIdentityUid(redundantIdentityUid);
		Identity fin = identityDao.findByIdentityUid(finalIdentityUid);
		if (red == null || fin == null) throw new BusinessException("Impossibile unire una Identity vuota");
		// Save the redundant UID
		fin.setIdentityUidOld(red.getIdentityUid());
		//Non merge di: email, address, password
		if (fin.getBirthDate() == null) fin.setBirthDate(red.getBirthDate());
		if (fin.getCodiceFiscale() == null) fin.setCodiceFiscale(red.getCodiceFiscale());
		if (fin.getFirstName() == null) fin.setFirstName(red.getFirstName());
		if (fin.getInterest() == null) fin.setInterest(red.getInterest());
		if (fin.getJob() == null) fin.setJob(red.getJob());
		if (fin.getLastName() == null) fin.setLastName(red.getLastName());
		if (fin.getPartitaIva() == null) fin.setPartitaIva(red.getPartitaIva());
		if (fin.getSchool() == null) fin.setSchool(red.getSchool());
		if (fin.getSex() == null) fin.setSex(red.getSex());
		if (fin.getTelephone() == null) fin.setTelephone(red.getTelephone());
		Identity result = identityDao.update(fin, ChangeEnum.MERGE);
		identityDao.logicalDelete(red.getId());
		return result;
	}
	
	@Transactional
	public void addLog(String identityUid, Integer idFederation,
			String functionName, Object parameterBean, String result) {
		logIdentityDao.insertLog(identityUid, idFederation, functionName, parameterBean, result);	
	}
}
