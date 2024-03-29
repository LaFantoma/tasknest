package com.progettone.tasknest.model.dto.tasklist;

import java.util.Set;

import com.progettone.tasknest.model.dto.task.TaskDtoRspSimple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TasklistDtoRspName {

    private Integer id;
    private String title;
    private Integer position;
    private Set<TaskDtoRspSimple> my_tasks;

}
