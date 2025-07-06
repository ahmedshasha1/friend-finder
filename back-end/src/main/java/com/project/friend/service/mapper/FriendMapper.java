package com.project.friend.service.mapper;

import com.project.friend.dto.FriendDto;
import com.project.friend.dto.PostsDto;
import com.project.friend.model.Friend;
import com.project.friend.model.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FriendMapper {
    FriendMapper friendMapper= Mappers.getMapper(FriendMapper.class);

    FriendDto toDto(Friend friend);
    Friend toEntity(FriendDto friend);

    List<FriendDto> toDtoList(List<Friend> friend);
    List<Friend> toEntityList(List<FriendDto> friend);

}
