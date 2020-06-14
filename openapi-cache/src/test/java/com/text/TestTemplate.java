package com.text;

import com.play.openapi.cache.CacheApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheApplication.class)
public class TestTemplate {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        System.out.println(redisTemplate);
        redisTemplate.opsForValue().set("c","bbb");
        System.out.println(redisTemplate.opsForValue().get("c"));
    }

}
