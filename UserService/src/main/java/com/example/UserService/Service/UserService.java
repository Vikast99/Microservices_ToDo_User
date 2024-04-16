package com.example.UserService.Service;

import java.util.List;

import com.example.UserService.Entity.User;

public interface UserService {

	String createUser(User user);

	String deleteUser(Long id);

	User getUserById(Long id);

	List<User> getAllUsers();

}
