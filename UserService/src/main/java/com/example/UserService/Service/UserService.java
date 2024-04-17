package com.example.UserService.Service;

import java.sql.Date;
import java.util.List;

import com.example.UserService.Entity.User;

public interface UserService {

	String createUser(User user);

	String deleteUser(Long id);

	Object getUserById(Long id);

	List<User> getAllUsers();

	User updateUser(Long id, User updateUser) throws Exception;

	List<User> getUsersBetweenDates(Date startDate, Date endDate);

}
