package com.task.todolist.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.todolist.entity.Task;


public interface TaskController {
	@GetMapping("/user/{userId}")
	public List<Task> getTaskByUserId(@PathVariable Long userId);

	@GetMapping
	public ResponseEntity<List<Task>> findAllTask();

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Integer id);

	@PostMapping
	public ResponseEntity<String> addTask(@RequestBody Task task);

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteTaskById(@PathVariable Integer id);

	@PostMapping("/update/{id}")
	public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable Integer id);;

	@GetMapping("/completionDate")
	public ResponseEntity<List<Task>> getTaskByCompletionDate(@RequestBody Task task);

	@GetMapping("/creationDate")
	public ResponseEntity<List<Task>> getTaskByCreationDate(@RequestBody Task task);

	@GetMapping("/remainning")
	public ResponseEntity<List<Task>> getRemainningTask();
	
	
	@GetMapping("/search/namedquery")
	public Task findByTitle(@RequestParam String title);
	
//	@GetMapping("/search")
//	public ResponseEntity<Page<Task>> searchTasksByName(@RequestParam String title,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size);


	@GetMapping("/title/{title}")
	public List<Task> getTasksByName(@PathVariable String title);
		
	
}
