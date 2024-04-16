package com.example.UserService.Controller;

import com.example.UserService.Entity.User;
import com.example.UserService.Service.UserService;
import com.example.UserService.ServiceImpl.UserSeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/createUser")
	String createUser(@RequestBody User user) {
		String existEmployee = null;
		if (user != null) {
			try {
				logger.info("Creating User: {}", user.getName());
				existEmployee = userService.createUser(user);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		} else {
			logger.info("User is null");
		}
		return existEmployee;
	}

	@PostMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam Long id) {
		try {
			if (id != 0) {
				logger.info("Deleting User with id: {}", id);
				String existEmployee = userService.deleteUser(id);
				return ResponseEntity.status(HttpStatus.CREATED).body(existEmployee);
			} else {
				logger.warn("please enter the id");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}
}