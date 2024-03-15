package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.List;

public interface ListRepository extends JpaRepository<List, Integer>{

}
