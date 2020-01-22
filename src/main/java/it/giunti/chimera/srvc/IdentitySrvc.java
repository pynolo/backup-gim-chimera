package it.giunti.chimera.srvc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.model.dao.IdentityDao;
import it.giunti.chimera.model.entity.Identity;

@Service("identitySrvc")
public class IdentitySrvc {

	@Autowired
	@Qualifier("identityDao")
	IdentityDao identityDao;
	
	@Transactional
	public Identity getIdentity(String identityUid) {
		return identityDao.findByIdentityUid(identityUid);
	}
	
	@Transactional
	public Identity getIdentityByEmail(String email) throws DuplicateResultException{
		Identity entity = identityDao.findByEmail(email);
		if (entity == null) entity = identityDao.findByUserName(email);
		return entity;
	}
}
