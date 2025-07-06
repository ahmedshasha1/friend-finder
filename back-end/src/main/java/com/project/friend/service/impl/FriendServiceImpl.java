package com.project.friend.service.impl;

import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dao.FriendRepo;
import com.project.friend.dao.PostsRepo;
import com.project.friend.dao.auth.UserRepo;
import com.project.friend.dto.PostsDto;
import com.project.friend.model.Friend;
import com.project.friend.model.Posts;
import com.project.friend.model.auth.User;
import com.project.friend.service.FriendService;
import com.project.friend.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendRepo friendRepo;
    @Autowired
    private PostsRepo postsRepo;



    @Override
    public void follow(Long friendId) {
        if (Objects.isNull(friendId)) {
            throw new RuntimeException("invalid.id");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User friend = userRepo.findById(friendId)
                .orElseThrow(() -> new RuntimeException("user.notfound"));

        if (user.getId().equals(friend.getId())) {
            throw new RuntimeException("cannot.follow.self");
        }

        if (friendRepo.findByUserAndFriend(user, friend).isPresent()) {
            throw new RuntimeException("found.follow");
        }

        Friend newFollow = new Friend();
        newFollow.setUser(user);
        newFollow.setFriend(friend);
        friendRepo.save(newFollow);
    }

    @Override
    public void unFollow(Long friendId) {
        if (Objects.isNull(friendId)) {
            throw new RuntimeException("invalid.id");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User friend = userRepo.findById(friendId)
                .orElseThrow(() -> new RuntimeException("user.notfound"));

        Optional<Friend> followRelation = friendRepo.findByUserAndFriend(user, friend);
        if (followRelation.isEmpty()) {
            throw new RuntimeException("notfound.follow");
        }

        friendRepo.delete(followRelation.get());
    }

    @Override
    public List<UserDetailsVM> getMyFollowings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Friend> myFollowings = friendRepo.findAllByUser(user);
        List<UserDetailsVM> userDetailsVMS = new ArrayList<>();
        UserDetailsVM userDetailsVM = new UserDetailsVM();
        for (int i = 0; i < myFollowings.size(); i++) {

            userDetailsVM.setFirstName(myFollowings.get(i).getUser().getFirstName());
            userDetailsVM.setLastName(myFollowings.get(i).getUser().getLastName());
            userDetailsVM.setEmail(myFollowings.get(i).getUser().getEmail());
            userDetailsVM.setFriend(myFollowings.get(i).getUser().getFriends().size());

            userDetailsVMS.add(userDetailsVM);
        }

        return userDetailsVMS ;
    }

    @Override
    public long countMyFollowings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return friendRepo.countByUser(user);
    }


    @Override
    public List<PostsDto> getTimelineForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Friend> friends = friendRepo.findAllByUser(user);
        List<Long> friendIds = friends.stream()
                .map(f -> f.getFriend().getId())
                .collect(Collectors.toList());

        List<Posts> posts = postsRepo.findAllPostsByFriendIds(friendIds);

        return PostMapper.postMapper.toDtoList(posts);
    }

}
