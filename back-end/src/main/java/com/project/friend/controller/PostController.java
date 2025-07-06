package com.project.friend.controller;


import com.project.friend.controller.vm.PostResponseVM;
import com.project.friend.dto.PostsDto;
import com.project.friend.service.FriendService;
import com.project.friend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/home")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FriendService friendService;

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    ResponseEntity<PostResponseVM> getAllPosts(@PathVariable int pageNo, @PathVariable int pageSize){
        return ResponseEntity.ok(postService.getAllPosts(pageNo-1,pageSize));
    }

    @PostMapping("/new-post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ResponseEntity<String> createPost(@RequestBody @Validated PostsDto postsDto){
        postService.createPost(postsDto);
        return ResponseEntity.created(URI.create("/post/newPost")).build();
    }

    @GetMapping("/search/{text}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ResponseEntity<PostResponseVM> search(@PathVariable @Validated String text,@PathVariable int pageNo, @PathVariable int pageSize){
        return ResponseEntity.ok(postService.search(text,pageNo-1,pageSize));
    }

    @GetMapping("/post-id/{id}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ResponseEntity<PostResponseVM> getById(@PathVariable @Validated Long id,@PathVariable int pageNo, @PathVariable int pageSize){
        return ResponseEntity.ok(postService.getById(id,pageNo-1,pageSize));
    }
    @GetMapping("/timeline")
    ResponseEntity<List<PostsDto>> getTimeline() {
        return ResponseEntity.ok(friendService.getTimelineForCurrentUser());
    }
    @GetMapping("/profile")
    ResponseEntity<List<PostsDto>> getMyPosts(){
        return ResponseEntity.ok(postService.getMyPosts());
    }

    @GetMapping("/my-images")
     ResponseEntity<List<String>> getMyImages() {
        return ResponseEntity.ok(postService.getMyImages());
    }

    @GetMapping("/my-videos")
     ResponseEntity<List<String>> getMyVideos() {
        return ResponseEntity.ok(postService.getMyVideos());
    }

}
