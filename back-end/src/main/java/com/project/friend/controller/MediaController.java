package com.project.friend.controller;

import com.project.friend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private PostService postService;

    @PostMapping("/upload-image")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String uploaded = postService.uploadImage(file);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("upload.failed");
        }
    }

    @PostMapping("/upload-video")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    ResponseEntity<String> uploadVideo(@RequestParam("video") MultipartFile video)  {
        try {
            String uploaded = postService.uploadVideo(video);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("upload.failed");
        }
    }

    @PostMapping("/upload/images")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("images") List<MultipartFile> images) {
        try {
            List<String> uploaded = postService.uploadListImage(images);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(List.of("upload.failed"));
        }
    }

    @PostMapping("/upload/videos")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<String>> uploadVideos(@RequestParam("videos") List<MultipartFile> videos) {
        try {
            List<String> uploaded = postService.uploadListVideos(videos);
            return ResponseEntity.ok(uploaded);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(List.of("upload.failed"));
        }
    }
}
