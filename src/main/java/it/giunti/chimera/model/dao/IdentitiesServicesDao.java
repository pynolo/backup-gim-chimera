package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.IdentitiesServices;

@Repository("identitiesServicesDao")
public class IdentitiesServicesDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public IdentitiesServices selectById(int id) {
		return entityManager.find(IdentitiesServices.class, id);
	}
	
	public IdentitiesServices insert(IdentitiesServices item) {
		entityManager.persist(item);
		return item;
	}
	
	public IdentitiesServices update(IdentitiesServices item) {
		IdentitiesServices itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdIdentity(item.getIdIdentity());
		itemToUpdate.setIdService(item.getIdService());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		IdentitiesServices item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<IdentitiesServices> findByServicesAndIdentity(Integer idIdentity, Integer idService)  {
		String hql = "from IdentitiesServices as iserv where " +
				"iserv.idIdentity = :id1 and "+
				"iserv.idService = :id2 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		q.setParameter("id2", idService);
		List<IdentitiesServices> sList = (List<IdentitiesServices>) q.getResultList();
		return sList;
	}
	
}
