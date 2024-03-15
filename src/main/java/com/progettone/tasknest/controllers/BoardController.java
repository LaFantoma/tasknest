package com.progettone.tasknest.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.entities.UserToBoard;
import com.progettone.tasknest.model.repositories.BoardsRepository;
import com.progettone.tasknest.model.repositories.UserToBoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BoardController {

    @Autowired
    BoardsRepository bRepo;

    @Autowired
    UserToBoardRepository utbRepo;

    // @GetMapping("boards/{id_u}")
    // public ResponseEntity<?> getMyBoards(@PathVariable Integer id_u) {

    // List<UserToBoard> utb =
    // utbRepo.findAll().stream().filter((i)->i.getMy_user().getId() == id_u)
    // }

}
