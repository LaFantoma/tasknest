package com.progettone.tasknest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettone.tasknest.model.dto.relog.LoginRqs;
import com.progettone.tasknest.model.dto.relog.RegisterRqs;
import com.progettone.tasknest.model.dto.user.UserDtoRspId;
import com.progettone.tasknest.model.dtoservices.UserConverter;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.UserRepository;
import static com.progettone.tasknest.utils.Utils.*;

@RestController
public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    UserConverter conv;

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRqs dto) {
        User q = conv.RegisterToUser(dto);
        if (!isValidPassword(q.getPassword())) {
            return ResponseEntity.badRequest().body(
                    "La password deve essere lunga almeno 8 caratteri e contenere almeno un carattere speciale (@, #, $, %, &, , !).");
        }

        if (!isValidEmail(q.getEmail())) {
            return ResponseEntity.badRequest().body("La email non è valida");
        }
        repo.save(q);
        return ResponseEntity.ok().body("Registrazione avvenuta con successo");
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRqs request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = repo.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            UserDtoRspId userDto = conv.UserToUserDtoRspId(user);

            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
