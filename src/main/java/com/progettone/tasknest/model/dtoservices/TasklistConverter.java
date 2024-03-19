package com.progettone.tasknest.model.dtoservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.BoardsRepository;

@Service
public class TasklistConverter {

    @Autowired
    BoardsRepository bRepo;

    public TasklistDtoRspName tasklistToDtoRspName(TaskList t) {

        return TasklistDtoRspName
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .position(t.getPosition())
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
