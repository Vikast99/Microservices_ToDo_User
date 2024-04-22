package com.task.todolist.entity;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
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
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "creation_date")
	@CreationTimestamp
	private LocalDate creationDate;

	@Column(name = "completion_date")
	private LocalDate completionDate;

	@Column(name = "priority")
	private priorityEnum priority;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "status")
	private Status status;
	
	@ElementCollection
	@CollectionTable(name = "CollectionHistory", joinColumns = @JoinColumn(name = "taskId"))
	List<LocalDate> completionDateHistory = new ArrayList<LocalDate>();

}
