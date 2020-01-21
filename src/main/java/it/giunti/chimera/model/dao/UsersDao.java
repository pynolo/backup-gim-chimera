package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.entity.Users;

@Repository("usersDao")
public class UsersDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Users selectById(int id) {
		return entityManager.find(Users.class, id);
	}
	
	public Users insert(Users item) {
		entityManager.persist(item);
		return item;
	}
	
	public Users update(Users item) {
		Users itemToUpdate = selectById(item.getId());
		itemToUpdate.setPassword(item.getPassword());
		itemToUpdate.setUserName(item.getUserName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Users item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public Users findByUserName(String userName) 
			throws EmptyResultException, DuplicateResultException {
		Users result = null;
		String hql = "from Users as u where " +
				"u.userName = :s1 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", userName);
		List<Users> pList = (List<Users>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			} else {
				if (pList.size() == 0)
					throw new EmptyResultException("No user having code="+userName);
				if (pList.size() > 1)
					throw new DuplicateResultException("More rows in Users have the same code="+userName);
			}
		}
		return result;
	}
}
