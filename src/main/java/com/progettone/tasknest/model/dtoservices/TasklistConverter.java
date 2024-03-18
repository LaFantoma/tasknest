package com.progettone.tasknest.model.dtoservices;

import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.entities.TaskList;

@Service
public class TasklistConverter {

    public TasklistDtoRspName tasklistToDtoRspName(TaskList t) {

        return TasklistDtoRspName
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .position(t.getPosition())
                .build();

    }

    public TaskList dtoRspNameToTaskList(TasklistDtoRspName dto) {

        return TaskList
                .builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .position(dto.getPosition())
                .build();
    }

}
