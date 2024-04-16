package com.example.UserService.Controller;

import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Service.UserService;
import com.example.UserService.ServiceImpl.UserSeviceImpl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.Entity.User;
import com.example.UserService.Service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
@Autowired
private UserRepository userRepository;
	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody User user) {
//		String existEmployee = null;
//		if (user != null) {
//			try {
//				logger.info("Creating User: {}", user.getName());
//				existEmployee = userService.createUser(user);
//			} catch (Exception e) {
//				logger.error(e.toString());
//			}
//		} else {
//			logger.info("User is null");
//		}
//		return existEmployee;
		try {
			if (user != null) {
				logger.info("Creating User: {}", user.getName());
				userService.createUser(user);
				return new ResponseEntity<String>("User Created Sucessfully!!!", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("User Not found!!!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	@GetMapping("/getUsersById")
	public User getUsers(@RequestParam Long id) {
		if (id != 0) {
			logger.info("get user by {}", id);
			return userService.getUserById(id);
		} else {
			return null;
		}
	}

	@GetMapping("/getUsers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
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


	@GetMapping
	public List<User> getUsersBetweenDates(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		// Retrieve all users from the database
		List<User> allUsers = userRepository.findAll();

		// Filter users based on creationDate falling within the specified date range
		return allUsers.stream()
				.filter(user -> isWithinDateRange(user.getCreationDate(), startDate, endDate))
				.collect(Collectors.toList());
	}

	private boolean isWithinDateRange(LocalDate creationDate, LocalDate startDate, LocalDate endDate) {
		return !creationDate.isBefore(startDate) && !creationDate.isAfter(endDate);
	}
}