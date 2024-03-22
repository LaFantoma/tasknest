package com.progettone.tasknest.model.dtoservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progettone.tasknest.model.dto.comment.CommentDtoRsp;
import com.progettone.tasknest.model.entities.Comment;
import com.progettone.tasknest.model.repositories.CommentRepository;

@Service
public class CommentConverter {

    @Autowired
    CommentRepository cRepo;

    public CommentDtoRsp boardToDto(Comment c) {

        return CommentDtoRsp
                .builder()
                .body(c.getBody())
                .made_at(c.getMade_at())
                .author_name(c.getAuthor().getName())
                .build();
    }
}
