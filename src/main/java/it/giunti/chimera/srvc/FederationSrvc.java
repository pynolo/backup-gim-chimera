package it.giunti.chimera.srvc;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.ErrorEnum;
import it.giunti.chimera.api05.bean.AccessKeyValidationBean;
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.IInputBean;
import it.giunti.chimera.model.dao.FederationDao;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.dao.IdentityFederationDao;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityFederation;

@Service("federationSrvc")
public class FederationSrvc {

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
	public AccessKeyValidationBean checkAccessKeyAndNull(IInputBean input) {
		AccessKeyValidationBean resultBean = new AccessKeyValidationBean();
		ErrorBean error = null;
		if (input != null) {
			Federation fed = federationDao.findByAccessKey(input.getAccessKey());
			if (fed != null) {
				// ACCESS KEY EXISTS
				resultBean.setFederation(fed);
				return resultBean;
			}
			// ACCESS KEY FAILS
			error = new ErrorBean();
			error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
			error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		} else {
			// NO INPUT
			error = new ErrorBean();
			error.setCode(ErrorEnum.EMPTY_PARAMETER.getErrorCode());
			error.setMessage("La richiesta e' priva di contenuto");
		}
		resultBean.setError(error);
		return resultBean;	
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
