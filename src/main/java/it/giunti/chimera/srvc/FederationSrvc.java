package it.giunti.chimera.srvc;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.FederationDao;
import it.giunti.chimera.model.entity.Federation;

@Service("serviceSrvc")
public class FederationSrvc {

	@Autowired
	@Qualifier("federationDao")
	private FederationDao federationDao;
	
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
}
