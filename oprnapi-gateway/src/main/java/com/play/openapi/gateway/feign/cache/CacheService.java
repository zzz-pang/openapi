package com.play.openapi.gateway.feign.cache;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "cache-service", fallback = CacheServiceFallback.class)
public interface CacheService {
    @PostMapping("/set")
    public boolean set( @RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") Long s );

    @GetMapping("/get")
    public String get( @RequestParam("key") String key );

    @PostMapping("/expire")
    public boolean expire( @RequestParam("key") String key, @RequestParam("seconds") int s );


    @PostMapping("/hmset")
    public boolean hmset( @RequestParam("key") String key, @RequestBody Map <String, String> map );

    @PostMapping("/hGetAll")
    public Map hGetAll( @RequestParam("key") String key );

    @PostMapping("/exists")
    public boolean exists( @RequestParam("key") String key );

    @PostMapping("/del")
    public boolean del( @RequestParam("key") String key );

    @RequestMapping(value = "/getAutoIncr",method = RequestMethod.GET)
    public Long getAutoIncr(@RequestParam("key") String key );

}
