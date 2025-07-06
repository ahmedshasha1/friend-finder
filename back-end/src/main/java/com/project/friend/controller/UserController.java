package com.project.friend.controller;

import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserDetailsVM> getMyInfo() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return ResponseEntity.ok(userService.getMyInfo(email));
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody @Validated UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.created(URI.create("/user/update")).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDetailsVM>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/delete-user/user-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/online-users")
    public ResponseEntity<List<UserDetailsVM>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getOnlineUsers());
    }
}

