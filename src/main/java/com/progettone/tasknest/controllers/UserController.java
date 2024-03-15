package com.progettone.tasknest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.progettone.tasknest.dto.relog.RegisterRqs;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.UserRepository;
import static com.progettone.tasknest.utils.Utils.*;

public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    UserConverter conv;

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRqs dto) 
    {
        User q = conv.RegisterToUser(dto);
            if (!isValidPassword(q.getPassword())) {
                return ResponseEntity.badRequest().body(
                        "La password deve essere lunga almeno 8 caratteri e contenere almeno un carattere speciale (@, #, $, %, &, , !).");
            }

            if (!isValidEmail(q.getMail())) {
                return ResponseEntity.badRequest().body("La email non è valida");
            }

            return ResponseEntity.ok(repo.save(q));
    }

}
