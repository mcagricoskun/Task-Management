package com.btk.bsd.mapper;

import com.btk.bsd.dto.TaskDTO;
import com.btk.bsd.model.Comment;
import com.btk.bsd.model.Project;
import com.btk.bsd.model.Task;
import com.btk.bsd.repository.ProjectRepository;
import com.btk.bsd.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommentRepository commentRepository;


    public TaskMapper() {
        this.modelMapper = new ModelMapper();
    }
    public TaskDTO taskToTaskDTO(Task task) {
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);

        taskDTO.setProjectId(task.getProject().getId());

        taskDTO.setCommentIds(
                task.getComments().stream()
                        .map(Comment::getId)
                        .collect(Collectors.toList())
        );

        return taskDTO;
    }

    public Task taskDTOToTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);

        Project project = projectRepository.findById(taskDTO.getProjectId()).orElse(null);

        task.setProject(project);

        task.setComments(
                taskDTO.getCommentIds().stream()
                        .map(id -> commentRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID: " + id)))
                        .collect(Collectors.toList())
        );

        return task;
    }
}
