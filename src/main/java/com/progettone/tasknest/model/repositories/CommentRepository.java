package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
