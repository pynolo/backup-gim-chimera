package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.entity.User;

@Repository("usersDao")
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public User selectById(int id) {
		return entityManager.find(User.class, id);
	}
	
	public User insert(User item) {
		entityManager.persist(item);
		return item;
	}
	
	public User update(User item) {
		User itemToUpdate = selectById(item.getId());
		itemToUpdate.setPassword(item.getPassword());
		itemToUpdate.setUserName(item.getUserName());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		User item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public User findByUserName(String userName) 
			throws EmptyResultException, DuplicateResultException {
		User result = null;
		String hql = "from User as u where " +
				"u.userName = :s1 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", userName);
		List<User> pList = (List<User>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			} else {
				if (pList.size() == 0)
					throw new EmptyResultException("No user having code="+userName);
				if (pList.size() > 1)
					throw new DuplicateResultException("More rows in User have the same code="+userName);
			}
		}
		return result;
	}
}
