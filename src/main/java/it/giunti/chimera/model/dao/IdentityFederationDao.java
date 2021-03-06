package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.IdentityFederation;

@Repository("identityFederationDao")
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
	public IdentityFederation findByIdentityAndFederation(Integer idIdentity, Integer idFederation)  {
		String hql = "from IdentityFederation as ifed where " +
				"ifed.idIdentity = :id1 and "+
				"ifed.idFederation = :id2 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		q.setParameter("id2", idFederation);
		List<IdentityFederation> ifedList = (List<IdentityFederation>) q.getResultList();
		if (ifedList.size() > 0) {
			return ifedList.get(0);
		}
		return null;
	}
	
}
