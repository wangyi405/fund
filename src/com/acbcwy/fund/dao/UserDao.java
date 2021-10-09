package com.acbcwy.fund.dao;

import java.util.List;

import com.acbcwy.fund.model.User;

public interface UserDao {
	public List<User> findUser(User user);
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(Long userId);
	public User findUserById(Long userId);
}
