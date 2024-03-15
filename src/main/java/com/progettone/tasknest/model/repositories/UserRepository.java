package com.progettone.tasknest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.progettone.tasknest.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>  
{
    User findByEmail(String email);
}
