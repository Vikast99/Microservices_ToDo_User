package com.example.UserService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
/*@NamedQueries({@NamedQuery(name = "User.findBycreationDate",
		query = "select * from users where creation_date between '2024-4-15' and '2024-4-16' ")})*/
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;

	@Setter
	private Date creationDate;

	public boolean isCreationDateWithinRange(Date startDate, Date endDate) {
		return !creationDate.before(startDate) && !creationDate.after(endDate);

	}
}
