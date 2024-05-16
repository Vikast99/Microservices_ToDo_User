package com.task.todolist.serviceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.task.todolist.entity.Task;
import com.task.todolist.repository.TaskRepository;
import com.task.todolist.service.TaskService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImp implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private EntityManager entityManager;
	
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
			if(task.getTitle()!=null && task.getUserId()!=null) {
			Optional<Task> existingTask = taskRepository.findTaskByTitleAndUserId(task.getTitle().toLowerCase(),
					task.getUserId());
			log.info("existingTask :{}", existingTask);
			if (existingTask.isEmpty()) {
				log.info("existingTask is empty we can add new task");
				Task newTask = new Task();
				
				newTask.setTitle(task.getTitle().toLowerCase());
				newTask.setUserId(task.getUserId());
				newTask.setDescription(task.getDescription());
				newTask.setCompletionDate(task.getCompletionDate());
				newTask.setPriority(task.getPriority());
				newTask.setStatus(task.getStatus());
				newTask.setRating(task.getRating());
                newTask.setTodoType(task.getTodoType());
                newTask.setTags(task.getTags());
				return taskRepository.save(newTask);
			}
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
			if(id!=null && newTask!=null) {
			Optional<Task> task = taskRepository.findById(id);
			log.info("update task :{}", task);

			if (task.isPresent()) {
				log.info(" task is not null :{}", task);

				if (newTask.getDescription() != null) {
					log.info(" description is not null :{}", newTask.getDescription());
					task.get().setDescription(newTask.getDescription());
				}
				if (newTask.getCompletionDate() != null) {
					log.info(" completionDate is not null :{}", newTask.getCompletionDate());					task.get().getCompletionDateHistory().add(task.get().getCompletionDate());
					task.get().setCompletionDate(newTask.getCompletionDate());
				}


				return taskRepository.save(task.get());

			}
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
			LocalDateTime date = LocalDateTime.now();
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
	public List<Task> getTaskByCreationDate(LocalDateTime creationDate) {
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
	public List<Task> getTaskByCompletionDate(LocalDateTime completionDate) {
		try {

			List<Task> task = taskRepository.findTaskByCompletionDate(completionDate);
			log.info("taskList:{} ", task);
			if (!task.isEmpty()) {
				log.info("taskList:{} ", task);
					return task;
			} 
		} catch (Exception e) {
			log.error("exception in getTaskByCompletionDate:{} ", e);
		}
		log.info("task not found");
		return null;
	}
	
	
	
//	@Scheduled(fixedRate = 60*1000)
//	public void sendNotificationsForTasksLeftWithOneHour() {
//		List<Task> tasks=taskRepository.findByCompletionDate(LocalDateTime.now().plusHours(1).withSecond(0).withNano(0));
//		System.out.println("task list "+tasks);
//		for(Task task:tasks) {
////			System.out.println("for block");
//			if(isTaskLeftWithOneHour(task)) {
////				System.out.println("if block");
//				System.out.println(LocalDateTime.now());
//				sendNotification(task);
//			}
//		}
//	}
//	
//	private void sendNotification(Task task) {
//		
//		System.out.println("Notification sent for the task"+task.getTitle()+" "+task.getCompletionDate());	
//	}
//
//	private boolean isTaskLeftWithOneHour(Task task) {
////		System.out.println("one hour method");
//		LocalDateTime currentDateTime= LocalDateTime.now();
//		LocalDateTime taskDueDateTime= task.getCompletionDate().minusHours(1).withSecond(0).withNano(0);
//		
//		LocalDateTime oneHourBeforeTaskDueDateAndTime=taskDueDateTime;
//		System.out.println(" current "+currentDateTime);
//		System.out.println("completion "+taskDueDateTime);
//		System.out.println("oneHourBefore "+oneHourBeforeTaskDueDateAndTime);
//		boolean b=currentDateTime.isAfter(oneHourBeforeTaskDueDateAndTime) && taskDueDateTime.withSecond(0).withNano(0).isBefore(taskDueDateTime.plusHours(1));
//		return (b);
//	}

	

	@Override
	public Task findByTitle(String title) {
		return taskRepository.findByTitle(title);
		
		
	}

	@Override
	public List<Task> getTasksByName(String title) {
		
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> criteriaQuery=criteriaBuilder.createQuery(Task.class);
		Root<Task> root=criteriaQuery.from(Task.class);
		
		Predicate titlePredicate=criteriaBuilder.equal(root.get("title"), title);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

//	@Override
//	public Page<Task> findTasksByName(String title,org.springframework.data.domain.Pageable pageable) {
//	
//		return taskRepository.findTasksByName(title,pageable);
//	}

	
	



}
