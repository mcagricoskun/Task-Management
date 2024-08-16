package com.btk.bsd.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Long taskId;
    private Long parentCommentId;
    private List<Long> childCommentIds;
}
