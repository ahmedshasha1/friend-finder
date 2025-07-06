package com.project.friend.service;

import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dto.PostsDto;


import java.util.List;

public interface FriendService {
    void follow( Long friendId);
    void unFollow( Long friendId);

    List<UserDetailsVM> getMyFollowings();
    long countMyFollowings();

    List<PostsDto> getTimelineForCurrentUser();

}
