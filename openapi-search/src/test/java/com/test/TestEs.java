package com.test;

import com.alibaba.fastjson.JSON;
import com.play.openapi.search.SearchApplication;
import com.play.openapi.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class TestEs {

//    @Resource
//    private RestHighLevelClient restHighLevelClient;

    @Resource
    private SearchService searchService;

    @Test
    public void test() throws  Exception{
//        System.err.println("RestHighLevelClient"+restHighLevelClient);
//        System.err.println(searchService.deleteIndex());
//        searchService.createIndex();
//        System.err.println(searchService.existIndex());
//        Map<String,Object> map = new HashMap<>();
//        for (int i = 0; i < 20; i++) {
//            map.put("appKey","aaaa"+i%3);//aaaa0  aaaa1  aaaa2
//            map.put("servIP","192.168.82."+(i+22));
//            map.put("venderId",(i+1));
//            map.put("remoteIp","192.168.82."+(i+52));
//            map.put("apiName","jingdong.category.findAll");
//            map.put("platformRepTime",System.currentTimeMillis());
//            map.put("requestContent","海尔");
//            map.put("errorCode","3000-1");
//            map.put("receiveTime", Date.valueOf("2019-08-10"));
//            map.put("createTime",Date.valueOf("2019-08-1"));
//
//            String json = JSON.toJSONString(map);
//            searchService.saveData(json);

//        }
        //查询
        Map<String,Object> map = new HashMap<>();
        map.put("appKey","aaaa0");
        map.put("apiName","jingdong.category.findAll");
        map.put("requestContent","海尔");
        map.put("startTime",1);
        map.put("endTime",1565366400001l);
        map.put("start",5);
        map.put("rows",5);
        String json = JSON.toJSONString(map);
//        searchService.search(json);
//        System.out.println(searchService.count(json));
        System.out.println(searchService.statAvg(1, 1565366400001l));
    }
}
