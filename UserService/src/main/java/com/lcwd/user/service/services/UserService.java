package com.lcwd.user.service.services;

import java.util.List;

import com.lcwd.user.service.entities.User;

public interface UserService {

	//User operations
	
	//create
	
	User saveUser(User user);
	
	//get all user
	List<User> getAllUser();
	
	//get single user of given userId
	
	User getUser(String userId);
	
	//TODO: delete
	void deleteUser(String userId);

	
	//TODO: update
	User updateUser(String userId);
}
