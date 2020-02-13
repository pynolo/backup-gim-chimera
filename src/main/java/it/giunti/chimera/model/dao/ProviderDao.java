package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Provider;
import it.giunti.chimera.util.QueryUtil;

@Repository("providerDao")
public class ProviderDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Provider selectById(int id) {
		return entityManager.find(Provider.class, id);
	}
	
	public Provider insert(Provider item) {
		entityManager.persist(item);
		return item;
	}
	
	public Provider update(Provider item) {
		Provider itemToUpdate = selectById(item.getId());
		itemToUpdate.setCasPrefix(item.getCasPrefix());
		itemToUpdate.setName(item.getName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Provider item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public Provider findByCasPrefix(String casPrefix) {
		Provider result = null;
		String hql = "from Provider as p where " +
				"p.casPrefix = :id1 " +
				"order by p.id asc";
		Query q = entityManager.createQuery(hql);
		casPrefix = QueryUtil.escapeParam(casPrefix);
		q.setParameter("id1", casPrefix);
		List<Provider> pList = (List<Provider>) q.getResultList();
		if (pList != null) {
			if (pList.size() >= 1) {
				result = pList.get(0);
			}
		}
		return result;
	}
	
}
