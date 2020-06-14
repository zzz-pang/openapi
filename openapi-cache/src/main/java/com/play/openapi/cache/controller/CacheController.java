package com.play.openapi.cache.controller;

import com.play.openapi.cache.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Api(value = "缓存api", description = "缓存模块的api")
public class CacheController {

    @Resource
    private CacheService cacheService;

    @PostMapping("/set")
    @ApiOperation(value = "存放一个String类型的值")
    public boolean set( @ApiParam(name = "key", value = "键") @RequestParam("key") String key,
                        @ApiParam(name = "value", value = "值") @RequestParam("value") String value,
                        @ApiParam(name = "expireSecond", value = "过期时间") @RequestParam("expireSecond") Long s ) {
        return cacheService.set(key, value, s);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取String类型的值")
    public String get( @ApiParam(name = "key", value = "键") @RequestParam("key") String key ) {
        return cacheService.getFromRedis(key);
    }

    @PostMapping("/expire")
    @ApiOperation(value = "设置过期时间")
    public boolean expire( @ApiParam(name = "key", value = "键") @RequestParam("key") String key,
                           @ApiParam(name = "seconds", value = "过期时间") @RequestParam("seconds") int s ) {
        return cacheService.expire(key, s);
    }

    @PostMapping("/lpush")
    @ApiOperation(value = "存放一个List类型的值")
    public Long lpush( @ApiParam(name = "key", value = "键") @RequestParam("key") String key,
                       @ApiParam(name = "value", value = "数组") @RequestBody String value[] ) {
        return cacheService.lpush(key, value);
    }

    @PostMapping("/hmset")
    @ApiOperation(value = "存放hash类型的值")
    public boolean hmset( @ApiParam(name = "key", value = "键") @RequestParam("key") String key,
                          @ApiParam(name = "value", value = "map") @RequestBody Map <String, String> map ) {
        return cacheService.hmset(key, map);
    }

    @PostMapping("/hGetAll")
    @ApiOperation(value = "获取hash类型的值")
    public Map hGetAll( @ApiParam(name = "key", value = "键") @RequestParam("key") String key ) {
        return cacheService.hgetall(key);
    }

    @PostMapping("/exists")
    @ApiOperation(value = "判断某个key是否存在")
    public boolean exists( @ApiParam(name = "key", value = "键") @RequestParam("key") String key ) {
        return cacheService.exists(key);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除某个键值对")
    public boolean del( @ApiParam(name = "key", value = "键") @RequestParam("key") String key ) {
        return cacheService.deleteKey(key);
    }

    @GetMapping("/cache/test")
    @ApiOperation(value = "删除某个键值对")
    public String test() {
        return "成功";
    }

    @RequestMapping(value = "/getAutoIncr", method = RequestMethod.GET)
    @ApiOperation(value = "获取自动增长的值")
    public Long getAutoIncr( @ApiParam(name = "key", value = "键") @RequestParam("key") String key ) {
        return cacheService.getAutoIncreId(key);
    }
}
