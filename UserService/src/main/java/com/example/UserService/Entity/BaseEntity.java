 package com.example.UserService.Entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

}
