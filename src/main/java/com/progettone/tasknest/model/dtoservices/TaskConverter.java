package com.progettone.tasknest.model.dtoservices;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.task.TaskDtoRqsPost;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.TaskRepository;
import com.progettone.tasknest.model.repositories.TasklistRepository;

@Service
public class TaskConverter {

    @Autowired
    TaskRepository tRepo;

    @Autowired
    TasklistRepository tlRepo;

    public Task dtoPostToTask(TaskDtoRqsPost dto) {

        TaskList padre = tlRepo.findById(dto.getTasklist_id()).get();

        return Task
                .builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .expired_date(dto.getExpired_date())
                .state("pending")
                .position(padre.getTasks().size() + 1)
                .tasklist(padre)
                .build();
    }

}
