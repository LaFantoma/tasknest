package com.progettone.tasknest.model.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BordDtoRqsImg {
    
    private Integer idBoard;
    private Integer imgId;
}
