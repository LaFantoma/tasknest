package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.UserToBoard;

public interface UserToBoardRepository extends JpaRepository<UserToBoard, Integer> {

}
