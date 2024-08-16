package com.btk.bsd.service;

import com.btk.bsd.dto.TaskDTO;
import com.btk.bsd.mapper.TaskMapper;

import com.btk.bsd.model.Project;
import com.btk.bsd.model.Task;
import com.btk.bsd.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    // GET
    @Cacheable(value = "user", key = "#id")
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            return taskMapper.taskToTaskDTO(task);
        }
        return null;
    }

    // GET ALL
    @Cacheable(value = "tasks")
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toList());
    }

    // CREATE
    @CachePut(value = "task", key = "#result.id")
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        //Saçma gibi direk taskDTO u gönder taskToTaskDTO ne gerek var
        Task savedTask = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(savedTask);
    }

    // UPDATE
    @CachePut(value = "task", key = "#id")
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setStatus(taskDTO.getStatus());
            existingTask.setImportance(taskDTO.getImportance());
            existingTask.setDueDate(taskDTO.getDueDate());

            existingTask.setProject(new Project(taskDTO.getProjectId(), null,null,null));  // Project'i ID ile güncelleme

//            existingTask.setComments(taskDTO.getCommentIds().stream()
//                    .map(commentId -> new Comment(commentId, null, null, null))
//                    .collect(Collectors.toList()));  // Comment'leri ID'ler ile güncelleme

            Task updatedTask = taskRepository.save(existingTask);
            return taskMapper.taskToTaskDTO(updatedTask);
        }

        return null;  // Veya uygun bir hata durumu
    }



    // DELETE
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
        taskRepository.delete(task);
    }
}
