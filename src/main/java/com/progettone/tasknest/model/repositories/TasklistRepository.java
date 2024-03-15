package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.TaskList;

public interface TasklistRepository extends JpaRepository<TaskList, Integer> {

}
