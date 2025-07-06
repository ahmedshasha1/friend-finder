package com.project.friend.dao;

import com.project.friend.model.Comments;
import com.project.friend.model.Posts;
import com.project.friend.model.Reacts;
import com.project.friend.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ReactsRepo extends JpaRepository<Reacts,Long> {
    Optional<Reacts> findByPostsId(Long postId);


    @Query("delete from Reacts where posts.id = :postId")
    void deleteByPosts(@Param("postId") Long postId);

    @Query("delete from Reacts where comment.id = :commentId")
    void deleteByComment(@Param("commentId") Long commentId);

    @Query("SELECT r FROM Reacts r WHERE r.user = :user AND r.comment = :comment")
    Optional<Reacts> findByUserAndComment(User user, Comments comment);

    @Query("SELECT COUNT(r) FROM Reacts r WHERE r.posts.id = :postId AND r.isLike = :isLike")
    long countByPostIdAndIsLike(Long postId, boolean isLike);

//    long countByCommentIdAndIsLike(Long commentId, boolean isLike);

    @Query("SELECT r FROM Reacts r WHERE r.user = :user AND r.posts = :post")
    Optional<Reacts> findByUserAndPost(@Param("user") User user, @Param("post") Posts post);

    @Query("SELECT COUNT(r) FROM Reacts r WHERE r.comment.id = :commentId AND r.isLike = :isLike")
    long countByCommentIdAndIsLike(@Param("commentId") Long commentId, @Param("isLike") boolean isLike);


}
