package com.ctgu.bookstore.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.service.LikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: BookStore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/bookstore/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/confirm/{commentId}")
    @ApiOperation("点赞，返回点赞数，redis实现")
    public Integer UserLikeBooks(@PathVariable("commentId") String commentId){
        return likeService.UserLikeBooks(commentId);
    }

    @GetMapping("/cancel/{commentId}")
    @ApiOperation("取消点赞，返回点赞数，redis实现")
    public Integer UserNotLikeBooks(@PathVariable("commentId") String commentId){
        return likeService.UserNotLikeBooks(commentId);
    }
}
