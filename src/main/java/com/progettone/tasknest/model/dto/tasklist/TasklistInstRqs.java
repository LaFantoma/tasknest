package com.progettone.tasknest.model.dto.tasklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TasklistInstRqs {
    private Integer idBoard;
    private String title;
}
