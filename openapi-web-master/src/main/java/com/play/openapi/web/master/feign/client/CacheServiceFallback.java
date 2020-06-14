package com.play.openapi.web.master.feign.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CacheServiceFallback implements CacheService {
    @Override
    public boolean set( String key, String value, Long s ) {
        return false;
    }

    @Override
    public String get( String key ) {
        return null;
    }

    @Override
    public boolean expire( String key, int s ) {
        return false;
    }

    @Override
    public boolean hmset( String key, Map <String, String> map ) {
        return false;
    }

    @Override
    public Map hGetAll( String key ) {
        return null;
    }

    @Override
    public boolean exists( String key ) {
        return false;
    }

    @Override
    public boolean del( String key ) {
        return false;
    }
}
