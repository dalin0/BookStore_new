package com.ctgu.bookstore.service;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    Integer UserLikeBooks(String commentId);

    Integer UserNotLikeBooks(String commentId);

    void transLikeFromRedis();
}
