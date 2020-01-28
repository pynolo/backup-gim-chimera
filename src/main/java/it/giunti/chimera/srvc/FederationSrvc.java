package it.giunti.chimera.srvc;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.ErrorEnum;
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.IInputBean;
import it.giunti.chimera.model.dao.FederationDao;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;

@Service("serviceSrvc")
public class FederationSrvc {

	@Autowired
	@Qualifier("federationDao")
	private FederationDao federationDao;
	@Autowired
	@Qualifier("identityDao")
	private IdentityDao identityDao;
	
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
	public ErrorBean checkAccessKeyAndNull(IInputBean input) {
		ErrorBean error = new ErrorBean();
		if (input != null) {
			Federation fed = federationDao.findByAccessKey(input.getAccessKey());
			if (fed != null) {
				// ACCESS KEY EXISTS
				return null;
			}
			// ACCESS KEY FAILS
			error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
			error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		} else {
			// NO INPUT
			error.setCode(ErrorEnum.EMPTY_PARAMETER.getErrorCode());
			error.setMessage("La richiesta e' priva di contenuto");
		}
		return error;	
	}

	
	@Transactional
	public List<Identity> findChangedIdentities(Long startTimestamp) {
		Date startDt = new Date(startTimestamp);
		List<Identity> identityList = identityDao.findByChangeTime(startDt);
		return identityList;
	}
}
