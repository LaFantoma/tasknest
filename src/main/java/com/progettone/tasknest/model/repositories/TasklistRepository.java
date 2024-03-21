package com.progettone.tasknest.model.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.progettone.tasknest.model.entities.TaskList;

public interface TasklistRepository extends JpaRepository<TaskList, Integer> {
    @Procedure(procedureName = "refresh")
    void refresh();
    
    Set<TaskList> findByBoardId(int boardId);
    Optional<TaskList> findByPositionAndBoardId(int position, int boardId);
}

