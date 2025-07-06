package com.project.friend.service.impl;

import com.project.friend.dao.CommentRepo;
import com.project.friend.dao.PostsRepo;
import com.project.friend.dao.ReactsRepo;
import com.project.friend.dto.ReactCountDto;
import com.project.friend.model.Comments;
import com.project.friend.model.Posts;
import com.project.friend.model.Reacts;
import com.project.friend.model.auth.User;
import com.project.friend.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReactServiceImpl implements ReactService {

    @Autowired
    private ReactsRepo reactsRepo;
    @Autowired
    private PostsRepo postsRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Override
    public void addReactToPost(Long postId, boolean isLike) {
        if(Objects.isNull(postId)){
            throw new RuntimeException("invalid.id");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Posts post = postsRepo.findById(postId).orElseThrow(() -> new RuntimeException("post.not.found"));

        Optional<Reacts> existingReact = reactsRepo.findByUserAndPost(currentUser, post);

        if (existingReact.isPresent()) {
            Reacts react = existingReact.get();
            react.setLike(isLike);
            reactsRepo.save(react);
        } else {
            Reacts newReact = new Reacts();
            newReact.setLike(isLike);
            newReact.setUser(currentUser);
            newReact.setPosts(post);
            reactsRepo.save(newReact);
        }
    }

    @Override
    public void unReactToPost(Long postId) {
        if(Objects.isNull(postId)){
            throw new RuntimeException("invalid.id");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Posts post = postsRepo.findById(postId).orElseThrow(() -> new RuntimeException("post.not.found"));

        Reacts react = reactsRepo.findByUserAndPost(currentUser, post)
                .orElseThrow(() -> new RuntimeException("react.not.found"));

        reactsRepo.delete(react);
    }

    @Override
    public void addReactToComment(Long commentId, boolean isLike) {
        if(Objects.isNull(commentId)){
            throw new RuntimeException("invalid.id");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Comments comments = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("comment.not.found"));

        Optional<Reacts> existingReact = reactsRepo.findByUserAndComment(currentUser, comments);

        if (existingReact.isPresent()) {
            Reacts react = existingReact.get();
            react.setLike(isLike);
            reactsRepo.save(react);
        } else {
            Reacts newReact = new Reacts();
            newReact.setLike(isLike);
            newReact.setUser(currentUser);
            newReact.setComment(comments);
            reactsRepo.save(newReact);
        }
    }

    @Override
    public ReactCountDto getPostReactCount(Long postId) {
        if(Objects.isNull(postId)){
            throw new RuntimeException("invalid.id");
        }
        long likes = reactsRepo.countByPostIdAndIsLike(postId, true);
        long disLikes = reactsRepo.countByPostIdAndIsLike(postId, false);
        return new ReactCountDto(likes, disLikes);
    }

    @Override
    public ReactCountDto getCommentReactCount(Long commentId) {
        if(Objects.isNull(commentId)){
            throw new RuntimeException("invalid.id");
        }
        long likes = reactsRepo.countByCommentIdAndIsLike(commentId, true);
        long disLikes = reactsRepo.countByCommentIdAndIsLike(commentId, false);
        return new ReactCountDto(likes, disLikes);
    }

    @Override
    public void unReactToComment(Long commentId) {
        if(Objects.isNull(commentId)){
            throw new RuntimeException("invalid.id");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Comments comments = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("comment.not.found"));

        Reacts react = reactsRepo.findByUserAndComment(currentUser, comments)
                .orElseThrow(() -> new RuntimeException("comment.not.found"));

        reactsRepo.delete(react);
        //reactsRepo.deleteByCommentId(commentId);
    }
}
