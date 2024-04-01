package com.julioCord.todosimple.repositories;

import com.julioCord.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long id);

//    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
//    List<Task> findByUserId(@Param("id") Long id);

}
