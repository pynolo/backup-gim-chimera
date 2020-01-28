package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.IdentityFederation;

@Repository("identityServiceDao")
public class IdentityFederationDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public IdentityFederation selectById(int id) {
		return entityManager.find(IdentityFederation.class, id);
	}
	
	public IdentityFederation insert(IdentityFederation item) {
		entityManager.persist(item);
		return item;
	}
	
	public IdentityFederation update(IdentityFederation item) {
		IdentityFederation itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdIdentity(item.getIdIdentity());
		itemToUpdate.setIdFederation(item.getIdFederation());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		IdentityFederation item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<IdentityFederation> findByServicesAndIdentity(Integer idIdentity, Integer idService)  {
		String hql = "from IdentitySrvc as iserv where " +
				"iserv.idIdentity = :id1 and "+
				"iserv.idService = :id2 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		q.setParameter("id2", idService);
		List<IdentityFederation> sList = (List<IdentityFederation>) q.getResultList();
		return sList;
	}
	
}
