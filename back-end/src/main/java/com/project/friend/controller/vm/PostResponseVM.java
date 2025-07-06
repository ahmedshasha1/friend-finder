package com.project.friend.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.friend.dto.PostsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PostResponseVM {
    private List<PostsDto> posts;
    private Long totalPageSize;
}
