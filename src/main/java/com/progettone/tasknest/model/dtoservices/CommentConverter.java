package com.progettone.tasknest.model.dtoservices;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.comment.CommentDtoRqs;
import com.progettone.tasknest.model.dto.comment.CommentDtoRsp;
import com.progettone.tasknest.model.entities.Comment;
import com.progettone.tasknest.model.entities.Task;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.CommentRepository;
import com.progettone.tasknest.model.repositories.TaskRepository;
import com.progettone.tasknest.model.repositories.UserRepository;

@Service
public class CommentConverter {

    @Autowired
    CommentRepository cRepo;

    @Autowired
    UserRepository uRepo;

    @Autowired
    TaskRepository tRepo;

    public CommentDtoRsp boardToDto(Comment c) {

        return CommentDtoRsp
                .builder()
                .body(c.getBody())
                .made_at(c.getMade_at())
                .author_name(c.getAuthor().getName())
                .build();
    }

    public Comment dtoToComment(CommentDtoRqs dto) {

        User author = uRepo.findById(dto.getAuthor_id()).get();

        Task task = tRepo.findById(dto.getTask_id()).get();

        return Comment
                .builder()
                .body(dto.getBody())
                .task(task)
                .author(author)
                .made_at(LocalDate.now())
                .build();
    }
}
