package com.liuweimin.adper.autoimport.config;

import com.liuweimin.adper.autoimport.job.AutoLearnJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author liuwm
 * @description 配置文件类
 * @date 7/8/2019 11:32 AM
 */

@Configuration
public class QuartzConfig {
    @Value("${cronExpression}")
    private String cronExpression;

    @Bean
    public JobDetail teatQuartzDetail() {
        return JobBuilder.newJob(AutoLearnJob.class).withIdentity("testQuartz").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger() {
        //设置时间周期单位秒
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
