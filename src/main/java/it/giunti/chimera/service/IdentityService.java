package it.giunti.chimera.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.ChangeEnum;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.LogIdentityDao;
import it.giunti.chimera.model.dao.ProviderAccountDao;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;
import it.giunti.chimera.mvc.Conflict409Exception;
import it.giunti.chimera.mvc.NotFound404Exception;
import it.giunti.chimera.mvc.UnprocessableEntity422Exception;

@Service("identityService")
public class IdentityService {


	@Autowired
	@Qualifier("socialService")
	private SocialService socialService;
	
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
	public Identity getIdentityByEmail(String email) {
		Identity entity = identityDao.findByEmail(email);
		if (entity == null) entity = identityDao.findByUserName(email);
		return entity;
	}
	
	@Transactional
	public Identity getIdentityBySocialId(String socialId) 
			throws UnprocessableEntity422Exception, Conflict409Exception {
		String pac4jPrefix = socialService.getCasPrefixFromSocialId(socialId);
		String accountIdentifier = socialService.getIdentifierFromSocialId(socialId);
		ProviderAccount account = providerAccountDao.findByProviderIdentifier(pac4jPrefix, accountIdentifier);
		if (account != null) {
			Identity entity = identityDao.selectById(account.getIdIdentity());
			return entity;
		} else return null;
	}
	
	@Transactional
	public List<Identity> findIdentityByReplacingUid(String identityUid) {
		List<Identity> resultList = new ArrayList<Identity>();
		if (identityUid != null) {
			resultList = identityDao.findByReplacingUid(identityUid);
		}
		return resultList;
	}
	
	@Transactional
	public void deleteIdentity(String identityUid) throws NotFound404Exception {
		if (identityUid != null) {
			if (identityUid.length() > 0) {
				Identity identity = identityDao.findByIdentityUid(identityUid);
				identityDao.logicalDelete(identity.getId());
				return;
			}
		}
		throw new NotFound404Exception("Nessuna identità con identityUid='"+identityUid+"'");
	}
	
	@Transactional
	public Identity replaceIdentity(String redundantIdentityUid, String finalIdentityUid)
			throws UnprocessableEntity422Exception {
		Identity red = identityDao.findByIdentityUid(redundantIdentityUid);
		Identity fin = identityDao.findByIdentityUid(finalIdentityUid);
		if (red == null || fin == null) throw new UnprocessableEntity422Exception("Impossibile unire una Identity vuota");
		// Redundant identity is marked as replaced by the final one
		red.setReplacedByUid(finalIdentityUid);
		identityDao.update(red, ChangeEnum.REPLACE);
		// Cycles all identities that had been replaced by the redundant one,
		// marking them as replaced by the final one instead
		List<Identity> deletedList = identityDao.findByReplacingUid(redundantIdentityUid);
		for (Identity delIdt:deletedList) {
			delIdt.setReplacedByUid(finalIdentityUid);
			identityDao.update(delIdt, ChangeEnum.REPLACE);
		}
		//Replaces empty property if redundant has info
		//Not to be merged: email, address, password
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
		if (fin.getGiuntiCard() == null) fin.setGiuntiCard(red.getGiuntiCard());
		if (fin.getGiuntiCardMode() == null) fin.setGiuntiCardMode(red.getGiuntiCardMode());
		Identity result = identityDao.update(fin, ChangeEnum.REPLACE);
		identityDao.logicalDelete(red.getId());
		return result;
	}
	
	@Transactional
	public void addLog(String identityUid, Integer idFederation,
			String functionName, Object parameterBean, String errorMsg) {
		String result = "OK";
		if (errorMsg != null) result = errorMsg;
		logIdentityDao.insertLog(identityUid, idFederation, functionName, parameterBean, result);	
	}
}
