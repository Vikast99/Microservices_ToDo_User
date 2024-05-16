package com.example.UserService.Repository;

import com.example.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByemail(String email);
	
	public Optional<List<User>> findByCreationDateBetween(Date startDate, Date endDate);

	public User findByUsername(String username);

	
	
	

}
