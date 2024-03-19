package com.progettone.tasknest.model.dto.board;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardDtoRqsUsers {

    private Integer id;
    private Set<Integer> users_id;

}
