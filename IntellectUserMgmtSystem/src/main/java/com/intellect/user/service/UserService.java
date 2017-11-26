package com.intellect.user.service;

import com.intellect.user.model.User;
import com.intellect.user.model.UserResponse;

public interface UserService {

	public UserResponse addUser(User user);
	
	public UserResponse updateUser(User user);
	
	public UserResponse deleteUser(User user);
}
