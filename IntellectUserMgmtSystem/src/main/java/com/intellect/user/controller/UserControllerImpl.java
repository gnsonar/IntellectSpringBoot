package com.intellect.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intellect.user.model.User;
import com.intellect.user.model.UserResponse;
import com.intellect.user.service.UserServiceImpl;

@RestController(value="/users")
public class UserControllerImpl implements UserController{

	@Autowired
	UserServiceImpl service;
	
	
	@PostMapping()
	public UserResponse createUser(@RequestBody User user) {
		
		UserResponse response = service.addUser(user);
		return response;
	}
	

	@PatchMapping
	public UserResponse updateUser(@RequestBody User user) {
		
		UserResponse response = service.updateUser(user);
		return response;
	}

	@DeleteMapping
	public UserResponse deleteUser(@RequestBody User user) {
		
		UserResponse response = service.deleteUser(user);
		return response;
	}
}
