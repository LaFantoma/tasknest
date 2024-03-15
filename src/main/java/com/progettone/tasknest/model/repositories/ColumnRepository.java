package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progettone.tasknest.model.entities.Column;

public interface ColumnRepository extends JpaRepository<Column, Integer> {

}
