package com.task.todolist.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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

	private priorityEnum priority;

	private Long userId;

	@ElementCollection
	@CollectionTable(name = "CollectionHistory", joinColumns = @JoinColumn(name = "taskId"))
	List<LocalDate> completionDateHistory = new ArrayList<LocalDate>();

	public void updatecompletionDateHistory(LocalDate completionDate) {
		if (completionDate != null) {
			completionDateHistory.add(completionDate);
		}
	}

}
