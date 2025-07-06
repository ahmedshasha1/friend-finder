package com.project.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.friend.model.Comments;
import com.project.friend.model.Reacts;
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
public class PostsDto extends BaseEntityDto {

    private String text;
    private String imgPath;
    private String vidPath;
    private List<String> imgPaths;
    private List<String> vidPaths;

    @JsonProperty(value = "reacts")
    private List<ReactsDto> reactsDtos;

    @JsonProperty(value = "comments")
    private List<CommentDto> commentDto;
}
