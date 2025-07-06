package com.project.friend.service.mapper;

import com.project.friend.dto.ReactsDto;
import com.project.friend.model.Reacts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReactMapper {
    ReactMapper react= Mappers.getMapper(ReactMapper.class);

    ReactsDto toDto(Reacts reacts);
    Reacts toEntity(ReactsDto reactsDto);

    List<ReactsDto> toDtoList(List<Reacts> reacts);
    List<Reacts> toEntityList(List<ReactsDto> reactsDtos);
}
