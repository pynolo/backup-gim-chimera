package it.giunti.chimera.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Federation;

@Repository("federationDao")
public class FederationDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Federation selectById(int id) {
		return entityManager.find(Federation.class, id);
	}
	
	public Federation insert(Federation item) {
		entityManager.persist(item);
		return item;
	}
	
	public Federation update(Federation item) {
		Federation itemToUpdate = selectById(item.getId());
		itemToUpdate.setAccessKey(item.getAccessKey());
		itemToUpdate.setContact(item.getContact());
		itemToUpdate.setName(item.getName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Federation item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Federation> findByIdIdentity(Integer idIdentity) {
		String hql = "select serv "+
				"from IdentityFederation as ifed, Federation as fed where " +
				"fed.id = ifed.idFederation and "+
				"ifed.idIdentity = :id1 "+
				"order by ifed.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		List<Federation> sList = (List<Federation>) q.getResultList();
		if (sList != null) {
			if (sList.size() > 0) return sList;
		}
		return new ArrayList<Federation>();
	}
	
	@SuppressWarnings("unchecked")
	public Federation findByUid(String uid) {
		String hql = "from Federation as fed where " +
				"fed.federationUid = :s1 "+
				"order by fed.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", uid);
		List<Federation> sList = (List<Federation>) q.getResultList();
		if (sList != null) {
			if (sList.size() > 0) return sList.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Federation findByAccessKey(String accessKey) {
		String hql = "from Federation as fed where " +
				"fed.accessKey = :s1 "+
				"order by fed.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", accessKey);
		List<Federation> sList = (List<Federation>) q.getResultList();
		if (sList != null) {
			if (sList.size() > 0) return sList.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Federation> findAll() {
		String hql = "from Federation as s " +
				"order by s.id ";
		Query q = entityManager.createQuery(hql);
		List<Federation> pList = (List<Federation>) q.getResultList();
		if (pList != null) {
			if (pList.size() > 0) return pList;
		}
		return new ArrayList<Federation>();
	}
}
