package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Providers;
import it.giunti.chimera.model.entity.Provinces;
import it.giunti.chimera.util.QueryUtil;

@Repository("provincesDao")
public class ProvincesDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Providers selectById(int id) {
		return entityManager.find(Providers.class, id);
	}
		
	@SuppressWarnings("unchecked")
	public Provinces findByCode(String pv) {
		if (pv == null) return null;
		Provinces result = null;
		String hql = "from Provinces as pv where " +
				"pv.code like :id1 " +
				"order by pv.id asc";
		Query q = entityManager.createQuery(hql);
		pv = QueryUtil.escapeParam(pv);
		q.setParameter("id1", pv);
		List<Provinces> pList = (List<Provinces>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			}
		}
		return result;
	}
	
}
