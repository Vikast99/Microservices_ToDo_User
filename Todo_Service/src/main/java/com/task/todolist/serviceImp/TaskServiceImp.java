package com.task.todolist.serviceImp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.todolist.entity.Task;
import com.task.todolist.repository.TaskRepository;
import com.task.todolist.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImp implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> getAllTask() {
	
		try {
			List<Task> taskList=taskRepository.findAll();
			log.info("list of tasks "+taskList);
			if(!taskList.isEmpty()) {
				return taskList;
			}
			
		}catch (Exception e) {
			log.error("exception "+ e);
		}
		
			return null;
	
	}

	@Override
	public Task getTaskById(Integer id) {
		
		try {
			Optional<Task> task=taskRepository.findById(id);
			if(!task.isEmpty()) {
				return task.get();
			}
		} catch (Exception e) {
			log.error("exception "+e);
		}
		
		return null;
	}

	@Override
	public Task addTask(Task task) {
		try {
			
			Optional<Task> existingTask = taskRepository.findByTitle(task.getTitle().toLowerCase()); 
			log.info("existingTask "+existingTask);
			if(existingTask.isEmpty()) {
				task.setTitle(task.getTitle());
				return taskRepository.save(task);
			}
			
		} catch (Exception e) {
			log.error("exception "+e);
		}
		
		return null;	
		}
		
	
	@Override
	public Task updateTask(Task newTask, Integer id) {
		try {
			Optional<Task> task = taskRepository.findById(id);
			log.info("task "+task);

			if(task.isPresent()) {
				if(newTask.getTitle()!=null) {
					task.get().setTitle(newTask.getTitle());
				}
				if(newTask.getDescription()!=null) {
					task.get().setDescription(newTask.getDescription());
				}
				if(newTask.getCompletionDate()!=null) {
					task.get().setCompletionDate(newTask.getCompletionDate());
				}
					return taskRepository.save(task.get());
					
		
			}
			
		} catch (Exception e) {
			log.error("exception "+e);
		}
	
		return null;
	}

	@Override
	public boolean deleteTaskById(Integer id) {
		
		try {
			Optional<Task> task=taskRepository.findById(id);
			if(task.isPresent()) {
				taskRepository.deleteById(id);
				return true;
			}
		} catch (Exception e) {
			log.error("exception "+e);
		}
		return false;
		
	}

	@Override
	public List<Task> getAllRemainningTask() {
		try {
			LocalDate date=LocalDate.now();
			List<Task> taskList = taskRepository.getAllRemainningTask(date);
			log.info("taskList "+taskList);
			if(!taskList.isEmpty()) {
				return taskList;
			}
			
		} catch (Exception e) {
			log.error("exception "+e);
		}
		return null;
	}

	@Override
	public List<Task> getTaskByCreationDate(LocalDate creationDate) {
		try {
			List<Task> task=taskRepository.findTaskByCreationDate(creationDate);
			log.info("taskList "+task);
			if(!task.isEmpty()){
				return task;
			}
		} catch (Exception e) {
			log.error("exception "+e);
		}
		 
		return null;
	}

	@Override
	public List<Task> getTaskByCompletionDate(LocalDate completionDate) {
		try {
			List<Task> task=taskRepository.findTaskByCompletionDate(completionDate);
			log.info("taskList "+task);
			if(!task.isEmpty()){
				return task;
			}
		} catch (Exception e) {
			log.error("exception "+e);
		}
		 
		return null;
	}


	

}
