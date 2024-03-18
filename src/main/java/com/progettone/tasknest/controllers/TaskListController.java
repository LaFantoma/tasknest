package com.progettone.tasknest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.dto.tasklist.TasklistMoveRqs;
import com.progettone.tasknest.model.dtoservices.TasklistConverter;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.TasklistRepository;

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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("New list succesfuly created!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid body request!");
        }
    }

    @PutMapping("/list")
    public ResponseEntity<?> moveList(@RequestBody TasklistMoveRqs request) {
        TaskList tl = tlRepo.findById(request.getIdList()).get();
        if (request.getIdList() != null && request.getNewPosition() != null
                && request.getNewPosition() < tlRepo.findAll().size()) {
            if (request.getNewPosition() > tl.getPosition()) {
                tlRepo.findAll().stream().filter(t -> t.getPosition() <= request.getNewPosition()).forEach(f -> {
                    if (f.getPosition() != 0) {
                        f.setPosition(f.getPosition() - 1);
                        tlRepo.save(f);
                    }
                });
            } else {
                tlRepo.findAll().stream().filter(t -> t.getPosition() >= request.getNewPosition()).forEach(f -> {
                    if (f.getPosition() != tlRepo.findAll().size() - 1) {
                        f.setPosition(f.getPosition() + 1);
                        tlRepo.save(f);
                    }
                });
            }

            tl.setPosition(request.getNewPosition());
            tlRepo.save(tl);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("New position setted!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid body request!");
        }

    }
}
