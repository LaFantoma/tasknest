package com.progettone.tasknest.model.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardDtoRqsPut extends BoardDtoRspSimple {

    private boolean visible;
    // private Set<Integer> my_users_id;
    // private Set<Integer> my_tasklists_id;

}
