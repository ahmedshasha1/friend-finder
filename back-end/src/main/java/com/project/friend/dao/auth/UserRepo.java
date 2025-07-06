package com.project.friend.dao.auth;

import com.project.friend.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User getUsersByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.lastSeen >= :threshold")
    List<User> findOnlineUsers(@Param("threshold") LocalDateTime threshold);

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.lastSeen < :cutoff AND u.active = true")
    void  deactivateInactiveUsers(@Param("cutoff") LocalDateTime cutoff);



}
