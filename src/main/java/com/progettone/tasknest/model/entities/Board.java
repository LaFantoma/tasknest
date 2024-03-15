package com.progettone.tasknest.model.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private LocalDate date_of_creation;
    private boolean visible;

    @JsonIgnore
    @OneToMany(mappedBy = "my_board", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<UserToBoard> my_users;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<TaskList> my_tasklists;
}
