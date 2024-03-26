package com.progettone.tasknest.model.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BordDtoRqsPost {
    private Integer userId;
    private String title;
    private String description;
    private boolean visible;
    private Integer img;
}
