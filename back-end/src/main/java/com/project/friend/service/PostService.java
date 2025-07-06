package com.project.friend.service;

import com.project.friend.controller.vm.PostResponseVM;
import com.project.friend.dto.PostsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostResponseVM getAllPosts(Integer pageNo, Integer pageSize);
    PostResponseVM search(String text,Integer pageNo, Integer pageSize );

    void createPost(PostsDto postsDto);
    PostResponseVM getById(Long id,Integer pageNo, Integer pageSize);

    List<PostsDto> getMyPosts();

    List<String> getMyImages();
    List<String> getMyVideos();

    String uploadImage(MultipartFile image) throws IOException;
    List<String> uploadListImage(List<MultipartFile> images) throws IOException;
    String uploadVideo(MultipartFile video) throws IOException;
    List<String> uploadListVideos(List<MultipartFile> videos) throws IOException;


}
