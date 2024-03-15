package com.progettone.tasknest.model.dtoservices;

import java.util.List;

import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.board.BoardDtoRspSimple;
import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.User;

@Service
public class BoardConverter {

    public BoardDtoRspSimple BoardToDtoRspSimple(Board b) {

        return BoardDtoRspSimple
                .builder()
                .id(b.getId())
                .title(b.getTitle())
                .description(b.getDescription())
                .build();
    }

    public BoardDtoRspTaskname BoardToDtoRspTaskname(Board b) {

        return BoardDtoRspTaskname
                .builder()
                .id(b.getId())
                .title(b.getTitle())
                .description(b.getDescription())
                .date_of_creation(b.getDate_of_creation())
                .visible(b.isVisible())
                .my_users(findUsers(b))
                .my_tasklists(b.getMy_tasklists())
                .build();
    }

    public List<User> findUsers(Board b) {

        return b.getMy_users().stream().map(i -> i.getMy_user()).toList();
    }

}
