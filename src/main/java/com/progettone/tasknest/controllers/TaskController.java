package com.progettone.tasknest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.task.TaskDtoRqsPosition;
import com.progettone.tasknest.model.dto.task.TaskDtoRqsPost;
import com.progettone.tasknest.model.dto.task.TaskDtoRspFull;
import com.progettone.tasknest.model.dtoservices.TaskConverter;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.TaskList;
import com.progettone.tasknest.model.repositories.TaskRepository;
import com.progettone.tasknest.model.repositories.TasklistRepository;

@RestController
public class TaskController {

    @Autowired
    TasklistRepository tlRepo;

    @Autowired
    TaskConverter tConv;

    @Autowired
    TaskRepository tRepo;

    @PostMapping("/tasks")
    public ResponseEntity<?> addTask(@RequestBody TaskDtoRqsPost dto) {

        Optional<TaskList> ot = tlRepo.findById(dto.getTasklist_id());
        if (!ot.isPresent())
            return new ResponseEntity<String>("tasklist non esistente", HttpStatus.BAD_REQUEST);

        TaskList tl = ot.get();
        Task t = tConv.dtoPostToTask(dto);

        if (tl == null || !t.isValid())
            return new ResponseEntity<String>("Invalid body request!", HttpStatus.BAD_REQUEST);

        tRepo.save(t);
        return new ResponseEntity<String>("New task succesfuly created!", HttpStatus.OK);
    }

    @PutMapping("/tasks")
    public ResponseEntity<?> moveTask(@RequestBody TaskDtoRqsPosition dto) {

        Task t = tRepo.findById(dto.getId()).get();
        TaskList tl = t.getTasklist();

        if (dto.getPosition() > tl.getTasks().size() + 1 || dto.getPosition() < 1)
            return new ResponseEntity<String>("Invalid body request!", HttpStatus.BAD_REQUEST);

        // trovo lista di task apparteneti alla stessa tasklist
        List<Task> tasks = tRepo.findAll().stream().filter(i -> i.getTasklist().equals(tl)).toList();

        // se la nuova posizione è maggiore di quella vecchia
        if (dto.getPosition() > t.getPosition()) {

            // tutti gli elementi con posizione compresa tra quella vecchia e quella nuova
            // vengono fatti indietreggiare di 1
            tasks.stream().filter(i -> i.getPosition() <= dto.getPosition() && i.getPosition() > t.getPosition())
                    .forEach(e -> {
                        e.setPosition(e.getPosition() - 1);
                        tRepo.save(e);
                    });
        }
        // se la nuova posizione è minore di quella vecchia
        if (dto.getPosition() < t.getPosition()) {

            // tutti gli elementi con posizione compresa tra quella nuova e quella vecchia
            // avanzano di 1
            tasks.stream().filter(i -> i.getPosition() >= dto.getPosition() && i.getPosition() < t.getPosition())
                    .forEach(e -> {
                        e.setPosition(e.getPosition() + 1);
                        tRepo.save(e);

                    });
        }
        t.setPosition(dto.getPosition());
        t.setTasklist(tlRepo.findByPositionAndBoardId(dto.getTaskLP(), dto.getBoardId()).get());
        tRepo.save(t);
        return new ResponseEntity<String>("New position setted!", HttpStatus.OK);

    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable Integer id) {

        Optional<Task> ot = tRepo.findById(id);
        if (!ot.isPresent())
            return new ResponseEntity<String>("task non esistente", HttpStatus.NOT_FOUND);

        return new ResponseEntity<TaskDtoRspFull>(tConv.TaskToDtoRspFull(ot.get()), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        if (tRepo.findById(id).isPresent()) {

            tRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<String>("Non esiste una task con id " + id, HttpStatus.BAD_REQUEST);
    }

}
