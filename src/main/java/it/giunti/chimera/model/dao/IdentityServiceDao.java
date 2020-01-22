package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.IdentityService;

@Repository("identityServiceDao")
public class IdentityServiceDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public IdentityService selectById(int id) {
		return entityManager.find(IdentityService.class, id);
	}
	
	public IdentityService insert(IdentityService item) {
		entityManager.persist(item);
		return item;
	}
	
	public IdentityService update(IdentityService item) {
		IdentityService itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdIdentity(item.getIdIdentity());
		itemToUpdate.setIdService(item.getIdService());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		IdentityService item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<IdentityService> findByServicesAndIdentity(Integer idIdentity, Integer idService)  {
		String hql = "from IdentityService as iserv where " +
				"iserv.idIdentity = :id1 and "+
				"iserv.idService = :id2 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		q.setParameter("id2", idService);
		List<IdentityService> sList = (List<IdentityService>) q.getResultList();
		return sList;
	}
	
}
