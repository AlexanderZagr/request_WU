package com.requst.wu.service;

import com.requst.wu.model.User;

public interface UserServiceImpl {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
