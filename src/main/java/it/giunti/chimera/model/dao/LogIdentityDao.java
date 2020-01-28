package it.giunti.chimera.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.LogIdentity;

@Repository("logIdentityDao")
public class LogIdentityDao {

	@PersistenceContext
	private EntityManager entityManager;
    @Autowired
    @Qualifier("federationDao")
    private FederationDao federationDao;
    
	public LogIdentity selectById(int id) {
		return entityManager.find(LogIdentity.class, id);
	}
	
	public LogIdentity insert(LogIdentity item) {
		entityManager.persist(item);
		return item;
	}
	
	public LogIdentity update(LogIdentity item) {
		LogIdentity itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdentityUid(item.getIdentityUid());
		itemToUpdate.setIdFederation(item.getIdFederation());
		itemToUpdate.setLastModified(item.getLastModified());
		itemToUpdate.setOperation(item.getOperation());
		itemToUpdate.setParameters(item.getParameters());
		itemToUpdate.setResult(item.getResult());
		itemToUpdate.setServiceDescr(item.getServiceDescr());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		LogIdentity item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	public void createLog(String identityUid, Integer idFederation, String operation, String parameters, String result) throws BusinessException {
		if (idFederation == null) throw new BusinessException("Cannot create log without idFederation");
		LogIdentity li = new LogIdentity();
		li.setIdFederation(idFederation);
		li.setIdentityUid(identityUid);
		li.setOperation(operation);
		li.setParameters(parameters);
		li.setResult(result);
		li.setLastModified(new Date());
		insert(li);
	}
	
	@SuppressWarnings("unchecked")
	public List<LogIdentity> findByIdentityUid(String identityUid, int page, int pageSize) {
		String hql = "from LogIdentity as lup where " +
				"lup.identityUid = :uid1 " +
				"order by lup.id desc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("uid1", identityUid);
		q.setMaxResults(pageSize);
		q.setFirstResult(page*pageSize);
		List<LogIdentity> lupList = (List<LogIdentity>) q.getResultList();
		if (lupList != null) {
			for (LogIdentity lup:lupList) {
				Federation s = federationDao.selectById(lup.getIdFederation());
				lup.setServiceDescr(s.getName());
			}
		}
		return lupList;
	}
	
	@SuppressWarnings("unchecked")
	public Integer countByIdentityUid(String identityUid) {
		Long result = 0L;
		String hql = "select count(id) from LogIdentity as lup where " +
				"lup.identityUid = :uid1";
		Query q = entityManager.createQuery(hql);
		q.setParameter("uid1", identityUid);
		List<Object> list = q.getResultList();
		if (list != null) {
			if (list.size() > 0) {
				result = (Long) list.get(0);
			}
		}
		return result.intValue();
	}
	
}
