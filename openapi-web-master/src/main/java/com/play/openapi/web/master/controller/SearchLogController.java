package com.play.openapi.web.master.controller;

import com.play.openapi.web.master.bean.SearchBean;
import com.play.openapi.web.master.service.AppInfoService;
import com.play.openapi.web.master.service.SearchLogService;
import com.play.openapi.web.master.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/search")
public class SearchLogController {
    @Resource
    private AppInfoService appInfoService;

    @Resource
    private SearchLogService searchLogService;

    @RequestMapping(params = "act=app")
    public R  findAppInfo(){

        return R.ok().put("appInfos",appInfoService.findAll());
    }

    /**
     act: table
     limit: 10
     offset: 0
     apiName: aaa
     startTime: 1565654400000
     endTime: 1567123200000
     requestContent: aaa
     appKey: 561AC1A8676CFCB0CC61B041AE42ABB8
     */
    @RequestMapping(params = "act=table")
    public R searchLog(SearchBean searchBean){
        System.err.println("act=able------------------>");

        return  searchLogService.searchLog(searchBean);

    }

}
