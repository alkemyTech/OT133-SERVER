package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDTO toDTO(Comment comment) {
    return new CommentDTO(comment.getUser(), comment.getUserId(),
        comment.getBody(),comment.getNews(), comment.getNewsId(), comment.getTimestamps());
  }
}
