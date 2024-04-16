package com.example.UserService.Service;

import com.example.UserService.Entity.User;

public interface UserService {
	
	String createUser(User user);

	String deleteUser(Long id);
}
