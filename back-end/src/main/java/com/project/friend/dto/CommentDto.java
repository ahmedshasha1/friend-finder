package com.project.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.friend.model.Posts;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CommentDto extends BaseEntityDto {

    private String comment;
    private String imgPath;
    private Long postId;
    private List<ReactsDto> reacts;
}
