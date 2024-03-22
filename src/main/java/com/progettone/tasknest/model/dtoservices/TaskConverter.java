package com.progettone.tasknest.model.dtoservices;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.task.TaskDtoRqsPost;
import com.progettone.tasknest.model.dto.task.TaskDtoRspFull;
import com.progettone.tasknest.model.dto.task.TaskDtoRspSimple;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.TaskRepository;
import com.progettone.tasknest.model.repositories.TasklistRepository;

@Service
public class TaskConverter {

    @Autowired
    TaskRepository tRepo;

    @Autowired
    TasklistRepository tlRepo;

    @Autowired
    CommentConverter cConv;

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

    public TaskDtoRspFull TaskToDtoRspFull(Task t) {

        return TaskDtoRspFull
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .expired_date(t.getExpired_date())
                .state(t.getState())
                .position(t.getPosition())
                .assigned_to(findUsers(t))
                .comments(t.getComments().stream().map(i -> cConv.boardToDto(i)).collect(Collectors.toSet()))
                .build();
    }

    public Set<User> findUsers(Task t) {

        return t.getAssigned_to().stream().map(i -> i.getMy_user()).collect(Collectors.toSet());
    }

    public TaskDtoRspSimple taskToDtoRspSimple(Task t) {

        return TaskDtoRspSimple
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .position(t.getPosition())
                .build();
    }

}
