package it.giunti.chimera.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.LookupConsentRange;

@Repository("lookupConsentRangeDao")
public class LookupConsentRangeDao {

	@PersistenceContext
	private EntityManager entityManager;

	public LookupConsentRange selectById(String id) {
		return entityManager.find(LookupConsentRange.class, id);
	}
		
	@SuppressWarnings("unchecked")
	public List<LookupConsentRange> findAll() {
		String hql = "from LookupConsentRange as lcr " +
				"order by lcr.id ";
		Query q = entityManager.createQuery(hql);
		List<LookupConsentRange> lcrList = (List<LookupConsentRange>) q.getResultList();
		if (lcrList != null) {
			if (lcrList.size() > 0) return lcrList;
		}
		return new ArrayList<LookupConsentRange>();
	}
}
