package com.alkemy.ong.dto;

import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {
    
    private User user;
    
    private String userId;
    
    private String body;
    
    private News news;
    
    private String newsId;
    
    LocalDateTime timestamps;

    
}
