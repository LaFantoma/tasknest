package com.progettone.tasknest.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.board.BoardDtoRqsPut;
import com.progettone.tasknest.model.dto.board.BoardDtoRqsUsers;
import com.progettone.tasknest.model.dto.board.BoardDtoRspSimple;
import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.dto.board.BordDtoRqsImg;
import com.progettone.tasknest.model.dto.board.BordDtoRqsPost;
import com.progettone.tasknest.model.dtoservices.BoardConverter;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.UserToBoard;
import com.progettone.tasknest.model.repositories.BoardsRepository;
import com.progettone.tasknest.model.repositories.UserRepository;
import com.progettone.tasknest.model.repositories.UserToBoardRepository;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class BoardController {

    @Autowired
    BoardsRepository bRepo;

    @Autowired
    UserRepository uRepo;

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

    @PostMapping("/boards")
    public ResponseEntity<?> postBoard(@RequestBody BordDtoRqsPost dto) {
        Board board = bConv.BordDtoRqsPostToBoard(dto);
        Set<UserToBoard> userSet = new HashSet<>();
        UserToBoard utb = UserToBoard
                .builder()
                .my_board(board)
                .my_user(uRepo.findById(dto.getUserId()).get())
                .build();
        userSet.add(utb);
        board.setMy_users(userSet);
        bRepo.save(board);
        utbRepo.save(utb);

        return new ResponseEntity<String>("board creata con successo", HttpStatus.OK);
    }

    @PutMapping("/boards")
    public ResponseEntity<?> modifyBoard(@RequestBody BoardDtoRqsPut dto) {

        Board board = bConv.dtoRqsPutToBoard(dto);

        if (board == null)
            return new ResponseEntity<String>("board inesistente", HttpStatus.NOT_FOUND);

        bRepo.save(board);
        return new ResponseEntity<BoardDtoRspTaskname>(bConv.BoardToDtoRspTaskname(board), HttpStatus.OK);
    }

    @PutMapping("/boards/users")
    public ResponseEntity<?> modifyUsers(@RequestBody BoardDtoRqsUsers dto) { // IL DELIRIO CHE STRANAMENTE FUNZIONA
                                                                              // OMG!!

        Optional<Board> b = bRepo.findById(dto.getId());

        if (!b.isPresent())
            return new ResponseEntity<String>("board inesistente", HttpStatus.NOT_FOUND);

        Board board = b.get();

        // lista di ID degli utenti modificata
        Set<Integer> newUsersList = dto.getUsers_id();

        // lista di ID dei vecchi utenti
        Set<Integer> oldUsersList = board.getMy_users().stream().map(i -> i.getMy_user().getId())
                .collect(Collectors.toSet());

        // se nella nuova lista sono presenti nuovi elementi creo nuovi elementi nella
        // tabella usersToBoard
        for (Integer i : newUsersList) {
            if (!oldUsersList.contains(i)) {
                UserToBoard utb = UserToBoard.builder()
                        .my_user(uRepo.findById(i).get())
                        .my_board(board)
                        .build();
                utbRepo.save(utb);
            }
        }

        // se nella nuova lista non ci sono degli alementi presenti nella vecchia vado a
        // eliminare i collegamenti userToBoard
        for (Integer i : oldUsersList)
            if (!newUsersList.contains(i))
                utbRepo.deleteById(i);

        // risetto la lista di UserToBoard collegata a questa board
        board.setMy_users(utbRepo.findAll().stream().filter(i -> i.getMy_board().getId() == board.getId())
                .collect(Collectors.toSet()));

        bRepo.save(board);

        return new ResponseEntity<BoardDtoRspTaskname>(bConv.BoardToDtoRspTaskname(board), HttpStatus.OK);

    }

    @PutMapping("/boards/img")
    public ResponseEntity<?> modifyUsersImg(@RequestBody BordDtoRqsImg dto) {
        Optional<Board> b = bRepo.findById(dto.getIdBoard());

        if (!b.isPresent())
            return new ResponseEntity<String>("board inesistente", HttpStatus.NOT_FOUND);

        Board board = b.get();

        board.setImg(dto.getImgId());
        bRepo.save(board);

        return new ResponseEntity<String>("Immagine modificata con successo", HttpStatus.OK);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Integer id) {
        if (bRepo.findById(id).isPresent()) {

            bRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Non esiste una board con id " + id, HttpStatus.BAD_REQUEST);
    }

}
