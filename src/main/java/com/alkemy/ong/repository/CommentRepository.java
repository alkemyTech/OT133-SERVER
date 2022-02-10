package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, String> {
    
    @Query("SELECT x.body FROM Comment x")
    public List<Comment> findAllBody();
}
