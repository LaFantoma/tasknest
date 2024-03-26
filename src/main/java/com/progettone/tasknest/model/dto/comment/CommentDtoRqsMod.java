package com.progettone.tasknest.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentDtoRqsMod {

    private Integer id;
    private String body;
    private Integer author_id;

}
