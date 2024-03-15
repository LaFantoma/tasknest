package com.progettone.tasknest.model.entities;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;
    private LocalDate date_of_regist;

    @JsonIgnore
    @OneToMany(mappedBy = "my_user", fetch = FetchType.EAGER) // cascade da errore
    private Set<UserToBoard> my_boards;

    @JsonIgnore
    @OneToMany(mappedBy = "my_user", fetch = FetchType.EAGER)
    private Set<UserToTask> my_tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Comment> my_comments;
}
