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
public class Comments extends BaseEntity{
    @Column(nullable = false,name = "comments")
    private String comment;

    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Posts posts;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reacts> reacts;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}
