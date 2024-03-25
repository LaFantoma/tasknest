package com.progettone.tasknest.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.comment.CommentDtoRqs;
import com.progettone.tasknest.model.dto.comment.CommentDtoRqsMod;
import com.progettone.tasknest.model.dtoservices.CommentConverter;
import com.progettone.tasknest.model.entities.Comment;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.CommentRepository;
import com.progettone.tasknest.model.repositories.TaskRepository;
import com.progettone.tasknest.model.repositories.UserRepository;

@RestController
public class CommentController {

    @Autowired
    CommentRepository cRepo;

    @Autowired
    CommentConverter cConv;

    @Autowired
    UserRepository uRepo;

    @Autowired
    TaskRepository tRepo;

    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentDtoRqs dto) {

        Optional<Task> ot = tRepo.findById(dto.getTask_id());
        Optional<User> ou = uRepo.findById(dto.getAuthor_id());

        if (!ot.isPresent())
            return new ResponseEntity<String>("task non esistente", HttpStatus.BAD_REQUEST);

        if (!ou.isPresent())
            return new ResponseEntity<String>("user non esistente", HttpStatus.BAD_REQUEST);

        cRepo.save(cConv.dtoToComment(dto));
        return new ResponseEntity<String>("New comment succesfuly created!", HttpStatus.OK);

    }

    @PutMapping("/comments")
    public ResponseEntity<?> modifyComment(@RequestBody CommentDtoRqsMod dto) {

        Comment c = cRepo.findById(dto.getId()).get();
        User author = uRepo.findById(dto.getAuthor_id()).get();

        if (!c.getAuthor().equals(author))
            return new ResponseEntity<String>("illegal request", HttpStatus.BAD_REQUEST);

        c.setBody(dto.getBody());
        return new ResponseEntity<String>("comment succesfuly modified!", HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {

        if (!cRepo.findById(id).isPresent())
            return new ResponseEntity<String>("invalid request", HttpStatus.BAD_REQUEST);

        cRepo.deleteById(id);
        return new ResponseEntity<String>("coso cancellato", HttpStatus.OK);

    }

}
