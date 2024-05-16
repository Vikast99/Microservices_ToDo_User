package com.example.UserService.ServiceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.UserService.Entity.Task;
import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Service.TaskClient;
import com.example.UserService.Service.UserService;

@Service
public class UserSeviceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	

    

	@Autowired
	private TaskClient taskClient;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
						UserSeviceImpl userServiceImp=new UserSeviceImpl(); 
						user.setPassword(passwordEncoder.encode(user.getPassword()));
						
						int pLength=12;
						if(!userServiceImp.isPasswordValid(user.getPassword(),pLength)) {
							return "password must contain one uppercase, one lowercase and one special character or length is greater than pLength";
						}
						
						
						
				             
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
		try {
			List<User> userList = userRepository.findByCreationDateBetween(startDate, endDate).get();
			if (!userList.isEmpty())
				logger.info("user List is not empty");
			return userList;
		} catch (Exception e) {
			logger.error("exception :{}", e);
		}
		return null;
	}

	@Override
	public String uploadImage(String path, MultipartFile file) {
		// TODO Auto-generated method stub

		// File name

		String name = file.getOriginalFilename();
		// File Path

		String filePath = path + File.separator + name;

		// create folder if not created

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy

		try {

			File files = new File("images/" + file.getOriginalFilename());
			if (files.exists()) {
				System.out.println("file already exist");
			} else {
				Files.copy(file.getInputStream(), Paths.get(filePath));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	
	public boolean isPasswordValid(String password, int pLength) {
		
		int strength=10;
		
		if(password.length()>=pLength) {
			return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[@#$%^&+=].*");
			
			
		}
		
		//check if the password contains atleast one uppercase,one lowercase,one special character and number
		return false;
	}
	
	
	public void generateAndSendOtp(String email) {
		Optional<User> optionalUser=userRepository.findByemail(email);
		if(!optionalUser.isPresent()) {
			throw new RuntimeException("user not found");
			
		}
		
		User user=optionalUser.get();
		String otp=generateOtp();
		sendOtpByMail(email, otp);
		
		user.setOtp(otp);
		userRepository.save(user);
	}
	
	public boolean validateOtp(String email,String otp) {
		Optional<User> optionalUser=userRepository.findByemail(email);
		if(!optionalUser.isPresent()) {
			throw new RuntimeException("user not found");
		}
		
		User user=optionalUser.get();
		String storedOtp=user.getOtp();
		if(storedOtp==null || !storedOtp.equals(otp)) {
			return false;    //invalid otp
		}
		
		//clear the otp field in the user entity after successful validation
		user.setOtp(null);
		userRepository.save(user);
		return true;
	}
	
	public String generateOtp() {
		
		//generate a 6 digit random otp
		
		Random random=new Random();
		int otp=100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
	
	public void sendOtpByMail(String email,String otp) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("otp verification");
		mailMessage.setText("your otp is:"+otp);
		javaMailSender.send(mailMessage);
	}

	

	
	
	


	
	
	
	
	
	

}
