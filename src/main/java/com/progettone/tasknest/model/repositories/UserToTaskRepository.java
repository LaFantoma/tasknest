package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.UserToTask;

public interface UserToTaskRepository extends JpaRepository<UserToTask, Integer> {

}
