package com.project.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.friend.model.Posts;
import com.project.friend.model.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendDto  extends BaseEntityDto{

    private String photo;
    private String cover;
    private User user;
    private List<PostsDto> postsDtos;
}
