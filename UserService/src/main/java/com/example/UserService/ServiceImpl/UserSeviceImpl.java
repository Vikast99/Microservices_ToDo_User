package com.example.UserService.ServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserService.Entity.Task;
import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Service.TaskClient;
import com.example.UserService.Service.UserService;

@Service
public class UserSeviceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskClient taskClient;

	private static final Logger logger = LoggerFactory.getLogger(UserSeviceImpl.class);

	@Override
	public String createUser(User user) {
		try {
			if (!Objects.equals(user.getEmail(), "") && !Objects.equals(user.getName(), "")) {
				if (user.getEmail() != null && user.getName() != null) {
					Optional<User> existUser = userRepository.findByemail(user.getEmail());
					if (existUser.isPresent()) {
						logger.warn("User already exist");
						return "User already exist";
					} else {
						userRepository.save(user);
						logger.info("User saved successfully");
						return "User saved successfully";
					}
				} else {
					logger.warn("User can not be  null");
					return "User can not be  null";
				}
			} else {
				logger.warn("User can not be  Empty");
				return "User can not be  Empty";
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return "something went wrong";
	}

	@Override
	public Object getUserById(Long id) {
		try {
			Optional<User> getUser = userRepository.findById(id);
			if (getUser.isPresent()) {
				User user = getUser.get();

				// fetch task by userId
				List<Task> taskList = taskClient.getTasksOfUser(user.getId()).stream()
						.sorted((task1, task2) -> task2.getPriority().compareTo(task1.getPriority()))
						.collect(Collectors.toList());
				// set list of fetched tasks to user
				user.setTask(taskList);
				logger.info("succesfully get the user");
				return user;
			} else {
				logger.warn("invaliad user id");
				return "invaliad user id";
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return "something went wrong";
	}

	@Override
	public List<User> getAllUsers() {
		try {
			List<User> userlist = userRepository.findAll();

			List<User> newUserList = userlist.stream().map(user -> {
				user.setTask(taskClient.getTasksOfUser(user.getId()));
				return user;
			}).collect(Collectors.toList());
			return newUserList;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public String deleteUser(Long id) {
		logger.info("finding the id of the User for getUserById");
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			logger.error("User ID is  not present=" + id);
			return "user is not present";
		} else {
			userRepository.deleteById(id);
			logger.info("Uesr with ID " + id + " deleted successfully.");
			return "user deleted sucessfully";
		}
	}

	@Override
	public User updateUser(Long id, User updateUser) throws Exception {

		if (id == null || updateUser == null) {
			throw new IllegalArgumentException("User id and updated user cannot be null");
		}

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new IllegalArgumentException("User not found with id: " + id);
		}

		if (updateUser.getName() != null && !updateUser.getName().isEmpty()) {
			user.setName(updateUser.getName());
		}
		if (updateUser.getEmail() != null && !updateUser.getEmail().isEmpty()) {
			user.setEmail(updateUser.getEmail());
		}

		try {
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new RuntimeException("Error updating user: " + e.getMessage());
		}

	}

	@Override
	public List<User> getUsersBetweenDates(Date startDate, Date endDate) {
		List<User> allUsers = userRepository.findAll();

		return allUsers.stream().filter(user -> user.isCreationDateWithinRange(startDate, endDate))
				.collect(Collectors.toList());
	}

}
