package com.alkemy.ong.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.entity.Comment;

@Component
public class CommentMapper {

	public Comment toComment(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setBody(commentDTO.getBody());
		comment.setNewsId(commentDTO.getNewId());
		comment.setUserId(commentDTO.getUserId());
		
		return comment;
	}
	
	public CommentDTO toCommentDTO(Comment comment) {
		return new CommentDTO(comment.getUserId(),comment.getBody(),comment.getNewsId());
	}
}
