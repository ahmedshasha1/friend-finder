package com.project.friend.controller;

import com.project.friend.dto.CommentDto;
import com.project.friend.dto.ReactsDto;
import com.project.friend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post-id/{postId}")
    public ResponseEntity<String> addComment(@PathVariable @Validated Long postId, @RequestBody @Validated CommentDto commentDto) {
        commentService.addComment(postId, commentDto);
        return ResponseEntity.created(URI.create("/comment/add-comment")).build();
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment-id/{commentId}/react")
    public ResponseEntity<Void> reactToComment(@PathVariable Long commentId, @RequestBody @Validated ReactsDto reactsDto) {
        commentService.addReactToComment(commentId, reactsDto);
        return ResponseEntity.created(URI.create("/react/addToComment")).build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/comment-id/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }
}