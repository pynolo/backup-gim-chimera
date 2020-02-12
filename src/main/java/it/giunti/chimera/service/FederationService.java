package it.giunti.chimera.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.FederationDao;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.IdentityFederationDao;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityFederation;

@Service("federationService")
public class FederationService {

	@Autowired
	@Qualifier("federationDao")
	private FederationDao federationDao;
	@Autowired
	@Qualifier("identityDao")
	private IdentityDao identityDao;
	@Autowired
	@Qualifier("identityFederationDao")
	private IdentityFederationDao identityFederationDao;
	
	@Transactional
	public Federation findFederationByUid(String uid) {
		Federation service = federationDao.findByUid(uid);
		return service;
	}

	@Transactional
	public Federation findFederationByAccessKey(String accessKey) {
		Federation service = federationDao.findByAccessKey(accessKey);
		return service;
	}
	
	@Transactional
	public List<Federation> findAllFederations() {
		List<Federation> list = federationDao.findAll();
		return list;
	}
	
	@Transactional
	public List<Identity> findChangedIdentities(Long startTimestamp) {
		Date startDt = new Date(startTimestamp);
		List<Identity> identityList = identityDao.findByChangeTime(startDt);
		return identityList;
	}
	
	@Transactional
	public IdentityFederation addOrUpdateIdentityFederation(
			Integer idIdentity, Integer idFederation) {
		Date now = new Date();
		IdentityFederation ifed = identityFederationDao
				.findByIdentityAndFederation(idIdentity, idFederation);
		if (ifed == null) {
			ifed = new IdentityFederation();
			ifed.setIdIdentity(idIdentity);
			ifed.setIdFederation(idFederation);
			ifed.setFirstAccess(now);
			ifed.setLastAccess(now);
			identityFederationDao.insert(ifed);
		} else {
			ifed.setLastAccess(now);
			identityFederationDao.update(ifed);
		}
		return ifed;
	}
}
