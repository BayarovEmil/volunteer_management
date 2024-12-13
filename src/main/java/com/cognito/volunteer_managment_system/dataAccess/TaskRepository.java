package com.cognito.volunteer_managment_system.dataAccess;

import com.cognito.volunteer_managment_system.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query("""
            select task
            from Task task
            where task.isDone=:done
            """)
    Page<Task> findAllByDone(boolean done,Pageable pageable);
    Page<Task> findAllById(Integer id,Pageable pageable);
}
