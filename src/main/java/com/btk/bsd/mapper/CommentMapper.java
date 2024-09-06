package com.btk.bsd.mapper;

import com.btk.bsd.dto.CommentDTO;
import com.btk.bsd.model.Comment;
import com.btk.bsd.model.Task;
import com.btk.bsd.repository.CommentRepository;
import com.btk.bsd.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {


    private final ModelMapper modelMapper;

    public CommentMapper() {
        this.modelMapper = new ModelMapper();
    }

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        commentDTO.setTaskId(comment.getTask().getId());
        if (comment.getParentComment() != null) {
            commentDTO.setParentCommentId(comment.getParentComment().getId());
        }

        return commentDTO;
    }

    public Comment commentDTOToComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        Task task = taskRepository.findById(commentDTO.getTaskId())
                .orElse(null);
        comment.setTask(task);

        if (commentDTO.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentDTO.getParentCommentId())
                    .orElse(null);
            comment.setParentComment(parentComment);
        }
        return comment;
    }
}
