package com.project.friend.service.security;

import com.project.friend.config.security.JwtTokenHandler;
import com.project.friend.dao.auth.RoleRepo;
import com.project.friend.dao.auth.UserRepo;
import com.project.friend.dto.auth.TokenDto;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.dto.auth.UserLoginDto;
import com.project.friend.model.auth.Role;
import com.project.friend.model.auth.User;
import com.project.friend.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public TokenDto login(UserLoginDto userLoginDto) {
        if(Objects.isNull(userLoginDto)){
            throw new RuntimeException("invalid.email");
        }
        User user=userService.getUserByemail(userLoginDto.getEmail());
        if (user == null){
            throw new RuntimeException("email.notfound");
        }

        List<String> roles = user.getRoles().stream().map(role -> role.getRole().substring(5)).collect(Collectors.toList());
        if(!passwordEncoder.matches(userLoginDto.getPassword(),user.getPassword())){
            throw new BadCredentialsException("error.pass");
        }
        userService.updateUserOnlineStatus(user);

        return new TokenDto(jwtTokenHandler.createToken(user), roles);
    }

    @Override
    public void signUp(UserDto userDto) {
        if(Objects.isNull(userDto)||Objects.nonNull(userDto.getId())){
            throw new RuntimeException("invalid.user");
        }

        User userExits = userRepo.getUsersByEmail(userDto.getEmail());
        if(userExits != null ){
            throw new RuntimeException("error.clientExist");
        }

        User user = UserMapper.userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role =  roleRepo.getByRole("ROLE_USER");
        if(role == null){
            throw new RuntimeException("role not exist");
        }
        List<Role> roles = List.of(role);
        user.setRoles(roles);
        user.setDateTime(new Date());
        userRepo.save(user);
    }
}
