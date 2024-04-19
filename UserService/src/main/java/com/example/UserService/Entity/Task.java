package com.example.UserService.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {
	private Integer id;

	private String title;

	private String description;

	@CreationTimestamp
	private LocalDate creationDate;

	private LocalDate completionDate;

	private priorityEnum priority;

	private Integer userid;



}
