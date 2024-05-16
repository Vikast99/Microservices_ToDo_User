package com.task.todolist.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.todolist.entity.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	public Task findByTitle(@Param("title") String title);
	

    @Query(value="select t from Task t where t.completionDate >= :presentDate",nativeQuery = true)
    List<Task> getAllRemainningTask(@Param("presentDate") LocalDateTime presentDate);
    
    public List<Task> findTaskByCreationDate(LocalDateTime creationDate) ;
    
    public List<Task> findTaskByCompletionDate(LocalDateTime completionDate) ;

    public List<Task> findByuserId(Long userId);
    
    public Optional<Task> findTaskByTitleAndUserId(String title, Long id);
    
    public List<Task> findByCompletionDate(LocalDateTime completeionTime);
    
 
    
    
    
//    public Page<Task> findTasksByName(String title,org.springframework.data.domain.Pageable pageable);
    
}
