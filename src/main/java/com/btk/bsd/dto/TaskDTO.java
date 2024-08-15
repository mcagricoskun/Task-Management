package com.btk.bsd.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String importance;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Long pojectId;
    private List<Long> commentsId;
}
