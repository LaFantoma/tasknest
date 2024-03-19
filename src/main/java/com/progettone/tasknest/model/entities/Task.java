package com.progettone.tasknest.model.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDate expired_date;
    private String state; // ("TO DO", "IN PROGRESS","COMPLETED", "CANCELLED", "ON HOLD");

    private Integer position;

    @JsonIgnore
    @OneToMany(mappedBy = "my_task", fetch = FetchType.EAGER)
    private Set<UserToTask> assigned_to;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tasklist_id")
    private TaskList tasklist;

    public boolean isValid() {

        return title != null && !title.isBlank() &&
                description != null && !description.isBlank() &&
                expired_date != null && expired_date.isAfter(LocalDate.now());
    }
}
