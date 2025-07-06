package com.project.friend.service.mapper;

import com.project.friend.dto.auth.UserDto;
import com.project.friend.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper userMapper= Mappers.getMapper(UserMapper.class);

    UserDto toDto (User user);
    User toEntity(UserDto userDto);
}

