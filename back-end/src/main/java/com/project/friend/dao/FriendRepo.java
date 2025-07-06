package com.project.friend.dao;

import com.project.friend.model.Friend;
import com.project.friend.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepo extends JpaRepository<Friend,Long> {
    Optional<Friend> findByUserAndFriend(User user, User friend);
    List<Friend> findAllByUser(User user);
    long countByUser(User user);
}
