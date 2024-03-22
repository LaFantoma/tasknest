package com.progettone.tasknest.model.dto.board;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BordDtoRqsPost {
    private String title;
    private String description;
    private LocalDate date_of_creation;
    private boolean visible;
    private Integer img;
}
