package com.project.friend.service.security;

import com.project.friend.config.security.JwtTokenHandler;
import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dao.auth.UserRepo;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.model.auth.User;
import com.project.friend.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @Override
    public User getUserByemail(String email) {
        if(Objects.isNull(email)){
            throw new RuntimeException("invalid.email");
        }
        User user = userRepo.getUsersByEmail(email);
        if (user == null){
            throw new RuntimeException("email.notfound");
        }
        return user;
    }

    @Override
    public User checkClientExistByToken(String token) throws RuntimeException {
        String email = jwtTokenHandler.getSubject(token);
        if(Objects.isNull(email)){
            throw new RuntimeException("email.notfound");
        }
        User user = userRepo.getUsersByEmail(email);
        if (user == null ){
            throw new RuntimeException("email.notfound");
        }
        return user;
    }

    @Override
    public void updateUser(UserDto userDto) {
        if(Objects.isNull(userDto)||Objects.isNull(userDto.getId())){
            throw new RuntimeException("invalid.id");
        }

        if(!userRepo.existsById(userDto.getId())){
            throw new RuntimeException("email.notfound");
        }
        userRepo.save(UserMapper.userMapper.toEntity(userDto));
    }

    @Override
    public UserDetailsVM getMyInfo(String email) {
        if(Objects.isNull(email)){
            throw new RuntimeException("invalid.email");
        }

        User userExits = userRepo.getUsersByEmail(email);
        if(userExits == null ){
            throw new RuntimeException("email.notfound");
        }
        UserDetailsVM userDetailsVM = new UserDetailsVM();
        userDetailsVM.setFirstName(userExits.getFirstName());
        userDetailsVM.setLastName(userExits.getLastName());
        userDetailsVM.setAge(userExits.getAge());
        userDetailsVM.setPhoneNumber(userExits.getPhoneNumber());
        userDetailsVM.setEmail(userExits.getEmail());
        userDetailsVM.setPassword(userExits.getPassword());
        userDetailsVM.setFriend(userExits.getFriends().size());

        return userDetailsVM;
    }

    @Override
    public List<UserDetailsVM> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserDetailsVM> userDetailsVMS = new ArrayList<>();
        for (int i = 0 ; i<= users.size();i++){
            UserDetailsVM userDetailsVM = new UserDetailsVM();
            userDetailsVM.setFirstName(users.get(i).getFirstName());
            userDetailsVM.setLastName(users.get(i).getLastName());
            userDetailsVM.setAge(users.get(i).getAge());
            userDetailsVM.setPhoneNumber(users.get(i).getPhoneNumber());
            userDetailsVM.setEmail(users.get(i).getEmail());
            userDetailsVM.setFriend(users.get(i).getFriends().size());

            userDetailsVMS.add(userDetailsVM);
        }
        return userDetailsVMS;
    }

    @Override
    public void deleteUser(Long id) {
        if(Objects.isNull(id) || !userRepo.existsById(id)){
            throw new RuntimeException("invalid.id");
        }
       userRepo.deleteById(id);
    }

    @Override
    public List<UserDetailsVM> getOnlineUsers() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<User> users = userRepo.findOnlineUsers(fiveMinutesAgo);
        List<UserDetailsVM> userDetailsVMS = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserDetailsVM userDetailsVM = new UserDetailsVM();
            userDetailsVM.setFirstName(users.get(i).getFirstName());
            userDetailsVM.setLastName(users.get(i).getLastName());
            userDetailsVM.setPhoto(users.get(i).getPhoto());
            userDetailsVM.setEmail(users.get(i).getEmail());
            userDetailsVM.setLastSeen(users.get(i).getLastSeen());

            userDetailsVMS.add(userDetailsVM);
        }

        return userDetailsVMS;
    }

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void checkInactiveUsers() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        userRepo.deactivateInactiveUsers(cutoff);
    }

    @Transactional
    public void updateUserOnlineStatus(User user) {
        user.setActive(true);
        user.setLastSeen(LocalDateTime.now());
        userRepo.save(user);
    }
}
