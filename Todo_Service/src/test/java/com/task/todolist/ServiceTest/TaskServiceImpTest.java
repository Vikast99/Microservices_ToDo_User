package com.task.todolist.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

import org.junit.jupiter.api.BeforeEach;

import com.task.todolist.RepositoryTest.Task;
import com.task.todolist.repository.TaskRepository;
import com.task.todolist.service.TaskService;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImpTest {
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskService taskService;
	
	@Test
	 void getAllTasks() {
		
		//given
		Task task=new Task(1,"learning python","today we learn some python concepts");
		Task task2=new Task(1,"learning java","today we learn some java concepts");
		
		//when
		 given(taskRepository.findAll())
	      .willReturn(List.of(task));
	     
	      
	      var  taskList = taskService.getAllTask();
	      
	       //Then

	      assertThat(taskList).isNotNull();
	         assertThat(taskList.size()).isEqualTo(2);

		
	}

}
