package com.project.friend.dao;

import com.project.friend.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepo extends JpaRepository<Posts,Long> {

    Page<Posts> findByTextContainingIgnoreCase(String text, Pageable pageable);
    Page<Posts> getPostsById(Long id, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.user.id IN (:friendIds)  ORDER BY p.updatedDate DESC")
    List<Posts> findAllPostsByFriendIds(@Param("friendIds") List<Long> friendIds);

    @Query("SELECT p FROM Posts p WHERE p.user.id = :userId ORDER BY p.updatedDate DESC")
    List<Posts> getPostsByUserId(@Param("userId") Long userId);

    @Query("SELECT p.imgPath FROM Posts p WHERE p.user.id = :userId ORDER BY p.updatedDate DESC")
    List<String> getAllImagesByUserId(@Param("userId") Long userId);


    @Query("SELECT p.vidPath FROM Posts p WHERE p.user.id = :userId ORDER BY p.updatedDate DESC")
    List<String> getAllVideosByUserId(@Param("userId") Long userId);






}
