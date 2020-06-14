package com.play.openapi.monitor.feign.client.impl;

import com.play.openapi.monitor.feign.client.SearchService;

import java.io.IOException;
import java.util.Map;

public class SearchServiceFallback implements SearchService {
    @Override
    public Map <String, Object> statAvg( long startTime, long endTime ) throws IOException {
        return null;
    }
}
