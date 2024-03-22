package com.progettone.tasknest.model.dto.comment;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentDtoRsp {

    private String body;
    private LocalDate made_at;
    private String author_name;

}
