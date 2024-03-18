package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.progettone.tasknest.model.entities.TaskList;

public interface TasklistRepository extends JpaRepository<TaskList, Integer> {
    @Procedure(procedureName = "refresh")
    void refresh();
}
