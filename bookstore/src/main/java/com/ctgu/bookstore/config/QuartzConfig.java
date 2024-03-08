package com.ctgu.bookstore.config;

import com.ctgu.bookstore.utils.LikeTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: BookStore
 * @description:
 * @author: Nidol
 * @create: 2024-3-7 17:53
 **/
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail testQuartz1() {
        return JobBuilder.newJob(LikeTask.class).withIdentity("LikeTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        //2个小时到数据库
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(2)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("LikeTask")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
