package com.project.friend.service.security;

import com.project.friend.dto.auth.TokenDto;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.dto.auth.UserLoginDto;
import com.project.friend.model.auth.User;

public interface AuthService {

    TokenDto login(UserLoginDto userLoginDto);
    void signUp(UserDto user);
}
