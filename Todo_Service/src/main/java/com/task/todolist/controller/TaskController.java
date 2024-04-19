package com.task.todolist.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.todolist.entity.Task;
import com.task.todolist.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
	@GetMapping("/user/{userId}")
	public List<Task> getTaskByUserId(@PathVariable Long userId){
		try {
			if(userId != null && userId!=0) {
				return taskService.getByUserId(userId);
				
			}
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		
		return null;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> findAllTask(){
		try {
			log.info("fetching all tasks");
			List<Task> taskList = taskService.getAllTask();
			log.info("taskList :{} ",taskList);
			if(!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		
		return null;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Integer id){
		try {
			
			log.info("get Task By Id : {}",id );
			Task task= taskService.getTaskById(id);
			log.info("task :{}",task);
			if(task!=null) {
				return new ResponseEntity<Task>(task,HttpStatus.FOUND);
			}
			else {
				return  new ResponseEntity<Task>(task,HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}
	
	@PostMapping
	public ResponseEntity<Task> addTask(@RequestBody Task task){
		try {
			log.info("adding new task : {}",task);
			Task addedTask = taskService.addTask(task);
			if(task!=null) {
				return new ResponseEntity<Task>(addedTask,HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<Task>(addedTask,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable Integer id){
		try {
			log.info("updating the task :{}", task);
			Task updatedTask=taskService.getTaskById(id);
			if(updatedTask!=null) {
				return new ResponseEntity<Task>(taskService.updateTask(task, id),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Task>(task,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}
	
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteTaskById(@PathVariable Integer id){
		try {
			log.info("deleting task by id : {} ",id);
			if(taskService.deleteTaskById(id)) {
				return new ResponseEntity<String>("Task deleted successfully",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Task not with given id not exist ",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}
	
	@GetMapping("/remainning")
	public ResponseEntity<List<Task>> getRemainningTask(){
		try {
			List<Task> taskList = taskService.getAllRemainningTask();
			log.info("taskList :{}",taskList);
			if(!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}
	
	@GetMapping("/creationDate")
	public ResponseEntity<List<Task>> getTaskByCreationDate(@RequestBody Task task){
		try {
			List<Task> taskList = taskService.getTaskByCreationDate(task.getCreationDate());
			log.info("taskList :{} ",taskList);
			if(!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
	return null;	
	}
	

	@GetMapping("/completionDate")
	public ResponseEntity<List<Task>> getTaskByCompletionDate(@RequestBody Task task){
		try {
			List<Task> taskList = taskService.getTaskByCompletionDate(task.getCompletionDate());
			log.info("taskList:{} ",taskList);
			if(!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Task>>(taskList,HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}",e);
		}
		return null;
	}

}
