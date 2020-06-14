package com.play.openapi.monitor.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 工具类 编写启动定时任务的类
 */
public class ScheduleUtils {
    static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    static final String GROUP_NAME = "monitor-group";

    /**
     * Scheduler 定时任务
     * JobDetail  定时任务描述类
     * Trigger   定时任务触发类
     *
     * @param scriptName
     * @param jobClass
     * @param cronExpression
     */


    public static void createSchedule( String scriptName, Class jobClass, String cronExpression ) {

        try {
            //1.得到Scheduler对象
            Scheduler scheduler = schedulerFactory.getScheduler();
            //2.创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(scriptName, GROUP_NAME).build();
            //3.创建trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).startNow().build();
            //4.注册任务和触发器
            scheduler.scheduleJob(jobDetail, cronTrigger);
            //5.启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
