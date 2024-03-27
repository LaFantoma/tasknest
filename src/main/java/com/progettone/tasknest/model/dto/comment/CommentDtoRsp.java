package com.progettone.tasknest.model.dto.comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentDtoRsp {

    private Integer id;
    private String body;
    private LocalDateTime made_at;
    private String author_name;
    private Integer author_id;

}
