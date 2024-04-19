package com.task.todolist.serviceImp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.todolist.entity.Task;
import com.task.todolist.entity.priorityEnum;
import com.task.todolist.repository.TaskRepository;
import com.task.todolist.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImp implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> getAllTask() {

		try {
			List<Task> taskList = taskRepository.findAll();
			log.info("list of tasks:{} ", taskList);
			if (!taskList.isEmpty()) {
				log.info("taskList is not empty");
				return taskList.stream().sorted((task1, task2) -> task2.getPriority().compareTo(task1.getPriority()))
						.collect(Collectors.toList());

			}

		} catch (Exception e) {
			log.error("exception in getAllTask :{} ", e);
		}

		return null;

	}

	@Override
	public Task getTaskById(Integer id) {
		log.info("Id : {}", id);
		try {
			Optional<Task> task = taskRepository.findById(id);
			if (!task.isEmpty()) {
				log.info("task is not empty");
				return task.get();
			}
		} catch (Exception e) {
			log.error("exception in getTaskById : {}", e);
		}

		return null;
	}

	@Override
	public Task addTask(Task task) {
		log.info("task :{}", task);
		try {

			Optional<Task> existingTask = taskRepository.findTaskByTitleAndUserId(task.getTitle().toLowerCase(),
					task.getUserId());
			log.info("existingTask :{}", existingTask);
			if (existingTask.isEmpty()) {
				Task newTask = new Task();
				log.info("existingTask is not empty");
				newTask.setTitle(task.getTitle().toLowerCase());
				newTask.setUserId(task.getUserId());
				newTask.setDescription(task.getDescription());
				newTask.setCompletionDate(task.getCompletionDate());
				newTask.setPriority(task.getPriority());
				return taskRepository.save(newTask);
			}

		} catch (Exception e) {
			log.error("exception in addTask : {}", e);
		}
		log.info("task already exist");
		return null;
	}

	@Override
	public Task updateTask(Task newTask, Integer id) {
		try {

			Optional<Task> task = taskRepository.findById(id);
			log.info("update task :{}", task);

			if (task.isPresent()) {
				log.info(" task is not null :{}", task);
				if (newTask.getTitle() != null) {
					log.info(" title is not null :{}", newTask.getTitle());
					task.get().setTitle(newTask.getTitle().toLowerCase());
				}
				if (newTask.getDescription() != null) {
					log.info(" description is not null :{}", newTask.getDescription());
					task.get().setDescription(newTask.getDescription());
				}
				if (newTask.getCompletionDate() != null) {
					log.info(" completionDate is not null :{}", newTask.getCompletionDate());
					task.get().updatecompletionDateHistory(task.get().getCompletionDate());
					task.get().setCompletionDate(newTask.getCompletionDate());
				}
//				if (newTask.getUserId() != null) {
//					log.info(" userId is not null :{}", newTask.getUserId());
//					task.get().setUserId(newTask.getUserId());
//				}

				return taskRepository.save(task.get());

			}

		} catch (Exception e) {
			log.error("exception in update task :{} ", e);
		}
		log.info("task not found with id : {}", id);
		return null;
	}

	@Override
	public boolean deleteTaskById(Integer id) {

		try {
			log.info("id : {}", id);
			Optional<Task> task = taskRepository.findById(id);
			if (task.isPresent()) {
				log.info("task is not empty ; {}", task);
				taskRepository.deleteById(id);
				return true;
			}
		} catch (Exception e) {
			log.error("exception :{} ", e);
		}
		log.info("task not found with id : {}", id);
		return false;

	}

	@Override
	public List<Task> getAllRemainningTask() {
		try {
			LocalDate date = LocalDate.now();
			List<Task> taskList = taskRepository.getAllRemainningTask(date);
			log.info("taskList " + taskList);
			if (!taskList.isEmpty()) {
				log.info("taskList is not empty :{}", taskList);
				return taskList;
			}

		} catch (Exception e) {
			log.error("exception in updateTask :{} ", e);
		}
		log.info("tasks not found");
		return null;
	}

	@Override
	public List<Task> getTaskByCreationDate(LocalDate creationDate) {
		try {
			List<Task> task = taskRepository.findTaskByCreationDate(creationDate);
			log.info("taskList :{}", task);
			if (!task.isEmpty()) {
				return task;
			}
		} catch (Exception e) {
			log.error("exception in getTaskByCreationDate : {}", e);
		}
		log.info("task not found");
		return null;
	}

	@Override
	public List<Task> getTaskByCompletionDate(LocalDate completionDate) {
		try {
			List<Task> task = taskRepository.findTaskByCompletionDate(completionDate);
			log.info("taskList:{} ", task);
			if (!task.isEmpty()) {
				return task;
			}
		} catch (Exception e) {
			log.error("exception in getTaskByCompletionDate:{} ", e);
		}
		log.info("task not found");
		return null;
	}

	@Override
	public List<Task> getByUserId(Long userId) {
		try {
			List<Task> taskList = taskRepository.findByuserId(userId);
			if (!taskList.isEmpty()) {
				return taskList.stream().sorted((task1, task2) -> task2.getPriority().compareTo(task1.getPriority()))
						.collect(Collectors.toList());

			}
		} catch (Exception e) {
			log.error("exception " + e.toString());
		}
		return null;
	}

}
