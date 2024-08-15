package com.btk.bsd.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String master;
    private List<Long> taskIds;
}
