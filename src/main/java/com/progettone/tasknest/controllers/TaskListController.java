package com.progettone.tasknest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.dto.tasklist.TasklistDtoRspName;
import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.dto.tasklist.TasklistMoveRqs;
import com.progettone.tasknest.model.dto.tasklist.TasklistPutRqs;
import com.progettone.tasknest.model.dtoservices.TasklistConverter;
import com.progettone.tasknest.model.entities.Board;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.TasklistRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TaskListController {

    @Autowired
    TasklistRepository tlRepo;

    @Autowired
    TasklistConverter tlConv;

    @PostMapping("/list")
    public ResponseEntity<?> newList(@RequestBody TasklistInstRqs request) {
        Integer id = request.getIdBoard();
        String title = request.getTitle();

        if (id != null && !title.isBlank()) {
            TaskList tl = tlConv.TasklistInstRqsToTasklist(request);
            tlRepo.save(tl);
            return ResponseEntity.status(HttpStatus.OK).body("New list succesfuly created!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid body request!");
        }
    }

    @PutMapping("/list/title")
    public ResponseEntity<?> putList(@RequestBody TasklistPutRqs request) {
        TaskList tl = tlRepo.findById(request.getIdList()).get();
        if (request.getIdList() != null && !request.getTitle().isBlank()) {
            tl.setTitle(request.getTitle());
            tlRepo.save(tl);
            return ResponseEntity.status(HttpStatus.OK).body("List hase been changed!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid body request!");
        }
    }

    @PutMapping("/list/position")
    public ResponseEntity<?> moveList(@RequestBody TasklistMoveRqs request) {
        TaskList tl = tlRepo.findById(request.getIdList()).get();
        Board board = tl.getBoard();

        if (request.getNewPosition() > board.getMy_tasklists().size() + 1 || request.getNewPosition() < 1)
            return new ResponseEntity<String>("Invalid body request!", HttpStatus.BAD_REQUEST);

        List<TaskList> tLIsts = tlRepo.findAll().stream().filter(i -> i.getBoard().equals(board)).toList();

        if (request.getNewPosition() > tl.getPosition()) {

            tLIsts.stream()
                    .filter(i -> i.getPosition() <= request.getNewPosition() && i.getPosition() > tl.getPosition())
                    .forEach(e -> {
                        e.setPosition(e.getPosition() - 1);
                        tlRepo.save(e);
                    });
        }

        if (request.getNewPosition() < tl.getPosition()) {

            tLIsts.stream()
                    .filter(i -> i.getPosition() >= request.getNewPosition() && i.getPosition() < tl.getPosition())
                    .forEach(e -> {
                        e.setPosition(e.getPosition() + 1);
                        tlRepo.save(e);

                    });
        }
        tl.setPosition(request.getNewPosition());
        tlRepo.save(tl);
        return new ResponseEntity<String>("New position setted!", HttpStatus.OK);
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deleteList(@PathVariable Integer id) {
        if (tlRepo.findById(id).isPresent()) {

            tlRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Non esiste una list con id " + id, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("lists/{id}")
    public ResponseEntity<?> getList(@RequestParam Integer id) {

        Optional<TaskList> ot = tlRepo.findById(id);

        if (ot.isPresent()) {
            TaskList t = ot.get();
            return new ResponseEntity<TasklistDtoRspName>(tlConv.tasklistToDtoRspName(t), HttpStatus.OK);
        } else
            return new ResponseEntity<String>("lista inesistente", HttpStatus.NOT_FOUND);
    }

}
