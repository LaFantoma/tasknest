package com.progettone.tasknest.model.dtoservices;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.task.TaskDtoRspSimple;
import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.BoardsRepository;

@Service
public class TasklistConverter {

    @Autowired
    BoardsRepository bRepo;

    @Autowired
    TaskConverter tConv;

    public TasklistDtoRspName tasklistToDtoRspName(TaskList t) {

        return TasklistDtoRspName
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .position(t.getPosition())
                .my_tasks(t.getTasks().stream().map(i -> tConv.taskToDtoRspSimple(i))
                        .sorted(Comparator.comparing(TaskDtoRspSimple::getPosition))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();

    }

    public TaskList TasklistInstRqsToTasklist(TasklistInstRqs tl) {

        Board padre = bRepo.findById(tl.getIdBoard()).get();

        return TaskList
                .builder()
                .title(tl.getTitle())
                .position(padre.getMy_tasklists().size() + 1)
                .board(padre)
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
