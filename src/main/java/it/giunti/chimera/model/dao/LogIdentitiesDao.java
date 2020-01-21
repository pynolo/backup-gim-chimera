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
import it.giunti.chimera.model.entity.LogIdentities;
import it.giunti.chimera.model.entity.Services;

@Repository("logIdentitiesDao")
public class LogIdentitiesDao {

	@PersistenceContext
	private EntityManager entityManager;
    @Autowired
    @Qualifier("servicesDao")
    private ServicesDao servicesDao;
    
	public LogIdentities selectById(int id) {
		return entityManager.find(LogIdentities.class, id);
	}
	
	public LogIdentities insert(LogIdentities item) {
		entityManager.persist(item);
		return item;
	}
	
	public LogIdentities update(LogIdentities item) {
		LogIdentities itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdentityUid(item.getIdentityUid());
		itemToUpdate.setIdService(item.getIdService());
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
		LogIdentities item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	public void createLog(String identityUid, Integer idService, String operation, String parameters, String result) throws BusinessException {
		if (idService == null) throw new BusinessException("Cannot create log without idService");
		LogIdentities li = new LogIdentities();
		li.setIdService(idService);
		li.setIdentityUid(identityUid);
		li.setOperation(operation);
		li.setParameters(parameters);
		li.setResult(result);
		li.setLastModified(new Date());
		insert(li);
	}
	
	@SuppressWarnings("unchecked")
	public List<LogIdentities> findByIdentityUid(String identityUid, int page, int pageSize) {
		String hql = "from LogIdentities as lup where " +
				"lup.identityUid = :uid1 " +
				"order by lup.id desc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("uid1", identityUid);
		q.setMaxResults(pageSize);
		q.setFirstResult(page*pageSize);
		List<LogIdentities> lupList = (List<LogIdentities>) q.getResultList();
		if (lupList != null) {
			for (LogIdentities lup:lupList) {
				Services s = servicesDao.selectById(lup.getIdService());
				lup.setServiceDescr(s.getName());
			}
		}
		return lupList;
	}
	
	@SuppressWarnings("unchecked")
	public Integer countByIdentityUid(String identityUid) {
		Long result = 0L;
		String hql = "select count(id) from LogIdentities as lup where " +
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
