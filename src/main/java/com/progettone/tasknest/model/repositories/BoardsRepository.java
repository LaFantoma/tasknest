package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.Board;

public interface BoardsRepository extends JpaRepository<Board, Integer> {

}
