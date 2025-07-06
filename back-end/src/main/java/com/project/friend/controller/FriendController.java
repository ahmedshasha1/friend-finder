package com.project.friend.controller;

import com.project.friend.controller.vm.UserDetailsVM;
import com.project.friend.dto.PostsDto;
import com.project.friend.dto.auth.UserDto;
import com.project.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/follow")
     ResponseEntity<String> follow(@RequestParam @Validated Long userId, @RequestParam @Validated Long friendId) {
        friendService.follow(friendId);
        return ResponseEntity.ok("Followed successfully");
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestParam @Validated Long userId, @RequestParam @Validated Long friendId) {
        friendService.unFollow(friendId);
        return ResponseEntity.ok("Unfollowed successfully");
    }
    @GetMapping("/my-followings")
    public ResponseEntity<List<UserDetailsVM>> getMyFollowings() {
        return ResponseEntity.ok(friendService.getMyFollowings());
    }

    @GetMapping("/my-followings/count")
    public ResponseEntity<Long> getMyFollowingsCount() {
        return ResponseEntity.ok(friendService.countMyFollowings());
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<PostsDto>> getTimeline() {
        return ResponseEntity.ok(friendService.getTimelineForCurrentUser());
    }

}
