package com.play.openapi.cache.service.impl;

import com.play.openapi.cache.exception.CacheException;
import com.play.openapi.cache.exception.ExceptionDict;
import com.play.openapi.cache.service.CacheService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.awt.SunHints;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean set( String key, String value, long expireScond ) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, expireScond);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            throw new CacheException(ExceptionDict.defaultCode, "存在失败");
        }
    }

    @Override
    public String getFromRedis( String key ) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean deleteKey( String key ) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public long getAutoIncreId( String key ) {
        return stringRedisTemplate.opsForValue().increment(key,1);
    }

    @Override
    public boolean setnx( String key, String value, long milliSeconds ) {
        return false;
    }

    @Override
    public boolean expire( String key, int seconds ) {
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public String get( String key ) {
        return null;
    }

    @Override
    public String getSet( String key, String value ) {
        return null;
    }

    @Override
    public boolean del( String key ) {
        return false;
    }

    @Override
    public Long lpush( String key, String[] strings ) {
        return stringRedisTemplate.opsForList().leftPushAll(key, strings);
    }

    @Override
    public String lpop( String key ) {
        return null;
    }

    @Override
    public List <String> lrange( String key, long start, long end ) {
        return null;
    }

    @Override
    public Long llen( String key ) {
        return null;
    }

    @Override
    public String hget( String key, String field ) {
        return null;
    }

    @Override
    public boolean hset( String key, String field, String value ) {
        return false;
    }

    @Override
    public Long sadd( String key, String[] strings ) {
        return null;
    }

    @Override
    public Long scard( String key ) {
        return null;
    }

    @Override
    public String spop( String key ) {
        return null;
    }

    @Override
    public boolean hDel( String key, String... fields ) {
        return false;
    }

    /**
     * 操作hash结构
     * @param key
     * @param map
     * @return
     */
    @Override
    public boolean hmset( String key, Map <String, String> map ) {
        try {
            stringRedisTemplate.opsForHash().putAll(key,map);
            return true;
        } catch (Exception e) {
            throw  new CacheException(ExceptionDict.defaultCode,"设置hash结构类型的数据失效");
        }
    }

    @Override
    public Map hgetall( String key ) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    @Override
    public boolean exists( String key ) {
        return stringRedisTemplate.hasKey(key);
    }
}
