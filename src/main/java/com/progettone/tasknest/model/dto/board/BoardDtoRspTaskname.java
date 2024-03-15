package com.progettone.tasknest.model.dto.board;

import java.util.List;
import java.util.Set;

import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardDtoRspTaskname extends BoardDtoBase {

    private Integer id;

    private List<User> my_users;
    private Set<TaskList> my_tasklists;

    private String task_name;

}
