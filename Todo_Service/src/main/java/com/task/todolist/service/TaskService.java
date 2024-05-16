package com.task.todolist.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Service;
import com.task.todolist.entity.Task;




@Service
public interface TaskService {
	
	public List<Task>  getAllTask();
	
	public Task getTaskById(Integer id);
	
	public Task addTask(Task task);
	
	public Task updateTask(Task task,Integer id);
	
	public boolean deleteTaskById(Integer id);
	
	public List<Task> getAllRemainningTask();
	
	public List<Task> getTaskByCreationDate(LocalDateTime creationDate);

	public List<Task> getTaskByCompletionDate(LocalDateTime completionDate);
	
	public List<Task> getByUserId(Long userId);
	
	
	public Task findByTitle(String title);
	
//	public org.springframework.data.domain.Page<Task> findTasksByName(String title,org.springframework.data.domain.Pageable pageable);
	
	public List<Task> getTasksByName(String title);

}
