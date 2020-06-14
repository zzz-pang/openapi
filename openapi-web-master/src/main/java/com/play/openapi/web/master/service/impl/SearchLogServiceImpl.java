package com.play.openapi.web.master.service.impl;

import com.alibaba.fastjson.JSON;
import com.play.openapi.web.master.bean.SearchBean;
import com.play.openapi.web.master.feign.client.SearchService;
import com.play.openapi.web.master.service.SearchLogService;
import com.play.openapi.web.master.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SearchLogServiceImpl implements SearchLogService {

    @Resource
    private SearchService searchService;

    @Override
    public R searchLog( SearchBean searchBean ) {
        System.err.println("impl---------");
        String json = JSON.toJSONString(searchBean);
        try {
            List <Map> list = searchService.find(json);
            long total = searchService.count(json);

            R r = new R();
            r.put("total",total);
            r.put("rows",list);

            return  r;

        } catch (IOException e) {
            e.printStackTrace();
        }
       return R.error();
    }
}
