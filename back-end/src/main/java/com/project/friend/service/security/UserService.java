package com.project.friend.service.security;

import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.model.auth.User;

import java.util.List;

public interface UserService {

    User getUserByemail(String email);

    User checkClientExistByToken(String token) throws RuntimeException;

    void updateUser(UserDto userDto);
    UserDetailsVM getMyInfo(String email);
    List<UserDetailsVM> getAllUser();
    void deleteUser(Long id);

    List<UserDetailsVM> getOnlineUsers();

    void updateUserOnlineStatus(User user);


}
