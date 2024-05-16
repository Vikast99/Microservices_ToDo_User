package com.task.todolist.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.todolist.entity.Task;
import com.task.todolist.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskControllerImpl implements TaskController {

	@Autowired
	private TaskService taskService;
	
	public ResponseEntity<List<Task>> findAllTask() {
		try {
			log.info("fetching all tasks");
			List<Task> taskList = taskService.getAllTask();
			log.info("taskList :{} ", taskList);
			if (!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}

		return null;
	}

	public List<Task> getTaskByUserId(@PathVariable Long userId) {
		try {
			if (userId != null && userId != 0) {
				return taskService.getByUserId(userId);

			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}

		return null;
	}

	

	public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
		if (id != 0 && id != null) {
			try {

				log.info("get Task By Id : {}", id);
				Task task = taskService.getTaskById(id);
				log.info("task :{}", task);
				if (task != null) {
					return new ResponseEntity<Task>(task, HttpStatus.FOUND);
				} else {
					return new ResponseEntity<Task>(task, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				log.info("exception : {}", e);
			}
		}
		return null;
	}

	public ResponseEntity<String> addTask(@RequestBody Task task) {
		try {
			if (task.getTitle() == null || task.getDescription() == null || task.getCompletionDate() == null
					|| task.getUserId() == null) {
				return new ResponseEntity<String>("task not added", HttpStatus.BAD_REQUEST);
			}
			log.info("adding new task : {}", task);
			Task addedTask = taskService.addTask(task);
			if (addedTask != null) {
				return new ResponseEntity<String>("task added", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("task not added", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable Integer id) {
		try {

			log.info("updating the task :{}", task);
			Task updatedTask = taskService.getTaskById(id);
			if (updatedTask != null) {
				return new ResponseEntity<Task>(taskService.updateTask(task, id), HttpStatus.OK);
			} else {
				return new ResponseEntity<Task>(task, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	public ResponseEntity<String> deleteTaskById(@PathVariable Integer id) {
		try {
			log.info("deleting task by id : {} ", id);
			if (taskService.deleteTaskById(id)) {
				return new ResponseEntity<String>("Task deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Task not with given id not exist ", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	public ResponseEntity<List<Task>> getRemainningTask() {
		try {
			List<Task> taskList = taskService.getAllRemainningTask();
			log.info("taskList :{}", taskList);
			if (!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	public ResponseEntity<List<Task>> getTaskByCreationDate(@RequestBody Task task) {
		try {
			List<Task> taskList = taskService.getTaskByCreationDate(task.getCreationDate());
			log.info("taskList :{} ", taskList);
			if (!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	public ResponseEntity<List<Task>> getTaskByCompletionDate(@RequestBody Task task) {
		try {
			List<Task> taskList = taskService.getTaskByCompletionDate(task.getCompletionDate());
			log.info("taskList:{} ", taskList);
			if (!taskList.isEmpty()) {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Task>>(taskList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			log.info("exception : {}", e);
		}
		return null;
	}

	
	
	@Override
	public Task findByTitle(@RequestParam String title) {
		return taskService.findByTitle(title);
	}

//	@Override
//	public ResponseEntity<Page<Task>> searchTasksByName(@RequestParam String title,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
//		org.springframework.data.domain.Pageable pageable=PageRequest.of(page, size);
//		Page<Task> tasks=taskService.findTasksByName(title, pageable);
//		return ResponseEntity.ok().body(tasks);
//		
//	}
	
	public List<Task> getTasksByName(@PathVariable String title){
		return taskService.getTasksByName(title);
	}

}
