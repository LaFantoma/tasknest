package com.progettone.tasknest.model.dto.task;

import java.time.LocalDate;
import java.util.Set;

import com.progettone.tasknest.model.entities.Comment;
import com.progettone.tasknest.model.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskDtoRspFull {

    private Integer id;
    private String title;
    private String description;
    private LocalDate expired_date;
    private String state;
    private Integer position;
    private Set<Comment> comments;
    private Set<User> assigned_to;
}
