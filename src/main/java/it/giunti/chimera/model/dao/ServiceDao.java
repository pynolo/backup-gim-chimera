package it.giunti.chimera.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.Service;

@Repository("serviceDao")
public class ServiceDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Service selectById(int id) {
		return entityManager.find(Service.class, id);
	}
	
	public Service insert(Service item) {
		entityManager.persist(item);
		return item;
	}
	
	public Service update(Service item) {
		Service itemToUpdate = selectById(item.getId());
		itemToUpdate.setAccessKey(item.getAccessKey());
		itemToUpdate.setContact(item.getContact());
		itemToUpdate.setName(item.getName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Service item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> findByIdIdentity(Integer idIdentity) {
		String hql = "select serv "+
				"from IdentitySrvc as iserv, Service as serv where " +
				"serv.id = iserv.idService and "+
				"iserv.idIdentity = :id1 "+
				"order by iserv.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		List<Service> sList = (List<Service>) q.getResultList();
		if (sList != null) {
			if (sList.size() > 0) return sList;
		}
		return new ArrayList<Service>();
	}
	
	@SuppressWarnings("unchecked")
	public Service findByAccessKey(String accessKey) {
		String hql = "from Service as serv where " +
				"serv.accessKey = :s1 "+
				"order by serv.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", accessKey);
		List<Service> sList = (List<Service>) q.getResultList();
		if (sList != null) {
			if (sList.size() > 0) return sList.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Service> findAll() {
		String hql = "from Service as s " +
				"order by s.id ";
		Query q = entityManager.createQuery(hql);
		List<Service> pList = (List<Service>) q.getResultList();
		if (pList != null) {
			if (pList.size() > 0) return pList;
		}
		return new ArrayList<Service>();
	}
}
