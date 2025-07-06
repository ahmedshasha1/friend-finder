package com.project.friend.service;

import com.project.friend.dto.CommentDto;
import com.project.friend.dto.ReactsDto;

import java.util.List;

public interface CommentService {
    void addComment(Long postId, CommentDto commentDto);

    void deleteComment(Long id);
    void addReactToComment(Long commentId, ReactsDto reactsDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long id);
    List<CommentDto> getAllComments();
}
