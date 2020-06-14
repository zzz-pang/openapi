package com.play.openapi.monitor.quartz;

import com.play.openapi.monitor.feign.client.SearchService;
import com.play.openapi.monitor.utils.ScheduleUtils;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class JobQuartz implements Job, ApplicationContextAware {

    static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        JobQuartz.applicationContext = applicationContext;
    }


    @Override
    public void execute( JobExecutionContext jobExecutionContext ) throws JobExecutionException {
        SearchService searchService = applicationContext.getBean(SearchService.class);
        Date date = new Date();
        long t = (date.getTime() - 1000 * 60 * 60 * 12);
        try {
            Map <String, Object> map = searchService.statAvg(t, date.getTime());
            for (String key : map.keySet()) {
                Object v = map.get(key);
                if (key.equals("apiName")){
                    System.out.print("apiName"+v);
                }
                if (key.equals("avg")){
                    System.out.println("\t平均调用时间"+v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        //创建定时任务
        ScheduleUtils.createSchedule("avg_quartz", JobQuartz.class, "0/5 * * * * ?");
    }

    @PreDestroy
    public void destory() {
        System.out.println("destory");

    }


}
