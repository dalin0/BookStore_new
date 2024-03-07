package com.ctgu.bookstore.controller;


import com.ctgu.bookstore.entity.Comment;
import com.ctgu.bookstore.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/bookstore/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/total")
    @ApiOperation("获取评论数量")
    public int getTotalComment(){
        return commentService.list(null).size();
    }

    @GetMapping("/test/{id}")
    @ApiOperation("获取一个评论")
    public Comment getOneComment(@PathVariable("id") int Commentid){
        Comment comment =commentService.getById(Commentid);
        System.out.println(comment);
        return comment;
    }
}

