package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.exception.CategoryException;
import com.alkemy.ong.exception.CommentException;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.NewService;
import com.alkemy.ong.service.UserService;

import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired

  private CommentRepository repository;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private NewService newService;

  @Autowired
  private UserService userService;

  @Autowired
  private NewsRepository newsRepository;

  @Override
  public Optional<CommentDTO> create(CommentDTO commentDTO) {
    if (this.newService.existsById(commentDTO.getNewId()) && this.userService.userExists(commentDTO.getUserId())) {
      Comment comment = this.commentRepository.save(this.commentMapper.toComment(commentDTO));
      return Optional.of(this.commentMapper.toCommentDTO(comment));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public CommentDTO findById(String id) {
    Comment comment = commentRepository.findById(id).get();
    return commentMapper.toCommentDTO(comment);
  }

  @Override
  public void delete(String id) {
    Comment comment = commentRepository.findById(id).get();
    commentRepository.delete(comment);
  }

  @Override
  public List<CommentDTO> getAllComments(String id) throws CommentException {
      Optional<News> existNews=newsRepository.findById(id);
      List<CommentDTO> commentDTOList = new ArrayList<>();
      if (existNews.isPresent() ) {
        List<Comment> commentList = commentRepository.findAll();
        for (Comment comment : commentList) {
          //filtrar los comentarios que tienen el id de noticia
          if (comment.getNewsId().equals(existNews.get().getId())) {
            //añadimos el comentario dto a la lista  devolver
            commentDTOList.add(commentMapper.toCommentDTO(comment));
          }
        }
      }else{
        System.out.println("error");
        throw new CommentException("The id does not exist in the database.");
      }
      return commentDTOList;
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, String id){
        Comment comment = commentRepository.getById(id);
        comment.setBody(commentDTO.getBody());
        commentRepository.save(comment);
        CommentDTO response = commentMapper.toCommentDTO(comment);
        return response;
    }

    @Override
    public Integer validateUser(String commentId){

        if(commentRepository.existsById(commentId)){

            SecurityContext securityContext = SecurityContextHolder.getContext();

            List<Rol> roles = userService.getByEmail(securityContext.getAuthentication().getName()).get().getRoles();
            for(Rol rol : roles){
                if (rol.getName().equals(Roles.ROL_ADMIN))
                    return HttpStatus.SC_OK;
            }

            String userId = userService.getByEmail(securityContext.getAuthentication().getName() /* EMAIL */).get().getId();
            Comment comment = commentRepository.getById(commentId);
            if(comment.getUserId().equals(userId)) {
                return HttpStatus.SC_OK;
            } 

            return HttpStatus.SC_FORBIDDEN; //403

        }

        return HttpStatus.SC_NOT_FOUND; //404
    }
  
    @Override
    public List<CommentBodyDTO> bringCommentsBodies(){
        List<Comment> comments = repository.findAll();
        List<CommentBodyDTO> commentsBodies = new ArrayList<>();
        for (Comment c : comments){
            commentsBodies.add(commentMapper.commentToBody(c));
        }
        return commentsBodies;
    }


}
