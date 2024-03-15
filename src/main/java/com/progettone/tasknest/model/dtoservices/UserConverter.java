package com.progettone.tasknest.model.dtoservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.UserDtoRspId;
import com.progettone.tasknest.model.dto.relog.RegisterRqs;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.UserRepository;

@Service
public class UserConverter {

    @Autowired
    UserRepository uRepo;

    public User RegisterToUser(RegisterRqs dto) {

        return User
                .builder()
                .name(dto.getName())
                .email(dto.getMail())
                .password(dto.getPassword())
                .build();
    }

    public UserDtoRspId UserToUserDtoRspId(User user) {
        return UserDtoRspId.
                builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

    }

}
