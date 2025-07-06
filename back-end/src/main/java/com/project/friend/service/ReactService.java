package com.project.friend.service;

import com.project.friend.dto.ReactCountDto;

public interface ReactService {
    void addReactToPost(Long postId, boolean isLike);
    void addReactToComment(Long commentId, boolean isLike);
    void unReactToPost(Long postId);
    void unReactToComment(Long commentId);
    ReactCountDto getCommentReactCount(Long commentId);
    ReactCountDto getPostReactCount(Long postId);


}
