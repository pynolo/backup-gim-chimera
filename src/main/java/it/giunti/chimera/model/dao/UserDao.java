package it.giunti.chimera.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.model.entity.User;

@Repository("userDao")
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
		//itemToUpdate.setPassword(item.getPassword());
		itemToUpdate.setUsername(item.getUsername());
		itemToUpdate.setRole(item.getRole());
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
	public User findByUsername(String username) {
		User result = null;
		String hql = "from User as u where " +
				"u.username = :s1 ";
		Query q = entityManager.createQuery(hql);
		q.setParameter("s1", username);
		List<User> pList = (List<User>) q.getResultList();
		if (pList != null) {
			if (pList.size() > 1) {
				result = pList.get(0);
			} else {
				return null;
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> selectAll() {
		Query query = entityManager.createQuery("from User as user order by user.username");
		return (List<User>) query.getResultList();
	}
	
}
