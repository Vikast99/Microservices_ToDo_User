package com.task.todolist.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String title;

	private String description;

	@CreationTimestamp
	private LocalDate creationDate;

	private LocalDate completionDate;

	private Long userId;

	

}
