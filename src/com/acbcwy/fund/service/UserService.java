package com.acbcwy.fund.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acbcwy.fund.dao.UserDao;
import com.acbcwy.fund.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public void addUser(User user){
		userDao.addUser(user);
	}
	public void updateUser(User user){
		userDao.updateUser(user);
	}
	public void deleteUser(Long userId){
		userDao.deleteUser(userId);
	}
	public List<User> findUser(User user){
		return userDao.findUser(user);
	}
	
	public User findUserById(Long userId){
		return userDao.findUserById(userId);
	}
	
	public void openAccount(){

	}
}
