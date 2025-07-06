package com.project.friend.service.mapper;

import com.project.friend.dto.CommentDto;
import com.project.friend.model.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface CommentMapper {
    CommentMapper commentMapper= Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comments comments);
    Comments toEntity(CommentDto commentDto);

    List<CommentDto> toDtoList(List<Comments> comments);
    List<Comments> toEntityList(List<CommentDto> commentDtos);
}
