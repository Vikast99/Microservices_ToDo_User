package com.example.UserService.Service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.UserService.Entity.Task;

@FeignClient(url = "http://localhost:8084", value = "TaskClient")
public interface TaskClient {
	
	@GetMapping("/task/user/{userId}")
	List<Task> getTasksOfUser(@PathVariable Long userId);
	
//	@PostMapping("/task")
//	Task addTask(@RequestBody Task task);
}
