package com.ctgu.bookstore.utils;

import com.ctgu.bookstore.service.LikeService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: BookStore
 * @description:
 * @author: Nidol
 * @create: 2024-3-6
 **/
public class LikeTask extends QuartzJobBean {
    @Autowired
    private LikeService likeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        likeService.transLikeFromRedis();
    }

}
