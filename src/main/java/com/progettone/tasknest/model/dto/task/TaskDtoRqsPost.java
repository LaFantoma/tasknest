package com.progettone.tasknest.model.dto.task;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskDtoRqsPost {

    private Integer tasklist_id;
    private String title;
    private String description;
    private LocalDate expired_date;

}
