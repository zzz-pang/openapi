package com.play.openapi.search.controller;

import com.play.openapi.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Api("搜索模块在线api")
public class SearchController {

    @Resource
    private SearchService searchService;

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    @ApiOperation("搜索日志")
    public List <Map> find( @ApiParam(value = "查询条件封装承德json") @RequestParam("json") String json ) throws IOException {
        System.err.println("find-------------");
        return searchService.search(json);
    }

    @RequestMapping(value = "/statAvg",method = RequestMethod.GET)
    @ApiOperation("计算api的平均请求时长")
    public Map<String,Object> statAvg(@ApiParam(value = "时间范围的起始值") @RequestParam(value = "startTime",required = true) long startTime,@ApiParam(value = "调用请求时间范围的结束值") @RequestParam(value = "endTime",required = true) long endTime) throws IOException {
        return  searchService.statAvg(startTime,endTime);
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ApiOperation("计算满足条件的总的记录数")
    public long count(@ApiParam(value = "查询条件封装成的json") @RequestParam(value = "json",required = true) String json) throws IOException {
        System.err.println("count-------------");
        return  searchService.count(json);
    }
}
