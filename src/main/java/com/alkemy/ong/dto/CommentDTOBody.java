package com.alkemy.ong.dto;

import com.alkemy.ong.entity.Comment;

import lombok.Data;

@Data
public class CommentDTOBody {

    private String body;

    public CommentDTOBody comment2DTOBody(Comment comment){
        CommentDTOBody dto = new CommentDTOBody();
        dto.setBody(comment.getBody());
        return dto;
    }

}
