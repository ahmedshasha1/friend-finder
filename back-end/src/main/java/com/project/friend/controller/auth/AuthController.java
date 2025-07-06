package com.project.friend.controller.auth;

import com.project.friend.config.security.AuthFilter;
import com.project.friend.dto.auth.TokenDto;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.dto.auth.UserLoginDto;
import com.project.friend.service.security.AuthService;
import com.project.friend.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    ResponseEntity<TokenDto> login(@RequestBody @Validated UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @PostMapping("/sign-up")
    ResponseEntity<Void> signUp(@RequestBody @Validated UserDto userDto){
        authService.signUp(userDto);
        return ResponseEntity.created(URI.create("/auth/create-user")).build();
    }
}
