package com.project.friend.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.friend.dto.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto extends BaseEntityDto {

    private String role;
    private UserDto userDto;
}
