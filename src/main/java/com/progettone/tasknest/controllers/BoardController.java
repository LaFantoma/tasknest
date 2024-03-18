package com.progettone.tasknest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.board.BoardDtoRspSimple;
import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.dtoservices.BoardConverter;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.UserToBoard;
import com.progettone.tasknest.model.repositories.BoardsRepository;
import com.progettone.tasknest.model.repositories.UserToBoardRepository;

@RestController
public class BoardController {

    @Autowired
    BoardsRepository bRepo;

    @Autowired
    BoardConverter bConv;

    @Autowired
    UserToBoardRepository utbRepo;

    @GetMapping("/boards/user/{id_u}")
    public ResponseEntity<?> getMyBoards(@PathVariable Integer id_u) {

        // prendo la lista delle board collegate all'utente
        List<UserToBoard> utb = utbRepo.findAll().stream().filter(i -> i.getMy_user().getId() == id_u).toList();
        List<Board> b = utb.stream().map(i -> i.getMy_board()).toList();

        if (b != null && !b.isEmpty()) {
            List<BoardDtoRspSimple> boards = b.stream().map(i -> bConv.BoardToDtoRspSimple(i)).toList();
            return new ResponseEntity<List<BoardDtoRspSimple>>(boards, HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Nessuna board presente", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Integer id) {

        Optional<Board> b = bRepo.findById(id);

        if (b.isPresent()) {
            Board board = b.get();
            return new ResponseEntity<BoardDtoRspTaskname>(bConv.BoardToDtoRspTaskname(board), HttpStatus.OK);
        } else
            return new ResponseEntity<String>("board inesistente", HttpStatus.NOT_FOUND);

    }

}
