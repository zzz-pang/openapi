package com.play.openapi.search.mq.listener;

import com.play.openapi.search.service.SearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 监听 gw——log队列
 */
@Component
public class GwLogListener {
    //注入searchService
    @Resource
    private SearchService searchService;

    @RabbitListener(queues = "gw_log")
    public void bnMessage(String message){
        try {
            System.err.println("rabbitmq接到消息了"+message+"。存储es成功");
            //业务逻辑
            searchService.saveData(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
