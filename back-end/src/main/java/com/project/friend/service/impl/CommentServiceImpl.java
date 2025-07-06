package com.project.friend.service.impl;

import com.project.friend.dao.CommentRepo;
import com.project.friend.dao.PostsRepo;
import com.project.friend.dao.ReactsRepo;
import com.project.friend.dto.CommentDto;
import com.project.friend.dto.ReactsDto;
import com.project.friend.model.Comments;
import com.project.friend.model.Posts;
import com.project.friend.model.Reacts;
import com.project.friend.model.auth.User;
import com.project.friend.service.CommentService;
import com.project.friend.service.mapper.CommentMapper;
import com.project.friend.service.mapper.ReactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostsRepo postsRepo;

    @Override
    public void addComment(Long postId, CommentDto commentDto) {
        Posts post = postsRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("post.not.found"));
        Comments comment = CommentMapper.commentMapper.toEntity(commentDto);
        comment.setPosts(post);
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("invalid.id");
        }

        Comments comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("comment.not.found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        boolean isCommentOwner = comment.getUser().getId().equals(currentUser.getId());
        boolean isPostOwner = comment.getPosts().getUser().getId().equals(currentUser.getId());

        if (!isCommentOwner && !isPostOwner) {
            throw new RuntimeException("unauthorized.comment.remove");
        }

        commentRepo.delete(comment);
    }

    @Override
    public void addReactToComment(Long commentId, ReactsDto reactsDto) {
        Comments comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("comment.not.found"));
        Reacts react = ReactMapper.react.toEntity(reactsDto);
        react.setComment(comment);
        comment.getReacts().add(react);
        commentRepo.save(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        return CommentMapper.commentMapper.toDtoList(commentRepo.findByPostsId(postId));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comments comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("comment.not.found"));
        return CommentMapper.commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        return CommentMapper.commentMapper.toDtoList(commentRepo.findAll());
    }
}
