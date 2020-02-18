package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.IdentityConsent;

@Repository("identityConsentDao")
public class IdentityConsentDao {

	@PersistenceContext
	private EntityManager entityManager;
    @Autowired
    @Qualifier("identityDao")
    private IdentityDao identityDao;
    
	public IdentityConsent selectById(int id) {
		return entityManager.find(IdentityConsent.class, id);
	}
	
	public IdentityConsent insert(IdentityConsent item) {
		entityManager.persist(item);
		return item;
	}
	
	public IdentityConsent update(IdentityConsent item) {
		IdentityConsent itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdIdentity(item.getIdIdentity());
		itemToUpdate.setMarketing(item.getMarketing());
		itemToUpdate.setMarketingDate(item.getMarketingDate());
		itemToUpdate.setProfiling(item.getProfiling());
		itemToUpdate.setConsentRange(item.getConsentRange());
		itemToUpdate.setTos(item.getTos());
		itemToUpdate.setTosDate(item.getTosDate());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		IdentityConsent item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public IdentityConsent findByIdentityUid(String identityUid) {
		Identity identity = identityDao.findByIdentityUid(identityUid);
		String hql = "from IdentityConsent as ic where " +
				"ic.idIdentity = :id1 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", identity.getId());
		List<IdentityConsent> list = (List<IdentityConsent>) q.getResultList();
		if (list != null) {
			if (list.size() == 1) {
				return list.get(0);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public IdentityConsent findByIdentityUidAndRange(String identityUid, String range) {
		Identity identity = identityDao.findByIdentityUid(identityUid);
		String hql = "from IdentityConsent as ic where " +
				"ic.idIdentity = :id1 and " +
				"ic.consentRange = :id2 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", identity.getId());
		q.setParameter("id2", range);
		List<IdentityConsent> list = (List<IdentityConsent>) q.getResultList();
		if (list != null) {
			if (list.size() == 1) {
				return list.get(0);
			}
		}
		return null;
	}
		
}
