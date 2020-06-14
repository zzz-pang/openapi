package com.play.openapi.cache.service;

import java.util.List;
import java.util.Map;

public interface CacheService {
    //string list map set zset

    boolean set(String key,String value,long expireScond);

    /**
     * 从redis读取字符串类型的数据
     *
     * @param key
     * @return
     */
    String getFromRedis(String key);

    /**
     * 从redis删除字符串类型的数据key
     *
     * @param key
     * @return @
     */

    boolean deleteKey(String key);

    /**
     * 传入一个key，通过redis取得自增id
     *
     * @param key
     * @return @
     */
    long getAutoIncreId(String key);

    /**
     * 设置一个键值对    key存在：设置失败    key不存在，设置成功
     * @param key  键
     * @param value  值
     * @param milliSeconds  过期时间   单位毫秒
     * @return
     */
    public boolean setnx(String key, String value, long milliSeconds);

    public boolean expire(String key, int seconds);

    public String get(String key);

    public String getSet(String key, String value);

    public boolean del(String key);

    public Long lpush(String key, String[] strings);

    public String lpop(String key);

    public List <String> lrange( String key, long start, long end);

    public Long llen(String key);

    public String hget(String key, String field);

    public boolean hset(String key, String field, String value);

    Long sadd(String key, String[] strings);

    Long scard(String key);

    String spop(String key);

/**
 * 得到所有的业务数据集合
 *
 * @param key 业务数据集合key
 * @return @
 */
//    public Map<Object, Object> hgetAll(String key);

    /**
     * 删除所有业务数据集合
     *
     * @param key
     * @return
     */
    public boolean hDel(String key, String... fields);

    public boolean hmset(String key, Map<String,String>  map) ;

    public Map hgetall( String key);
    public boolean exists( String key);
}
