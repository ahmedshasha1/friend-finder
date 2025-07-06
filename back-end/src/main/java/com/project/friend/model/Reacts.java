package com.project.friend.model;

import com.project.friend.model.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REACTS")
public class Reacts extends BaseEntity{
    private boolean isLike;

    @ManyToOne()
    @JoinColumn(name = "POST_ID")
    private Posts posts;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comments comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}
