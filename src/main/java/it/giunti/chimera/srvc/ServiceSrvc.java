package it.giunti.chimera.srvc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.ServiceDao;

@Service("serviceSrvc")
public class ServiceSrvc {

	@Autowired
	@Qualifier("serviceDao")
	private ServiceDao serviceDao;
	
	@Transactional
	public it.giunti.chimera.model.entity.Service findServiceByAccessKey(String accessKey) {
		it.giunti.chimera.model.entity.Service service = serviceDao.findByAccessKey(accessKey);
		return service;
	}
}
