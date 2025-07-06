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
public class Posts extends BaseEntity{
    @Column(nullable = false)
    private String text;
    @Column(name="img_path")
    private String imgPath;

    @Column(name="vid_path")
    private String vidPath;

    @ElementCollection
    private List<String> imgPaths;

    @ElementCollection
    private List<String> vidPaths;

    @OneToMany(mappedBy = "posts")
    private List<Reacts> reacts;

    @OneToMany(mappedBy = "posts")
    private List<Comments> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
