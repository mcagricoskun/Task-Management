package com.btk.bsd.service;

import com.btk.bsd.dto.CommentDTO;
import com.btk.bsd.mapper.CommentMapper;
import com.btk.bsd.model.Comment;
import com.btk.bsd.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    // GET
    @Cacheable(value = "comment", key = "#id")
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElse(null);
        return commentMapper.commentToCommentDTO(comment);
    }

    // GET ALL
    @Cacheable(value = "comments")
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    // CREATE
    @CachePut(value = "comment", key = "#result.id")
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.commentDTOToComment(commentDTO);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.commentToCommentDTO(savedComment);
    }

    // UPDATE
    @CachePut(value = "comment", key = "#id")
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + id + " not found"));

        existingComment.setComment(commentDTO.getText());

        if (commentDTO.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentDTO.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID: " + commentDTO.getParentCommentId()));
            existingComment.setParentComment(parentComment);
        }

        Comment updatedComment = commentRepository.save(existingComment);
        return commentMapper.commentToCommentDTO(updatedComment);
    }

    @CacheEvict(value = {"comment", "comments"}, key = "#id", allEntries = true)
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + id + " not found"));
        commentRepository.delete(comment);
    }
}
