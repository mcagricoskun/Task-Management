package com.btk.bsd.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String importance;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Long projectId;
    private List<Long> commentIds;
}
