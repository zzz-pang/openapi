package com.play.openapi.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {


    @Value("${test}")
    private String test;


    // RedisConnectionFactory is required
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)//
    public StringRedisTemplate redisTemplate( RedisConnectionFactory redisConnectionFactory ) {
        System.err.println("加载的文件" + test);
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}
