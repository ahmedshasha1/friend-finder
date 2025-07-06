package com.project.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.friend.model.Posts;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class ReactsDto extends BaseEntityDto{

    private boolean isLike;
    private Long postId;
    private CommentDto comment;
}
