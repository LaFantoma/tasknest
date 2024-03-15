package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

}
