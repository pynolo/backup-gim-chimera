package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.entity.Services;

@Repository("servicesDao")
public class ServicesDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Services selectById(int id) {
		return entityManager.find(Services.class, id);
	}
	
	public Services insert(Services item) {
		entityManager.persist(item);
		return item;
	}
	
	public Services update(Services item) {
		Services itemToUpdate = selectById(item.getId());
		itemToUpdate.setAccessKey(item.getAccessKey());
		itemToUpdate.setContact(item.getContact());
		itemToUpdate.setName(item.getName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Services item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Services> findByIdIdentity(Integer idIdentity) 
			throws EmptyResultException {
		String hql = "select serv "+
				"from IdentitiesServices as iserv, Services as serv where " +
				"serv.id = iserv.idService and "+
				"iserv.idIdentity = :id1 "+
				"order by iserv.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		List<Services> sList = (List<Services>) q.getResultList();
		if (sList != null) {
			if (sList.size() == 0) throw new EmptyResultException("No services found");
		} else {
			throw new EmptyResultException("No services found");
		}
		return sList;
	}
	
	@SuppressWarnings("unchecked")
	public Services findByAccessKey(String accessKey) throws EmptyResultException {
		String hql = "from Services as serv where " +
				"serv.accessKey = :s1 "+
				"order by serv.id ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", accessKey);
		List<Services> sList = (List<Services>) q.getResultList();
		if (sList != null) {
			if (sList.size() == 0) throw new EmptyResultException("No services found");
		} else {
			throw new EmptyResultException("No services found");
		}
		return sList.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Services> findAll() throws EmptyResultException {
		String hql = "from Services as s " +
				"order by s.id ";
		Query q = entityManager.createQuery(hql);
		List<Services> pList = (List<Services>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 0) throw new EmptyResultException("No services found");
		} else {
			throw new EmptyResultException("No services found");
		}
		return pList;
	}
}
