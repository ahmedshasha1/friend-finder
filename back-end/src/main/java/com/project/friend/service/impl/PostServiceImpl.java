package com.project.friend.service.impl;

import com.project.friend.controller.vm.PostResponseVM;
import com.project.friend.dao.PostsRepo;
import com.project.friend.dto.PostsDto;
import com.project.friend.model.Posts;
import com.project.friend.model.auth.User;
import com.project.friend.service.PostService;
import com.project.friend.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostsRepo postsRepo;
    @Override
    public PostResponseVM getAllPosts(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Posts> posts = postsRepo.findAll(pageable);
        return new PostResponseVM(
                PostMapper.postMapper.toDtoList(posts.getContent()),
                posts.getTotalElements()
        );
    }

    @Override
    public PostResponseVM search(String text, Integer pageNo, Integer pageSize) {
        if(Objects.isNull(text)){
            throw new RuntimeException("invalid.text");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Posts> posts = postsRepo.findByTextContainingIgnoreCase(text,pageable);
        if(posts.isEmpty()){
            throw new RuntimeException("invalid.text");
        }

        return new PostResponseVM(
                PostMapper.postMapper.toDtoList(posts.getContent()),
                posts.getTotalElements()
        );
    }

    @Override
    public void createPost(PostsDto postsDto) {
        if(Objects.nonNull(postsDto.getId())){
            throw new RuntimeException("invalid.id");
        }
        if(postsDto==null){
            throw new RuntimeException("Enter correct data");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Posts post = PostMapper.postMapper.toEntity(postsDto);
        post.setUser(user);

        postsRepo.save(post);
    }

    @Override
    public PostResponseVM getById(Long id, Integer pageNo, Integer pageSize) {
        if(Objects.isNull(id)){
            throw new RuntimeException("invalid.id");
        }
        Pageable page = PageRequest.of(pageNo,pageSize);
        Page<Posts> posts = postsRepo.getPostsById(id,page);
        return new PostResponseVM(
                PostMapper.postMapper.toDtoList(posts.getContent()),
                posts.getTotalElements()
        );
    }

    @Override
    public List<PostsDto> getMyPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Posts> posts = postsRepo.getPostsByUserId(user.getId());

        return PostMapper.postMapper.toDtoList(posts);
    }

    @Override
    public List<String> getMyImages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return postsRepo.getAllImagesByUserId(user.getId());
    }

    @Override
    public List<String> getMyVideos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return postsRepo.getAllVideosByUserId(user.getId());
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
            String uploadDir = "uploads/images/";
            String contentType = image.getContentType();
            if (!contentType.startsWith("image/")) {
                throw new RuntimeException("invalid.file.type");
            }
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());

            return path.toString();


    }


    @Override
    public String uploadVideo(MultipartFile video) throws IOException {
        String uploadDir = "uploads/videos/";
        String contentType = video.getContentType();
        if (!contentType.startsWith("video/")) {
            throw new RuntimeException("invalid.file.type");
        }

        String fileName = UUID.randomUUID()+"_"+video.getOriginalFilename();
        Path path = Paths.get(uploadDir,fileName);

        Files.createDirectories(path.getParent());
        Files.write(path , video.getBytes());

        return path.toString();
    }
    @Override
    public List<String> uploadListImage(List<MultipartFile> images) throws IOException {
        String uploadDir = "uploads/images/";
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile image : images) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("invalid.file.type");
            }

            String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(image.getOriginalFilename());
            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());

            fileNames.add(path.toString().replace("\\", "/"));
        }

        return fileNames;
    }

    @Override
    public List<String> uploadListVideos(List<MultipartFile> videos) throws IOException {
        String uploadDir = "uploads/videos/";
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile video : videos) {
            String contentType = video.getContentType();
            if (contentType == null || !contentType.startsWith("video/")) {
                throw new RuntimeException("invalid.file.type");
            }

            String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(video.getOriginalFilename());
            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, video.getBytes());

            fileNames.add(path.toString().replace("\\", "/"));
        }

        return fileNames;
    }

}
