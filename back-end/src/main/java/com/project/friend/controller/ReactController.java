package com.project.friend.controller;

import com.project.friend.dto.ReactCountDto;
import com.project.friend.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reacts")
public class ReactController {

    @Autowired
    private ReactService reactService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<String> reactToPost(@PathVariable Long postId, @RequestParam boolean isLike) {
        reactService.addReactToPost(postId, isLike);
        return ResponseEntity.created(URI.create("/reactes/add-react")).build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> unReactToPost(@PathVariable Long postId) {
        reactService.unReactToPost(postId);
        return ResponseEntity.created(URI.create("/reactes/unReact")).build();
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<ReactCountDto> getPostReactCount(@PathVariable Long postId) {
        ReactCountDto count = reactService.getPostReactCount(postId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> reactToComment(@PathVariable Long commentId, @RequestParam boolean isLike) {
        reactService.addReactToComment(commentId, isLike);
        return ResponseEntity.created(URI.create("/reactes/add-react")).build();
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> unReactToComment(@PathVariable Long commentId) {
        reactService.unReactToComment(commentId);
        return ResponseEntity.created(URI.create("/reactes/unReact")).build();
    }

    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<ReactCountDto> getCommentReactCount(@PathVariable Long commentId) {
        ReactCountDto count = reactService.getCommentReactCount(commentId);
        return ResponseEntity.ok(count);
    }
}