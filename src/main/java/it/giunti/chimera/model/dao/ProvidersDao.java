package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.entity.Providers;
import it.giunti.chimera.util.QueryUtil;

@Repository("providersDao")
public class ProvidersDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Providers selectById(int id) {
		return entityManager.find(Providers.class, id);
	}
	
	public Providers insert(Providers item) {
		entityManager.persist(item);
		return item;
	}
	
	public Providers update(Providers item) {
		Providers itemToUpdate = selectById(item.getId());
		itemToUpdate.setCasPrefix(item.getCasPrefix());
		itemToUpdate.setName(item.getName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Providers item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public Providers findByCasPrefix(String casPrefix) 
			throws EmptyResultException, DuplicateResultException {
		Providers result = null;
		String hql = "from Providers as p where " +
				"p.casPrefix = :id1 " +
				"order by p.id asc";
		Query q = entityManager.createQuery(hql);
		casPrefix = QueryUtil.escapeParam(casPrefix);
		q.setParameter("id1", casPrefix);
		List<Providers> pList = (List<Providers>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			} else {
				if (pList.size() == 0)
					throw new EmptyResultException("No rows in Providers have prefix="+casPrefix);
				if (pList.size() > 1)
					throw new DuplicateResultException("More rows in Providers have the same prefix");
			}
		}
		return result;
	}
	
}
