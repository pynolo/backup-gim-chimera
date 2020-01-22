package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Provider;
import it.giunti.chimera.model.entity.Province;
import it.giunti.chimera.util.QueryUtil;

@Repository("provinceDao")
public class ProvinceDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Provider selectById(int id) {
		return entityManager.find(Provider.class, id);
	}
		
	@SuppressWarnings("unchecked")
	public Province findByCode(String pv) {
		if (pv == null) return null;
		Province result = null;
		String hql = "from Province as pv where " +
				"pv.code like :id1 " +
				"order by pv.id asc";
		Query q = entityManager.createQuery(hql);
		pv = QueryUtil.escapeParam(pv);
		q.setParameter("id1", pv);
		List<Province> pList = (List<Province>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			}
		}
		return result;
	}
	
}
