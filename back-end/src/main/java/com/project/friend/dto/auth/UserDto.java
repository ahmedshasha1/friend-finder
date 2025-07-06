package com.project.friend.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.friend.dto.BaseEntityDto;
import com.project.friend.dto.FriendDto;
import com.project.friend.model.Friend;
import com.project.friend.model.auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseEntityDto {

    private String firstName;
    private String lastName;
    @Min(value = 18,message = "invalid.age")
    private int age;
    @JsonIgnore
    private Date dateTime;
    @NotBlank(message = "error.email")
    private String email;
    @NotBlank(message = "error.password")
    private String password;
    @Pattern(regexp = "^(01)[0-9]{9}$", message = "invalid.phone")
    private String phoneNumber;

    private String photo;
    private String cover;

    @JsonIgnore
    private Boolean  active = false;
    private LocalDateTime lastSeen;

    @JsonIgnore
    private List<RoleDto> roles;
    private List<FriendDto> friends;
}
