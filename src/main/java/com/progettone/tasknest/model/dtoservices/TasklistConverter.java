package com.progettone.tasknest.model.dtoservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.BoardsRepository;

@Service
public class TasklistConverter {

    @Autowired
    BoardsRepository bRepo;

    public TasklistDtoRspName TasklistToDtoRspName(TaskList t) 
    {

        return TasklistDtoRspName
                .builder()
                .id(t.getId())
                .title(t.getTitle())
                .position(t.getPosition())
                .build();

    }

    public TaskList TasklistInstRqsToTasklist(TasklistInstRqs tl)
    {
        return TaskList
                .builder()
                .title(tl.getTitle())
                .position(bRepo.findById(tl.getIdBoard()).get().getMy_tasklists().size())
                .board(bRepo.findById(tl.getIdBoard()).get())
                .build();
    }

}
