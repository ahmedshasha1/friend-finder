package com.project.friend.service.mapper;

import com.project.friend.dto.PostsDto;
import com.project.friend.model.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {
    PostMapper postMapper= Mappers.getMapper(PostMapper.class);

    PostsDto toDto(Posts posts);
    Posts toEntity(PostsDto postsDto);

    List<PostsDto> toDtoList(List<Posts> posts);
    List<Posts> toEntityList(List<PostsDto> posts);

}
