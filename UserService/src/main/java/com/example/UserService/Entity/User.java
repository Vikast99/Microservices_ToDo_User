package com.example.UserService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
@Entity

public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Transient
	private List<Task> task;
	

	@Column(name = "creation_date")
	@CreationTimestamp
	private Date creationDate;
	
	@Column(name="fileName")
	private String fileName;
	
	@Column
	private String username;
	
	
	@Column(name="password")
	private String password;
	
	private String authority;
	
	@Column(name="otp")
	private String otp;
	
	

	
	

}
