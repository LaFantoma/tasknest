package com.progettone.tasknest.model.dto.tasklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TasklistMoveRqs {
    private Integer idList;
    private Integer newPosition;
}
