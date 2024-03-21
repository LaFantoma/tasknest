package com.progettone.tasknest.model.dtoservices;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.board.BoardDtoRqsPut;
import com.progettone.tasknest.model.dto.board.BoardDtoRspSimple;
import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.entities.Board;

import com.progettone.tasknest.model.entities.User;

import com.progettone.tasknest.model.repositories.BoardsRepository;
import com.progettone.tasknest.model.repositories.TasklistRepository;
import com.progettone.tasknest.model.repositories.UserToBoardRepository;

@Service
public class BoardConverter {

    @Autowired
    TasklistConverter tlConv;

    @Autowired
    BoardsRepository bRepo;

    @Autowired
    UserToBoardRepository utbRepo;

    @Autowired
    TasklistRepository tlRepo;

    public BoardDtoRspSimple BoardToDtoRspSimple(Board b) {

        return BoardDtoRspSimple
                .builder()
                .id(b.getId())
                .title(b.getTitle())
                .description(b.getDescription())
                .img(b.getImg())
                .build();
    }

    public BoardDtoRspTaskname BoardToDtoRspTaskname(Board b) {

        return BoardDtoRspTaskname
                .builder()
                .id(b.getId())
                .title(b.getTitle())
                .description(b.getDescription())
                .img(b.getImg())
                .date_of_creation(b.getDate_of_creation())
                .visible(b.isVisible())
                .my_users(findUsers(b))
                .my_tasklists(
                        b.getMy_tasklists().stream().map(i -> tlConv.tasklistToDtoRspName(i))
                                .sorted(Comparator.comparing(TasklistDtoRspName::getPosition))
                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }

    public List<User> findUsers(Board b) {

        return b.getMy_users().stream().map(i -> i.getMy_user()).toList();
    }

    public Board dtoRqsPutToBoard(BoardDtoRqsPut dto) {

        Board board = bRepo.findById(dto.getId()).get();

        board.setTitle(dto.getTitle());
        board.setDescription(dto.getDescription());
        board.setVisible(dto.isVisible());
        return board;

    }

}
