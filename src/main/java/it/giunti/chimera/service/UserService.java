package it.giunti.chimera.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.UserDao;
import it.giunti.chimera.model.entity.User;

@Service("userService")
public class UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Transactional
	public User getUserByUsername(String username) {
		return userDao.findByUserName(username);
	}
	
	@Transactional
	public User addUser(User task) {
		return userDao.insert(task);
	}

	@Transactional
	public User modifyUser(User task) {
		return userDao.update(task);
	}

	@Transactional
	public List<User> getAllUsers() {
		return userDao.selectAll();
	}

	@Transactional
	public void removeUser(String username) {
		User user = userDao.findByUserName(username);
		userDao.delete(user.getId());
	}

}
