package it.giunti.chimera.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	//public LogIdentity update(LogIdentity item) {
	//	LogIdentity itemToUpdate = selectById(item.getId());
	//	itemToUpdate.setIdentityUid(item.getIdentityUid());
	//	itemToUpdate.setIdFederation(item.getIdFederation());
	//	itemToUpdate.setLastModified(item.getLastModified());
	//	itemToUpdate.setFunction(item.getFunction());
	//	itemToUpdate.setParameters(item.getParameters());
	//	itemToUpdate.setResult(item.getResult());
	//	itemToUpdate.setServiceDescr(item.getServiceDescr());
	//	entityManager.merge(itemToUpdate);
	//	entityManager.flush();
	//	return item;
	//}

	public void delete(int id) {
		LogIdentity item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	public void insertLog(String identityUid, Integer idFederation,
			String functionName, Object parameterBean, String result) {
		ObjectMapper mapper = new ObjectMapper();
		String parameterJson = "null"; 
		if (parameterBean != null) {
			try {
				parameterJson = mapper.writeValueAsString(parameterBean);
			} catch (JsonProcessingException e) {
				parameterJson = "obj->json conversion error";
			}
		}
		LogIdentity log = new LogIdentity();
		log.setIdentityUid(identityUid);
		log.setIdFederation(idFederation);
		log.setLastModified(new Date());
		log.setFunction(functionName);
		log.setParameters(parameterJson);
		if (result != null) {
			if (result.length() <= 255) {
				log.setResult(result);
			} else {
				log.setResult(result.substring(0, 255));
			}
		}
		entityManager.persist(log);
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
